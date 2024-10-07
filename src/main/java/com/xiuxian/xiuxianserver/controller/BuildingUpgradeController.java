package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.CustomApiResponse;
import com.xiuxian.xiuxianserver.entity.BuildingUpgrade;
import com.xiuxian.xiuxianserver.service.BuildingUpgradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BuildingUpgradeController 用于处理与建筑升级相关的 API 请求。
 */
@RestController
@RequestMapping("/api/buildings")
public class BuildingUpgradeController {

    @Autowired
    private BuildingUpgradeService buildingUpgradeService;

    /**
     * 获取所有建筑升级的列表。
     *
     * @return 包含所有建筑升级的统一响应
     */
    @Operation(summary = "获取所有建筑升级", description = "返回所有建筑升级的列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取建筑升级列表"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/getAllUpgrades")
    public CustomApiResponse<List<BuildingUpgrade>> getAllBuildingUpgrades() {
        List<BuildingUpgrade> upgrades = buildingUpgradeService.getAllBuildingUpgrades();
        return new CustomApiResponse<>(200, "成功", upgrades);
    }

    /**
     * 根据建筑模板ID和目标升级等级获取建筑升级信息。
     *
     * @param request 包含建筑模板ID和目标等级的请求
     * @return 包含建筑升级详情的统一响应
     */
    @Operation(summary = "根据建筑模板ID和目标升级等级获取建筑升级", description = "根据建筑模板ID和目标升级等级返回建筑升级信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取建筑升级信息"),
            @ApiResponse(responseCode = "404", description = "未找到建筑升级信息"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/getByTemplateIdAndLevel")
    public CustomApiResponse<BuildingUpgrade> getBuildingUpgradeByTemplateIdAndLevel(@RequestBody BuildingUpgradeRequest request) {
        BuildingUpgrade upgrade = buildingUpgradeService.getBuildingUpgradeByTemplateIdAndLevel(request.getBuildingTemplateId(), request.getLevel());
        return new CustomApiResponse<>(200, "成功", upgrade);
    }

    // 请求体类，用于传递参数
    public static class BuildingUpgradeRequest {
        @Schema(description = "建筑模板ID", example = "1")
        private Long buildingTemplateId;

        @Schema(description = "目标升级等级", example = "2")
        private int level;

        // Getter 和 Setter
        public Long getBuildingTemplateId() {
            return buildingTemplateId;
        }

        public void setBuildingTemplateId(Long buildingTemplateId) {
            this.buildingTemplateId = buildingTemplateId;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
