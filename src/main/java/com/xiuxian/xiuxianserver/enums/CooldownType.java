package com.xiuxian.xiuxianserver.enums;

public enum CooldownType {
    BUILDING_UPGRADE("建筑升级"),
    TECH_RESEARCH("科技研究"),
    EQUIPMENT_BREAKTHROUGH("装备突破");
    
    private final String description;
    
    CooldownType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
} 