package com.xiuxian.xiuxianserver.enums;

public enum AccelerateItemType {
    SPEED_UP_5M("5分钟加速", 300, 1001L),
    SPEED_UP_15M("15分钟加速", 900, 1002L),
    SPEED_UP_30M("30分钟加速", 1800, 1003L),
    SPEED_UP_1H("1小时加速", 3600, 1004L),
    SPEED_UP_3H("3小时加速", 10800, 1005L),
    SPEED_UP_8H("8小时加速", 28800, 1006L),
    SPEED_UP_24H("24小时加速", 86400, 1007L);

    private final String description;
    private final int seconds;
    private final Long itemId;

    AccelerateItemType(String description, int seconds, Long itemId) {
        this.description = description;
        this.seconds = seconds;
        this.itemId = itemId;
    }

    public int getSeconds() {
        return seconds;
    }

    public Long getItemId() {
        return itemId;
    }

    public static AccelerateItemType getByItemId(Long itemId) {
        for (AccelerateItemType type : values()) {
            if (type.getItemId().equals(itemId)) {
                return type;
            }
        }
        return null;
    }
} 