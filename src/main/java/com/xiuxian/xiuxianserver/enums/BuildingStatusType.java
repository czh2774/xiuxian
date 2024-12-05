package com.xiuxian.xiuxianserver.enums;

public enum BuildingStatusType {
    IDLE("空闲"),
    CONSTRUCTING("建造中"),
    UPGRADING("升级中"),
    BREAKING_THROUGH("突破中"),
    TRAINING("训练中");

    private final String description;

    BuildingStatusType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
