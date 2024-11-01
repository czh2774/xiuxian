package com.xiuxian.xiuxianserver.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 枚举类定义道具的稀有度。
 */
@Schema(description = "稀有度枚举")
public enum RarityEnum {

    @Schema(description = "普通")
    COMMON("普通", 1, "#FFFFFF"),

    @Schema(description = "稀有")
    RARE("稀有", 2, "#00FF00"),

    @Schema(description = "史诗")
    EPIC("史诗", 3, "#FF00FF"),

    @Schema(description = "传奇")
    LEGENDARY("传奇", 4, "#FFD700");

    private final String displayName;
    private final int level;
    private final String fontColor;

    RarityEnum(String displayName, int level, String fontColor) {
        this.displayName = displayName;
        this.level = level;
        this.fontColor = fontColor;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getLevel() {
        return level;
    }

    public String getFontColor() {
        return fontColor;
    }

    public static RarityEnum fromDisplayName(String displayName) {
        for (RarityEnum rarity : values()) {
            if (rarity.getDisplayName().equals(displayName)) {
                return rarity;
            }
        }
        throw new IllegalArgumentException("未知的稀有度: " + displayName);
    }
}
