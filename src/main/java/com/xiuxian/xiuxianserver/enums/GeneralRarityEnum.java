package com.xiuxian.xiuxianserver.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 武将稀有度枚举类，定义不同武将的稀有等级。
 */
@Schema(description = "武将稀有度枚举")
public enum GeneralRarityEnum {

    @Schema(description = "良才")
    COMMON(1, "Common"),

    @Schema(description = "名将")
    UNCOMMON(2, "Uncommon"),

    @Schema(description = "国士")
    RARE(3, "Rare"),

    @Schema(description = "巾帼")
    EPIC(4, "Epic"),

    @Schema(description = "传奇")
    LEGENDARY(5, "Legendary");

    private final int value;
    private final String displayName;

    GeneralRarityEnum(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * 获取稀有度的整数值
     * @return 稀有度的整数值
     */
    public int getValue() {
        return value;
    }

    /**
     * 获取稀有度的显示名称
     * @return 稀有度的显示名称
     */
    public String getDisplayName() {
        return displayName;
    }
}
