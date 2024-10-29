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
import java.sql.PreparedStatement;
import java.util.List;

public class ExcelDataLoader {

    private final DataSource dataSource;

    public ExcelDataLoader(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void loadExcelData() throws Exception {
        // 指定扫描的文件夹路径
        String directoryPath = "generated-files";

        // 获取文件夹中所有的Excel文件
        List<File> excelFiles = Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".xlsx"))
                .map(path -> path.toFile())
                .toList();

        // 处理每个文件
        for (File file : excelFiles) {
            System.out.println("正在处理文件: " + file.getName());
            processExcelFile(file);
        }

        System.out.println("所有文件处理完毕！");
    }

    private void processExcelFile(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis);
             Connection connection = dataSource.getConnection()) {

            Sheet sheet = workbook.getSheetAt(0);

            // 跳过第一行，使用第二行作为列名
            Row headerRow = sheet.getRow(1);  // 第2行的列名
            int columnCount = headerRow.getPhysicalNumberOfCells();

            String tableName = generateTableNameFromFile(file.getName());

            if (tableName == null) {
                System.out.println("无法处理文件: " + file.getName());
                return;
            }

            // 从第三行开始读取数据
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String sql = generateInsertSQL(row, headerRow, columnCount, tableName);
                if (sql != null) {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.executeUpdate();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateTableNameFromFile(String fileName) {
        if (fileName == null || !fileName.endsWith(".xlsx")) {
            return null;
        }

        String baseName = fileName.replace(".xlsx", "");

        StringBuilder tableName = new StringBuilder();
        tableName.append(Character.toLowerCase(baseName.charAt(0)));

        for (int i = 1; i < baseName.length(); i++) {
            char c = baseName.charAt(i);
            if (Character.isUpperCase(c)) {
                tableName.append('_');
                tableName.append(Character.toLowerCase(c));
            } else {
                tableName.append(c);
            }
        }

        return tableName.toString();
    }

    // 处理列名，将驼峰命名转换为下划线格式
    private String convertToSnakeCase(String columnName) {
        StringBuilder result = new StringBuilder();
        result.append(Character.toLowerCase(columnName.charAt(0)));  // 首字母转为小写

        for (int i = 1; i < columnName.length(); i++) {
            char c = columnName.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append('_').append(Character.toLowerCase(c));  // 大写字母前添加下划线，并转换为小写
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    private String generateInsertSQL(Row row, Row headerRow, int columnCount, String tableName) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (int col = 0; col < columnCount; col++) {
            // 将 Excel 列名转换为下划线格式的列名
            String columnName = convertToSnakeCase(headerRow.getCell(col).getStringCellValue());
            columns.append(columnName);

            switch (row.getCell(col).getCellType()) {
                case STRING:
                    values.append("'").append(row.getCell(col).getStringCellValue()).append("'");
                    break;
                case NUMERIC:
                    values.append(row.getCell(col).getNumericCellValue());
                    break;
                case BOOLEAN:
                    values.append(row.getCell(col).getBooleanCellValue());
                    break;
                default:
                    values.append("NULL");
            }

            if (col < columnCount - 1) {
                columns.append(", ");
                values.append(", ");
            }
        }

        return String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, columns.toString(), values.toString());
    }

    public static void main(String[] args) {
        try {
            // 创建并配置 HikariCP 数据源
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mariadb://localhost:3306/xiuxian_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8");
            config.setUsername("root");
            config.setPassword("Leng9s9bxs");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            DataSource dataSource = new HikariDataSource(config);

            ExcelDataLoader loader = new ExcelDataLoader(dataSource);
            loader.loadExcelData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
