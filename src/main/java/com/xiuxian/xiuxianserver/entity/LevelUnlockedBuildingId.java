package com.xiuxian.xiuxianserver.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class LevelUnlockedBuildingId implements Serializable {
    private Integer level;
    private Long buildingTemplateId;
} 