package com.xiuxian.xiuxianserver.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcelDataLoader {

    private final DataSource dataSource;

    // ANSI 颜色编码
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";

    public ExcelDataLoader(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 加载指定目录下的所有 Excel 文件
    public void loadExcelData() {
        String directoryPath = "generated-files";

        try {
            List<File> excelFiles = Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".xlsx"))
                    .map(path -> path.toFile())
                    .toList();

            for (File file : excelFiles) {
                processFileWithLogging(file);
            }

            logInfo("所有文件处理完毕！");
        } catch (Exception e) {
            logError("加载文件目录时发生异常", e);
        }
    }

    // 包装文件处理，添加日志记录
    private void processFileWithLogging(File file) {
        logInfo("开始处理文件: " + file.getName());
        try {
            processExcelFile(file);
            logInfo("文件处理完成: " + file.getName());
        } catch (Exception e) {
            logError("处理文件时发生异常: " + file.getName(), e);
        }
    }

    // 处理单个 Excel 文件并将数据插入数据库
    private void processExcelFile(File file) throws Exception {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis);
             Connection connection = dataSource.getConnection()) {

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(1); // 假设第二行为标题行
            String tableName = generateTableNameFromFile(file.getName());

            if (tableName == null) {
                logWarning("文件名无法转换为表名，跳过文件: " + file.getName());
                return;
            }

            Set<String> tableColumns = getTableColumns(connection, tableName);

            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // 根据行数据生成 SQL 插入语句
                String sql = generateInsertSQL(row, headerRow, tableName, tableColumns);
                if (sql != null) {
                    executeInsert(connection, sql, file.getName(), i + 1);
                } else {
                    logWarning("由于存在空值，跳过插入文件 " + file.getName() + " 的第 " + (i + 1) + " 行");
                }
            }
        }
    }

    // 获取数据库表的列名集合，用于列存在性检查
    private Set<String> getTableColumns(Connection connection, String tableName) throws SQLException {
        Set<String> columns = new HashSet<>();
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME").toLowerCase());
            }
        }
        return columns;
    }

    // 执行插入操作，如果主键冲突则更新
    private void executeInsert(Connection connection, String sql, String fileName, int rowNum) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            logInfo("成功插入数据，文件: " + fileName + "，行号: " + rowNum);
        } catch (SQLException e) {
            handleSQLException(e, fileName, rowNum);
        }
    }

    // 处理 SQL 异常并记录日志
    private void handleSQLException(SQLException e, String fileName, int rowNum) {
        if (e.getSQLState().startsWith("23") && e.getErrorCode() == 1062) {
            logInfo("跳过重复数据，文件: " + fileName + "，行号: " + rowNum);
        } else if (e instanceof java.sql.SQLSyntaxErrorException) {
            logWarning("SQL语法错误，文件: " + fileName + "，行号: " + rowNum + "，错误信息: " + e.getMessage());
        } else {
            logWarning("插入数据时发生SQL异常，文件: " + fileName + "，行号: " + rowNum, e);
        }
    }

    // 生成数据库表名的工具方法
    private String generateTableNameFromFile(String fileName) {
        if (fileName == null || !fileName.endsWith(".xlsx")) {
            return null;
        }

        String baseName = fileName.replace(".xlsx", "");
        return convertToSnakeCase(baseName);
    }

    // 生成插入语句，使用 ON DUPLICATE KEY UPDATE 以忽略重复主键
    private String generateInsertSQL(Row row, Row headerRow, String tableName, Set<String> tableColumns) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        StringBuilder updateClause = new StringBuilder();

        boolean firstColumn = true; // 标记是否是第一个列，用于控制逗号
        boolean hasNullInNonNullableColumn = false;

        for (int col = 0; col < headerRow.getPhysicalNumberOfCells(); col++) {
            String columnName = convertToSnakeCase(headerRow.getCell(col).getStringCellValue());

            // 如果表中不存在该列，则跳过
            if (!tableColumns.contains(columnName)) {
                logWarning("数据库表 " + tableName + " 中不存在列: " + columnName + "，跳过该列");
                continue;
            }

            if (!firstColumn) {
                columns.append(", ");
                values.append(", ");
                updateClause.append(", ");
            }
            firstColumn = false;

            // 处理列和值
            boolean valueIsNull = appendColumnAndValue(row, col, columnName, columns, values, updateClause);
            if (valueIsNull && !isNullableColumn(columnName)) {
                hasNullInNonNullableColumn = true;
            }
        }

        if (hasNullInNonNullableColumn) {
            return null; // 跳过该行的插入
        }

        return String.format("INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s;",
                tableName, columns, values, updateClause);
    }

    // 添加列和值到 SQL 语句，如果值为 NULL 则返回 true
    private boolean appendColumnAndValue(Row row, int col, String columnName, StringBuilder columns,
                                         StringBuilder values, StringBuilder updateClause) {
        columns.append(columnName);

        // 如果单元格为空
        if (row.getCell(col) == null) {
            logNullValueError(columnName, row.getRowNum() + 1);
            values.append("NULL");
            updateClause.append(columnName).append(" = NULL");
            return true;
        }

        try {
            switch (row.getCell(col).getCellType()) {
                case STRING:
                    if (isNumericColumn(columnName)) {
                        logTypeMismatch(columnName, row.getRowNum() + 1, row.getCell(col).getStringCellValue(), "Numeric", "String");
                        values.append("NULL");
                        updateClause.append(columnName).append(" = NULL");
                        return true;
                    } else {
                        String escapedValue = row.getCell(col).getStringCellValue().replace("'", "\\'");
                        values.append("'").append(escapedValue).append("'");
                        updateClause.append(columnName).append(" = VALUES(").append(columnName).append(")");
                    }
                    break;

                case NUMERIC:
                    values.append(row.getCell(col).getNumericCellValue());
                    updateClause.append(columnName).append(" = VALUES(").append(columnName).append(")");
                    break;

                case BOOLEAN:
                    values.append(row.getCell(col).getBooleanCellValue());
                    updateClause.append(columnName).append(" = VALUES(").append(columnName).append(")");
                    break;

                default:
                    values.append("NULL");
                    updateClause.append(columnName).append(" = NULL");
                    return true;
            }
        } catch (Exception e) {
            logWarning("插入数据时发生异常，列: " + columnName + "，行: " + (row.getRowNum() + 1) + "，值: " + row.getCell(col).toString(), e);
            values.append("NULL");
            updateClause.append(columnName).append(" = NULL");
            return true;
        }
        return false;
    }
    // 添加重载的 logWarning 方法，处理异常情况
    private void logWarning(String message, Exception e) {
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
        e.printStackTrace();
    }

    // 判断列是否为数值类型
    private boolean isNumericColumn(String columnName) {
        return columnName.matches(".*(id|tier|count|number|amount|level|rate|score|attack|defense).*");
    }

    // 判断列是否允许为空
    private boolean isNullableColumn(String columnName) {
        return !columnName.equals("attack_per_tier");
    }
    // 定义 convertToSnakeCase 方法，将字符串转换为蛇形命名
    private String convertToSnakeCase(String input) {
        StringBuilder result = new StringBuilder();
        result.append(Character.toLowerCase(input.charAt(0)));

        for (int i = 1; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append('_').append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
    // 记录空值错误日志
    private void logNullValueError(String columnName, int rowNum) {
        System.out.println(ANSI_RED + "错误：列 '" + columnName + "' 在行号 " + rowNum + " 处不能为空，当前值为 NULL。" + ANSI_RESET);
    }

    // 记录数据类型不匹配日志
    private void logTypeMismatch(String columnName, int rowNum, String cellValue, String expectedType, String actualType) {
        System.out.println(ANSI_YELLOW + "数据类型不匹配，列: " + columnName + "，行号: " + rowNum +
                "，值: " + cellValue + "，期望类型: " + expectedType + "，实际类型: " + actualType + ANSI_RESET);
    }

    private void logInfo(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    private void logWarning(String message) {
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }

    private void logError(String message, Exception e) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
        e.printStackTrace();
    }

    public static void main(String[] args) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mariadb://localhost:3306/xiuxian_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8");
            config.setUsername("root");
            config.setPassword("Leng9s9bxs");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            DataSource dataSource = new HikariDataSource(config);
            new ExcelDataLoader(dataSource).loadExcelData();
        } catch (Exception e) {
            System.out.println(ANSI_RED + "加载Excel数据时发生严重异常" + ANSI_RESET);
            e.printStackTrace();
        }
    }
}
