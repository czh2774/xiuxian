package com.xiuxian.xiuxianserver.util;

import com.xiuxian.xiuxianserver.util.ExcelColumn;
import com.xiuxian.xiuxianserver.util.ExcelField;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class ExcelGenerator {

    // 输出Excel文件到指定路径
    public static void generateExcel(Class<?> clazz, String outputDir) throws IOException {
        // 检查类是否有 @ExcelField 注解
        if (!clazz.isAnnotationPresent(ExcelField.class)) {
            System.out.println("类 " + clazz.getName() + " 未包含 @ExcelField 注解，跳过...");
            return;
        }

        // 输出目录
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();  // 创建生成目录
            System.out.println("创建目录：" + outputDir);
        }

        String fileName = clazz.getSimpleName() + ".xlsx";
        File outputFile = new File(dir, fileName);

        if (outputFile.exists()) {
            // 如果文件已存在，检查字段差异
            checkExcelConsistency(outputFile, clazz);
        } else {
            // 文件不存在，正常生成Excel文件
            createNewExcelFile(outputFile, clazz);
        }
    }

    // 检查Excel文件和实体类字段是否一致
    private static void checkExcelConsistency(File excelFile, Class<?> clazz) throws IOException {
        System.out.println("Excel 文件已存在，正在检查与类 " + clazz.getSimpleName() + " 的字段是否一致...");

        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row commentRow = sheet.getRow(0);  // 第一行：字段注释
            Row fieldNameRow = sheet.getRow(1);  // 第二行：字段名称

            // 获取实体类的字段信息
            Field[] fields = clazz.getDeclaredFields();
            List<String> classFieldNames = new ArrayList<>();
            List<String> classComments = new ArrayList<>();

            for (Field field : fields) {
                if (field.isAnnotationPresent(ExcelColumn.class)) {
                    ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                    classFieldNames.add(field.getName());
                    classComments.add(excelColumn.headerName());
                }
            }

            // 获取Excel文件中的字段信息
            List<String> excelFieldNames = new ArrayList<>();
            List<String> excelComments = new ArrayList<>();

            for (int i = 0; i < fieldNameRow.getPhysicalNumberOfCells(); i++) {
                excelFieldNames.add(fieldNameRow.getCell(i).getStringCellValue());
                excelComments.add(commentRow.getCell(i).getStringCellValue());
            }

            // 比较类中的字段和Excel中的字段
            boolean isSame = true;

            // 记录差异
            if (!classFieldNames.equals(excelFieldNames)) {
                isSame = false;
                System.out.println("\033[31m字段不匹配，差异如下：\033[0m");
                logFieldDifferences("字段", classFieldNames, excelFieldNames);
            }

            // 比较注释
            if (!classComments.equals(excelComments)) {
                isSame = false;
                System.out.println("\033[31m注释不匹配，差异如下：\033[0m");
                logFieldDifferences("注释", classComments, excelComments);
            }

            if (isSame) {
                System.out.println("Excel文件与类 " + clazz.getSimpleName() + " 完全一致，无需更新。");
            }
        }
    }

    // 日志输出字段差异
    private static void logFieldDifferences(String type, List<String> classList, List<String> excelList) {
        Set<String> classSet = new HashSet<>(classList);
        Set<String> excelSet = new HashSet<>(excelList);

        // 类中存在但在Excel中不存在的字段
        Set<String> missingInExcel = new HashSet<>(classSet);
        missingInExcel.removeAll(excelSet);

        // Excel中存在但在类中不存在的字段
        Set<String> missingInClass = new HashSet<>(excelSet);
        missingInClass.removeAll(classSet);

        if (!missingInExcel.isEmpty()) {
            System.out.println("\033[31m" + type + "在类中存在但在Excel中不存在: " + missingInExcel + "\033[0m");
        }

        if (!missingInClass.isEmpty()) {
            System.out.println("\033[31m" + type + "在Excel中存在但在类中不存在: " + missingInClass + "\033[0m");
        }
    }

    // 创建新的Excel文件
    private static void createNewExcelFile(File outputFile, Class<?> clazz) throws IOException {
        System.out.println("生成新的 Excel 文件：" + outputFile.getAbsolutePath());

        // 创建一个新的 Excel 工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(clazz.getSimpleName());

        // 获取类的所有字段
        Field[] fields = clazz.getDeclaredFields();
        int rowIndex = 0;

        // 创建Excel的第一行：字段注释
        Row commentRow = sheet.createRow(rowIndex++);
        // 创建Excel的第二行：字段名称
        Row fieldNameRow = sheet.createRow(rowIndex++);
        int cellIndex = 0;

        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);

                // 第一行填写字段注释
                Cell commentCell = commentRow.createCell(cellIndex);
                String comment = field.getAnnotation(ExcelColumn.class).headerName();
                commentCell.setCellValue(comment != null ? comment : "");
                System.out.println("创建注释列：" + (comment != null ? comment : "无注释"));

                // 第二行填写字段名称
                Cell fieldNameCell = fieldNameRow.createCell(cellIndex++);
                fieldNameCell.setCellValue(field.getName());
                System.out.println("创建字段列：" + field.getName());
            }
        }

        // 输出文件到指定目录
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            workbook.write(outputStream);
            System.out.println("Excel 文件生成成功：" + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("生成 Excel 文件时出错：" + e.getMessage());
        }

        workbook.close();
        System.out.println("Excel 文件处理完成：" + outputFile.getName());
    }

    // 获取指定包下的所有类
    public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    // 查找目录中的类
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    // 扫描并生成所有模板类的Excel文件
    public static void generateAllExcel(String outputDir, String packageName) throws IOException, ClassNotFoundException {
        System.out.println("开始扫描包：" + packageName);

        List<Class<?>> classes = getClasses(packageName);

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(ExcelField.class)) {
                System.out.println("处理类：" + clazz.getName());
                generateExcel(clazz, outputDir);
            }
        }

        System.out.println("所有Excel文件生成完毕！");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 输出目录
        String outputDir = "D:/workspace/java/generated-files/";

        // 自动扫描并生成Excel文件，扫描entity包下所有类
        generateAllExcel(outputDir, "com.xiuxian.xiuxianserver.entity");
    }
}
