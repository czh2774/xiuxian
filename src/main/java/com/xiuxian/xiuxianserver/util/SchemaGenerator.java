package com.xiuxian.xiuxianserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.*;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class SchemaGenerator {

    // ANSI 转义序列，用于控制台颜色输出
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private static final Logger logger = LoggerFactory.getLogger(SchemaGenerator.class);
    private static final String SCHEMA_PATH = "src/main/resources/schemas/";

    public static void main(String[] args) {
        // 1. 扫描 com.xiuxian.xiuxianserver.entity 包中的所有类
        Reflections reflections = new Reflections("com.xiuxian.xiuxianserver.entity", Scanners.TypesAnnotated);

        // 2. 找出带有 @ExcelField 注解的类
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(ExcelField.class);

        // 3. 遍历所有符合条件的类并生成对应的 JSON Schema 文件
        for (Class<?> clazz : annotatedClasses) {
            generateSchemaForClass(clazz);
        }
    }

    private static void generateSchemaForClass(Class<?> clazz) {
        String className = clazz.getSimpleName();
        String schemaFileName = SCHEMA_PATH + className + ".json";

        // 如果文件已经存在，打印红色日志，并跳过生成
        if (Files.exists(Paths.get(schemaFileName))) {
            logger.error(ANSI_RED + "Schema 文件已存在: {}" + ANSI_RESET, schemaFileName);
            return;
        }

        // 使用 jsonschema-generator 生成 JSON Schema
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(
                SchemaVersion.DRAFT_7, OptionPreset.PLAIN_JSON);
        SchemaGeneratorConfig config = configBuilder.build();
        com.github.victools.jsonschema.generator.SchemaGenerator generator = new com.github.victools.jsonschema.generator.SchemaGenerator(config);

        // 生成 JSON Schema
        Object schema = generator.generateSchema(clazz);

        // 将 JSON Schema 写入文件
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(schemaFileName), schema);
            logger.info(ANSI_GREEN + "Schema 文件生成成功: {}" + ANSI_RESET, schemaFileName);
        } catch (IOException e) {
            logger.error(ANSI_RED + "生成 Schema 文件时出错: {}" + ANSI_RESET, e.getMessage(), e);
        }
    }
}
