package com.xiuxian.xiuxianserver.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * GameFeature 枚举类
 * 表示游戏中的各种功能，并关联对应的客户端图标
 */
@Schema(description = "游戏功能枚举类，表示不同的游戏功能及其对应的客户端图标")
public enum GameFeature {

    @Schema(description = "升级功能", example = "UPGRADE")
    UPGRADE("upgrade_icon.png"),

    @Schema(description = "训练功能", example = "TRAIN")
    TRAIN("train_icon.png"),

    @Schema(description = "研究功能", example = "RESEARCH")
    RESEARCH("research_icon.png"),

    @Schema(description = "招募功能", example = "RECRUIT")
    RECRUIT("recruit_icon.png"),

    @Schema(description = "制作功能", example = "CRAFT")
    CRAFT("craft_icon.png"),

    @Schema(description = "突破功能", example = "BREAKTHROUGH")
    BREAKTHROUGH("breakthrough_icon.png"),

    @Schema(description = "洗练功能", example = "PURIFY")
    PURIFY("purify_icon.png"),

    @Schema(description = "科技功能", example = "TECHNOLOGY")
    TECHNOLOGY("technology_icon.png"),

    @Schema(description = "群雄逐鹿功能", example = "HERO_WAR")
    HERO_WAR("hero_war_icon.png"),

    @Schema(description = "比武大会功能", example = "TOURNAMENT")
    TOURNAMENT("tournament_icon.png"),

    @Schema(description = "过关斩将功能", example = "TRIAL")
    TRIAL("trial_icon.png"),

    @Schema(description = "沙盘演义功能", example = "SANDBOX")
    SANDBOX("sandbox_icon.png"),

    @Schema(description = "观星功能", example = "STARGAZING")
    STARGAZING("stargazing_icon.png"),

    @Schema(description = "碎星功能", example = "STAR_SHATTER")
    STAR_SHATTER("star_shatter_icon.png"),

    @Schema(description = "问道功能", example = "SEEK_WISDOM")
    SEEK_WISDOM("seek_wisdom_icon.png"),

    @Schema(description = "购买功能", example = "PURCHASE")
    PURCHASE("purchase_icon.png");

    // 存储客户端图标的路径或标识符
    @Schema(description = "客户端图标的路径或标识符")
    private final String iconPath;

    // 构造函数，用于为每个枚举项设置对应的图标路径
    GameFeature(String iconPath) {
        this.iconPath = iconPath;
    }

    // 获取客户端图标路径的方法
    public String getIconPath() {
        return iconPath;
    }
}
