package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * CharacterBuildingIdRequestDTO
 * 用于根据建筑实例ID获取建筑的请求对象
 */
@Schema(description = "建筑实例ID请求对象")
public class CharacterBuildingIdRequestDTO {

    @Schema(description = "建筑实例ID", example = "1001", required = true)
    private Long buildingId;  // 建筑实例ID

    // 无参构造函数
    public CharacterBuildingIdRequestDTO() {
    }

    // 有参构造函数
    public CharacterBuildingIdRequestDTO(Long buildingId) {
        this.buildingId = buildingId;
    }

    // Getters 和 Setters

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }
}
