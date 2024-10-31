package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.GeneralsTemplateCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateUpdateRequestDTO;
import com.xiuxian.xiuxianserver.dto.IdRequestDTO;
import com.xiuxian.xiuxianserver.service.GeneralsTemplateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * GeneralsTemplateController类，处理武将模板的API请求。
 */
@RestController
@RequestMapping("/generals/templates")
public class GeneralsTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(GeneralsTemplateController.class);

    @Autowired
    private GeneralsTemplateService generalsTemplateService;

    /**
     * 获取指定ID的武将模板
     * @param request 包含ID的请求DTO
     * @param servletRequest HTTP请求对象
     * @return 武将模板的DTO对象
     */
    @PostMapping("/getById")
    public ResponseEntity<GeneralsTemplateDTO> getGeneralTemplateById(@RequestBody IdRequestDTO request, HttpServletRequest servletRequest) {
        logger.info("Received request to fetch GeneralsTemplate with ID: {}", request.getId());
        GeneralsTemplateDTO template = generalsTemplateService.getGeneralTemplateById(request.getId());
        return ResponseEntity.ok(template);
    }

    /**
     * 获取所有武将模板
     * @param servletRequest HTTP请求对象
     * @return 武将模板的DTO列表
     */
    @PostMapping("/getAll")
    public ResponseEntity<List<GeneralsTemplateDTO>> getAllGeneralTemplates(HttpServletRequest servletRequest) {
        logger.info("Received request to fetch all GeneralsTemplates");
        List<GeneralsTemplateDTO> templates = generalsTemplateService.getAllGeneralTemplates();
        return ResponseEntity.ok(templates);
    }

    /**
     * 创建新的武将模板
     * @param request 创建模板请求DTO
     * @param servletRequest HTTP请求对象
     * @return 创建后的武将模板DTO对象
     */
    @PostMapping("/create")
    public ResponseEntity<GeneralsTemplateDTO> createGeneralTemplate(@RequestBody GeneralsTemplateCreateRequestDTO request, HttpServletRequest servletRequest) {
        logger.info("Received request to create a new GeneralsTemplate");
        GeneralsTemplateDTO template = generalsTemplateService.createGeneralTemplate(request);
        return ResponseEntity.ok(template);
    }

    /**
     * 更新武将模板信息
     * @param request 更新请求DTO
     * @param servletRequest HTTP请求对象
     * @return 更新后的武将模板DTO对象
     */
    @PostMapping("/update")
    public ResponseEntity<GeneralsTemplateDTO> updateGeneralTemplate(@RequestBody GeneralsTemplateUpdateRequestDTO request, HttpServletRequest servletRequest) {
        logger.info("Received request to update GeneralsTemplate with ID: {}", request.getId());
        GeneralsTemplateDTO template = generalsTemplateService.updateGeneralTemplate(request.getId(), request);
        return ResponseEntity.ok(template);
    }

    /**
     * 删除指定ID的武将模板
     * @param request 删除请求DTO
     * @param servletRequest HTTP请求对象
     * @return 空的响应体
     */
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteGeneralTemplate(@RequestBody IdRequestDTO request, HttpServletRequest servletRequest) {
        logger.info("Received request to delete GeneralsTemplate with ID: {}", request.getId());
        generalsTemplateService.deleteGeneralTemplate(request.getId());
        return ResponseEntity.ok().build();
    }
}
