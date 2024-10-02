package com.xiuxian.xiuxianserver.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ExcelTemplateManagerTest {

    @Test
    public void testScanAndGenerateOrUpdateTemplates() {
        ExcelTemplateManager manager = new ExcelTemplateManager();
        String entityDirectory = "D:/workspace/java/src/main/java/com/xiuxian/xiuxianserver/entity";
        String outputFilePath = "D:/workspace/java/generated-files/entity_templates.xlsx";

        try {
            manager.scanAndGenerateOrUpdateTemplates(entityDirectory, outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
