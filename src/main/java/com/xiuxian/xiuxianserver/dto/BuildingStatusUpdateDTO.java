package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.enums.BuildingStatusType;

/**
 * BuildingStatusUpdateDTO
 * 用于更新角色建筑状态的请求体
 */
public class BuildingStatusUpdateDTO {

    private Long buildingId;  // 建筑实例ID
    private BuildingStatusType newStatus;  // 新的状态，使用枚举类型

    // Constructors, Getters and Setters
    public BuildingStatusUpdateDTO() {}

    public BuildingStatusUpdateDTO(Long buildingId, BuildingStatusType newStatus) {
        this.buildingId = buildingId;
        this.newStatus = newStatus;
    }

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
