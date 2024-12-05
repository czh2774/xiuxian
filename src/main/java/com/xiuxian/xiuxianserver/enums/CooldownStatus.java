package com.xiuxian.xiuxianserver.enums;

public enum CooldownStatus {
    ACTIVE("进行中"),
    COMPLETED("已完成"),
    CANCELLED("已取消"),
    ACCELERATED("已加速");

    private final String description;

    CooldownStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 