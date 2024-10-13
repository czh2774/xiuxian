package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;

/**
 * CharacterBuildingStatusUpdateDTO
 * 用于更新建筑实例状态的请求对象
 */
@Schema(description = "建筑状态更新请求对象")
public class CharacterBuildingStatusUpdateDTO {

    @Schema(description = "建筑实例ID", example = "1001", required = true)
    private Long buildingId;  // 建筑实例ID

    @Schema(description = "新的建筑状态", example = "UPGRADING", required = true)
    private BuildingStatusType newStatus;  // 新的建筑状态

    // 无参构造函数
    public CharacterBuildingStatusUpdateDTO() {
    }

    // 有参构造函数
    public CharacterBuildingStatusUpdateDTO(Long buildingId, BuildingStatusType newStatus) {
        this.buildingId = buildingId;
        this.newStatus = newStatus;
    }

    // Getters 和 Setters

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public BuildingStatusType getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(BuildingStatusType newStatus) {
        this.newStatus = newStatus;
    }
}
