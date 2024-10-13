package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * CharacterBuildingEffect 实体类
 * 包含建筑升级后产生的效果，每个效果作为独立字段
 */
@Data
@NoArgsConstructor  // 无参构造函数
@AllArgsConstructor // 全参构造函数
@Entity
@Builder
@Table(name = "character_building_effects")
@Schema(description = "角色建筑效果实体类，涵盖建筑升级后的所有效果")
public class CharacterBuildingEffect {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @Schema(description = "效果的唯一标识符", example = "UUID格式")
    private String effectId; // 效果唯一ID (UUID)

    @Column(nullable = false)
    @Schema(description = "角色的唯一标识符", example = "UUID格式")
    private String characterId; // 角色ID (关联角色表)

    @Column(nullable = false)
    @Schema(description = "建筑的唯一标识符", example = "UUID格式")
    private String buildingId; // 建筑ID (关联建筑表)

    // 方士相关效果
    @Schema(description = "方士库存上限")
    private double stockCapacity; // 库存容量

    @Schema(description = "方士训练量")
    private double trainingAmount; // 训练量

    @Schema(description = "方士等级提升")
    private double levelIncrease; // 等级提升

    @Schema(description = "方士攻击提升")
    private double attackBoost; // 攻击提升

    @Schema(description = "方士防御提升")
    private double defenseBoost; // 防御提升

    @Schema(description = "方士速度提升")
    private double speedIncrease; // 速度提升

    // 弓兵相关效果
    @Schema(description = "弓兵库存上限")
    private double archerStockCapacity; // 弓兵库存容量

    @Schema(description = "弓兵训练量")
    private double archerTrainingAmount; // 弓兵训练量

    @Schema(description = "弓兵训练速度")
    private double archerTrainingSpeed; // 弓兵训练速度

    // 步兵相关效果
    @Schema(description = "步兵库存上限")
    private double infantryStockCapacity; // 步兵库存容量

    @Schema(description = "步兵训练量")
    private double infantryTrainingAmount; // 步兵训练量

    @Schema(description = "步兵训练速度")
    private double infantryTrainingSpeed; // 步兵训练速度

    // 骑兵相关效果
    @Schema(description = "骑兵库存上限")
    private double cavalryStockCapacity; // 骑兵库存容量

    @Schema(description = "骑兵训练量")
    private double cavalryTrainingAmount; // 骑兵训练量

    @Schema(description = "骑兵训练速度")
    private double cavalryTrainingSpeed; // 骑兵训练速度

    // 提升获得量相关
    @Schema(description = "将魂量提升")
    private double heroSoulGain; // 将魂提升

    // 全兵种相关效果
    @Schema(description = "所有兵种攻击提升")
    private double allTroopAttack; // 所有兵种攻击提升

    @Schema(description = "所有兵种防御提升")
    private double allTroopDefense; // 所有兵种防御提升

    // 资源生产相关效果
    @Schema(description = "粮草生产量提升/每小时")
    private double grainProduction; // 粮草生产

    @Schema(description = "木材生产量提升/每小时")
    private double woodProduction; // 木材生产

    @Schema(description = "生铁生产量提升/每小时")
    private double ironProduction; // 生铁生产

    @Schema(description = "银两生产量提升/每小时")
    private double silverProduction; // 银两生产

    // 其他效果
    @Schema(description = "每日小酿次数")
    private double dailySmallTrainingTimes; // 每日小酿次数

    @Schema(description = "每日珍宝商店购买次数")
    private double dailyTreasureStorePurchase; // 每日珍宝商店购买次数

    @Schema(description = "地图移动速度提升")
    private double mapMovementSpeed; // 地图移动速度

    @Schema(description = "封地建筑等级上限提升")
    private double landBuildingLevelLimit; // 封地建筑等级上限

    @Schema(description = "英雄等级上限提升")
    private double heroLevelLimit; // 英雄等级上限

    @Schema(description = "英雄前军兵力提升")
    private double heroFrontArmyBoost; // 英雄前军兵力提升

    @Schema(description = "英雄后军兵力提升")
    private double heroBackArmyBoost; // 英雄后军兵力提升

    // 资源购买相关
    @Schema(description = "购买银两基础量提升")
    private double silverPurchaseAmount; // 银两购买基础量

    @Schema(description = "购买粮草基础量提升")
    private double grainPurchaseAmount; // 粮草购买基础量

    @Schema(description = "购买木材基础量提升")
    private double woodPurchaseAmount; // 木材购买基础量

    @Schema(description = "购买生铁基础量提升")
    private double ironPurchaseAmount; // 生铁购买基础量

    @Schema(description = "每日购买次数上限提升")
    private double dailyPurchaseTimes; // 每日购买次数

    @Column(nullable = false, updatable = false)
    @Schema(description = "效果创建时间")
    private LocalDateTime createdAt; // 记录创建时间

    @Column(nullable = false)
    @Schema(description = "效果最后更新时间")
    private LocalDateTime updatedAt; // 记录更新时间
}
