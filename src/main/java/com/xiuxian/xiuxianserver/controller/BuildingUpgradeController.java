package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.BuildingUpgradeDTO;
import com.xiuxian.xiuxianserver.dto.BuildingUpgradeRequestDTO;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.service.BuildingUpgradeService;
import com.xiuxian.xiuxianserver.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BuildingUpgradeController 用于处理与建筑升级相关的 API 请求。
 */
@RestController
@RequestMapping("/building/upgrade")
public class BuildingUpgradeController {

    private static final Logger logger = LoggerFactory.getLogger(BuildingUpgradeController.class);

    @Autowired
    private BuildingUpgradeService buildingUpgradeService;

    /**
     * 获取所有建筑升级的列表。
     *
     * @return 包含所有建筑升级DTO的统一响应
     */
    @Operation(summary = "获取所有建筑升级", description = "返回所有建筑升级的列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取建筑升级列表"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/getAllUpgrades")
    public CustomApiResponse<List<BuildingUpgradeDTO>> getAllBuildingUpgrades(HttpServletRequest request) {
        logger.info("请求获取所有建筑升级数据，来源IP: {}", request.getRemoteAddr());

        List<BuildingUpgradeDTO> upgrades;
        try {
            upgrades = buildingUpgradeService.getAllBuildingUpgrades();
            logger.info("成功获取建筑升级列表，总计: {} 条记录", upgrades.size());
        } catch (Exception e) {
            logger.error("获取建筑升级数据时发生错误: {}", e.getMessage(), e);
            return new CustomApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取建筑升级数据失败", null, request.getRequestURI());
        }

        return new CustomApiResponse<>(HttpStatus.OK.value(), "成功", upgrades, request.getRequestURI());
    }

    /**
     * 根据建筑模板ID和目标升级等级获取建筑升级信息。
     *
     * @param requestDTO 包含建筑模板ID和目标等级的请求体
     * @return 包含建筑升级详情的统一响应
     */
    @Operation(summary = "根据建筑模板ID和目标升级等级获取建筑升级", description = "根据建筑模板ID和目标升级等级返回建筑升级信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取建筑升级信息"),
            @ApiResponse(responseCode = "404", description = "未找到建筑升级信息"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/getByTemplateIdAndLevel")
    public CustomApiResponse<BuildingUpgradeDTO> getBuildingUpgradeByTemplateIdAndLevel(@RequestBody BuildingUpgradeRequestDTO requestDTO, HttpServletRequest httpServletRequest) {
        logger.info("根据模板ID和等级获取建筑升级信息，模板ID: {}, 等级: {}", requestDTO.getBuildingTemplateId(), requestDTO.getLevel());

        BuildingUpgradeDTO upgrade;
        try {
            upgrade = buildingUpgradeService.getBuildingUpgradeByTemplateIdAndLevel(requestDTO.getBuildingTemplateId(), requestDTO.getLevel());
            logger.info("成功获取建筑升级信息，模板ID: {}, 等级: {}", requestDTO.getBuildingTemplateId(), requestDTO.getLevel());
        } catch (ResourceNotFoundException e) {
            logger.warn("未找到建筑升级信息，模板ID: {}, 等级: {}", requestDTO.getBuildingTemplateId(), requestDTO.getLevel());
            return new CustomApiResponse<>(HttpStatus.NOT_FOUND.value(), "未找到建筑升级信息", null, httpServletRequest.getRequestURI());
        } catch (Exception e) {
            logger.error("获取建筑升级信息时发生错误: {}", e.getMessage(), e);
            return new CustomApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取建筑升级信息失败", null, httpServletRequest.getRequestURI());
        }

        return new CustomApiResponse<>(HttpStatus.OK.value(), "成功", upgrade, httpServletRequest.getRequestURI());
    }
}
