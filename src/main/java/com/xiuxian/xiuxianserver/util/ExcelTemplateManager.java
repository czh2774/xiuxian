package com.xiuxian.xiuxianserver.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ExcelTemplateManager {

    /**
     * 扫描实体类目录并生成或更新Excel模板
     *
     * @param entityDirectory 实体类目录
     * @param outputFilePath  Excel模板输出路径
     */
    public void scanAndGenerateOrUpdateTemplates(String entityDirectory, String outputFilePath) throws IOException {
        File directory = new File(entityDirectory);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("无效的实体类目录：" + entityDirectory);
        }

        List<Class<?>> entityClasses = new ArrayList<>();
        // 假设已经加载了类，并在此处填充类列表
        // entityClasses.add(...);

        // 调用Excel模板生成/更新
        ExcelTemplateGenerator generator = new ExcelTemplateGenerator();
        generator.generateOrUpdateExcelTemplate(entityClasses, outputFilePath);
    }
}
