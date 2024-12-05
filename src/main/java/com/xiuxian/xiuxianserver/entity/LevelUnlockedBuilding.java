package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Data
@Entity
@IdClass(LevelUnlockedBuildingId.class)
public class LevelUnlockedBuilding {
    @Id
    private Integer level;
    @Id
    private Long buildingTemplateId;
} 