package com.xiuxian.xiuxianserver.enums;

/**
 * 建筑功能类型枚举
 * 定义建筑可能拥有的功能入口类型
 */
public enum BuildingFeatureType {
    TRAIN("训练"),
    RESEARCH("研究"),
    BREAKTHROUGH("突破"),
    FORMATION("布阵"),
    BATTLE_ARRAY("沙盘演义"),
    ARENA("比武大会"),
    CONQUEST("群雄逐鹿"),
    CHALLENGE("过关斩将"),
    FORGE("制作"),
    REFINE("洗练"),
    ENHANCE("强化"),
    RECRUIT("招募"),
    OBSERVE_STARS("观星"),
    BREAK_STARS("碎星"),
    ENLIGHTENMENT("问道"),
    TECH("科技"),
    TITLE("爵位"),
    GOVERNMENT("幕府"),
    ACHIEVEMENT("成就"),
    RESOURCE_TRADE("购买资源");

    private final String description;

    BuildingFeatureType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 