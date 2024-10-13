package com.xiuxian.xiuxianserver.enums;

public enum UpgradeEffectType {

    // 方士相关效果
    STOCK_CAPACITY("方士库存"),
    TRAINING_AMOUNT("方士训练量"),
    LEVEL_INCREASE("方士等级"),
    ATTACK_BOOST("方士攻击"),
    DEFENSE_BOOST("方士防御"),
    SPEED_INCREASE("方士速度"),

    // 弓兵相关效果
    ARCHER_STOCK_CAPACITY("弓兵库存"),
    ARCHER_TRAINING_AMOUNT("弓兵训练量"),
    ARCHER_TRAINING_SPEED("弓兵训练速度"),

    // 步兵相关效果
    INFANTRY_STOCK_CAPACITY("步兵库存"),
    INFANTRY_TRAINING_AMOUNT("步兵训练量"),
    INFANTRY_TRAINING_SPEED("步兵训练速度"),

    // 骑兵相关效果
    CAVALRY_STOCK_CAPACITY("骑兵库存"),
    CAVALRY_TRAINING_AMOUNT("骑兵训练量"),
    CAVALRY_TRAINING_SPEED("骑兵训练速度"),

    // 提升获得量相关
    HERO_SOUL_GAIN("将魂量提升"),  // 提高获取的将魂量

    // 全兵种相关效果
    ALL_TROOP_ATTACK("所有兵种攻击提升"),  // 增加所有兵种的攻击
    ALL_TROOP_DEFENSE("所有兵种防御提升"),  // 增加所有兵种的防御

    // 资源生产相关效果
    GRAIN_PRODUCTION("粮草生产/每小时"),
    WOOD_PRODUCTION("木材生产/每小时"),
    IRON_PRODUCTION("生铁生产/每小时"),
    SILVER_PRODUCTION("银两生产/每小时"),

    // 其他效果
    DAILY_SMALL_TRAINING_TIMES("每日小酌次数"),
    DAILY_TREASURE_STORE_PURCHASE("珍宝商店每日购买次数"),
    MAP_MOVEMENT_SPEED("地图上部队移动速度"),
    LAND_BUILDING_LEVEL_LIMIT("封地建筑等级上限"),
    HERO_LEVEL_LIMIT("英雄等级上限"),
    HERO_FRONT_ARMY_BOOST("英雄前军兵力"),
    HERO_BACK_ARMY_BOOST("英雄后军兵力"),

    // 资源购买相关
    SILVER_PURCHASE_AMOUNT("购买银两基础量"),
    GRAIN_PURCHASE_AMOUNT("购买粮草基础量"),
    WOOD_PURCHASE_AMOUNT("购买木材基础量"),
    IRON_PURCHASE_AMOUNT("购买生铁基础量"),
    DAILY_PURCHASE_TIMES("每日购买次数");

    private final String displayName;

    UpgradeEffectType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
