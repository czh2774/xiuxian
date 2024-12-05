package com.xiuxian.xiuxianserver.enums;

public enum ResourceType {
    FOOD("粮食", "food"),
    WOOD("木材", "wood"),
    IRON_ORE("铁矿", "ironOre"),
    COPPER_COINS("铜钱", "copperCoins"),
    YUANBAO("元宝", "yuanbao"),
    WAR_MERITS("军功", "warMerits"),
    REPUTATION("声望", "reputation");

    private final String description;
    private final String fieldName;  // 对应 CharacterProfile 中的字段名

    ResourceType(String description, String fieldName) {
        this.description = description;
        this.fieldName = fieldName;
    }

    public String getDescription() {
        return description;
    }

    public String getFieldName() {
        return fieldName;
    }
} 