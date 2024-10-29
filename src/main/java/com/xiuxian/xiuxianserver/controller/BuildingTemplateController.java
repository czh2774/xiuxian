package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.BuildingTemplateDTO;
import com.xiuxian.xiuxianserver.dto.BuildingTemplateIdRequestDTO;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.service.BuildingTemplateService;
import com.xiuxian.xiuxianserver.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * BuildingTemplateController
 * 处理与建筑模板相关的API请求
 */
@RestController
@RequestMapping("/building/templates")
public class BuildingTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(BuildingTemplateController.class);

    private final BuildingTemplateService buildingTemplateService;

    public BuildingTemplateController(BuildingTemplateService buildingTemplateService) {
        this.buildingTemplateService = buildingTemplateService;
    }

    /**
     * 获取所有建筑模板的列表
     *
     * @param request HttpServletRequest对象
     * @return 所有建筑模板的列表，封装在CustomApiResponse中
     */
    @PostMapping("/list")
    @Operation(summary = "获取所有建筑模板", description = "获取系统中所有建筑模板的信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功返回所有建筑模板"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public ResponseEntity<CustomApiResponse<List<BuildingTemplateDTO>>> getAllBuildingTemplates(HttpServletRequest request) {
        logger.info("请求获取所有建筑模板，来源IP: {}", request.getRemoteAddr());

        List<BuildingTemplateDTO> templates;
        try {
            templates = buildingTemplateService.getAllBuildingTemplates();
            logger.info("成功获取建筑模板列表，共 {} 个模板", templates.size());
        } catch (Exception e) {
            logger.error("获取建筑模板列表时发生错误: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取建筑模板失败", request.getRequestURI()));
        }

        return ResponseEntity.ok(CustomApiResponse.success("获取建筑模板成功", templates, request.getRequestURI()));
    }

    /**
     * 根据ID获取指定建筑模板的详细信息
     *
     * @param requestDTO 包含建筑模板ID的请求DTO
     * @param request HttpServletRequest对象
     * @return 指定建筑模板的详细信息，封装在CustomApiResponse中
     */
    @PostMapping("/details")
    @Operation(summary = "获取建筑模板详情", description = "根据模板ID获取建筑模板的详细信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功返回建筑模板信息"),
            @ApiResponse(responseCode = "404", description = "建筑模板未找到"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public ResponseEntity<CustomApiResponse<BuildingTemplateDTO>> getBuildingTemplateDetails(@RequestBody BuildingTemplateIdRequestDTO requestDTO,
                                                                                             HttpServletRequest request) {
        logger.info("请求获取建筑模板详情，模板ID: {}, 来源IP: {}", requestDTO.getBuildingTemplateId(), request.getRemoteAddr());

        BuildingTemplateDTO template;
        try {
            template = buildingTemplateService.getBuildingTemplateById(requestDTO.getBuildingTemplateId());
            logger.info("成功获取建筑模板详情，模板ID: {}", requestDTO.getBuildingTemplateId());
        } catch (ResourceNotFoundException e) {
            logger.warn("未找到建筑模板，ID: {}", requestDTO.getBuildingTemplateId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CustomApiResponse.error(HttpStatus.NOT_FOUND.value(), "建筑模板未找到", request.getRequestURI()));
        } catch (Exception e) {
            logger.error("获取建筑模板详情时发生错误: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取建筑模板详情失败", request.getRequestURI()));
        }

        return ResponseEntity.ok(CustomApiResponse.success("获取建筑模板详情成功", template, request.getRequestURI()));
    }
}
