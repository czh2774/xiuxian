package com.xiuxian.xiuxianserver.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 枚举类定义宝箱类型，如随机宝箱、固定宝箱、多选宝箱等。
 */
@Schema(description = "宝箱类型枚举")
public enum ChestTypeEnum {

    @Schema(description = "随机宝箱")
    RANDOM("随机宝箱", "random"),

    @Schema(description = "固定宝箱")
    FIXED("固定宝箱", "fixed"),

    @Schema(description = "多选宝箱")
    MULTI_CHOICE("多选宝箱", "multiChoice");

    private final String displayName;
    private final String typeCode;

    ChestTypeEnum(String displayName, String typeCode) {
        this.displayName = displayName;
        this.typeCode = typeCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public static ChestTypeEnum fromDisplayName(String displayName) {
        for (ChestTypeEnum chestType : values()) {
            if (chestType.getDisplayName().equals(displayName)) {
                return chestType;
            }
        }
        throw new IllegalArgumentException("未知的宝箱类型: " + displayName);
    }
}
