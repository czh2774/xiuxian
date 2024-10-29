package com.xiuxian.xiuxianserver.dto;

import jakarta.validation.constraints.NotNull;

/**
 * BuildingTemplateIdRequestDTO
 * 用于封装请求时的建筑模板ID
 */
public class BuildingTemplateIdRequestDTO {

    @NotNull(message = "建筑模板ID不能为空")
    private Long buildingTemplateId;

    // Constructors
    public BuildingTemplateIdRequestDTO() {
    }

    public BuildingTemplateIdRequestDTO(Long buildingTemplateId) {
        this.buildingTemplateId = buildingTemplateId;
    }

    // Getters and Setters
    public Long getBuildingTemplateId() {
        return buildingTemplateId;
    }

    public void setBuildingTemplateId(Long buildingTemplateId) {
        this.buildingTemplateId = buildingTemplateId;
    }
}
