package com.xiuxian.xiuxianserver.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class ExcelTemplateGenerator {

    /**
     * 生成或补充Excel模板文件，基于传入的实体类。
     *
     * @param entityClasses   需要生成或检查的实体类
     * @param outputFilePath  Excel模板的输出路径
     */
    public void generateOrUpdateExcelTemplate(List<Class<?>> entityClasses, String outputFilePath) throws IOException {
        File excelFile = new File(outputFilePath);
        Workbook workbook;

        System.out.println("开始生成或更新Excel模板...");

        // 检查文件是否存在
        if (excelFile.exists()) {
            System.out.println("文件已存在，加载现有的Excel文件：" + outputFilePath);
            // 如果存在，加载现有文件
            FileInputStream fis = new FileInputStream(excelFile);
            workbook = new XSSFWorkbook(fis);
        } else {
            System.out.println("文件不存在，创建新的Excel文件。");
            // 如果不存在，创建新的Workbook
            workbook = new XSSFWorkbook();
        }

        for (Class<?> entityClass : entityClasses) {
            String sheetName = entityClass.getSimpleName();
            System.out.println("处理实体类：" + sheetName);

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                System.out.println("工作表不存在，创建新的工作表：" + sheetName);
                // 如果表不存在，则创建新表
                sheet = workbook.createSheet(sheetName);
                createHeaderRow(sheet, entityClass);
            } else {
                System.out.println("工作表已存在，检查字段：" + sheetName);
                // 如果表存在，则检查字段
                updateExistingSheet(sheet, entityClass);
            }
        }

        // 写回文件
        try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
            workbook.write(fileOut);
            System.out.println("成功生成或更新Excel文件：" + outputFilePath);
        }
        workbook.close();
        System.out.println("Excel文件处理完成并关闭。");
    }

    /**
     * 为新的Sheet创建标题行，并打印实体类中的字段
     */
    private void createHeaderRow(Sheet sheet, Class<?> entityClass) {
        Row headerRow = sheet.createRow(0);
        Field[] fields = entityClass.getDeclaredFields();

        System.out.println("实体类中的字段列表：");
        int colIdx = 0;
        for (Field field : fields) {
            Cell cell = headerRow.createCell(colIdx++);
            cell.setCellValue(field.getName());
            System.out.println(" - " + field.getName());
        }
    }

    /**
     * 检查并更新现有表中的字段，并打印表中的字段
     */
    private void updateExistingSheet(Sheet sheet, Class<?> entityClass) {
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            headerRow = sheet.createRow(0);
        }

        System.out.println("从Excel表中扫描出的字段列表：");
        Map<String, Integer> existingColumns = new HashMap<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null) {
                existingColumns.put(cell.getStringCellValue(), i);
                System.out.println(" - " + cell.getStringCellValue());
            }
        }

        Field[] fields = entityClass.getDeclaredFields();
        int nextColIdx = headerRow.getLastCellNum();

        System.out.println("实体类中的字段列表：");
        for (Field field : fields) {
            String fieldName = field.getName();
            System.out.println(" - " + fieldName);
            if (!existingColumns.containsKey(fieldName)) {
                Cell newCell = headerRow.createCell(nextColIdx++);
                newCell.setCellValue(fieldName);
                System.out.println("   -> 新增字段：" + fieldName);
            }
        }

        // 标记不存在于实体类中的字段
        for (Map.Entry<String, Integer> entry : existingColumns.entrySet()) {
            String columnName = entry.getKey();
            boolean fieldExists = Arrays.stream(fields).anyMatch(f -> f.getName().equals(columnName));
            if (!fieldExists) {
                Cell cell = headerRow.getCell(entry.getValue());
                cell.setCellValue(columnName + " [未找到对应字段]");
                System.out.println("   -> 标记多余字段：" + columnName + " [未找到对应字段]");
            }
        }
    }
}
