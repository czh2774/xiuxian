package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * BuildingUpgradeRequestDTO
 * 用于封装客户端请求传递的建筑模板ID和升级等级。
 */
public class BuildingUpgradeRequestDTO {

    @Schema(description = "建筑模板ID", example = "1")
    private Long buildingTemplateId;

    @Schema(description = "目标升级等级", example = "2")
    private int level;

    // Getters and Setters
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
