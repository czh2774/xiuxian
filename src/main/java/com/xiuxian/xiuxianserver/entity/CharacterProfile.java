package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * 角色表，存储与玩家角色相关的基础信息、资源及官职信息。
 */
@Data
@Entity
@Table(name = "character_profile")
@Schema(description = "角色实体类，包含角色的基本信息和资源")
public class CharacterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @Schema(description = "角色唯一ID")
    private Long characterId; // 角色唯一ID

    @Column(nullable = false)
    @Schema(description = "所属玩家的ID")
    private Long playerId; // 所属玩家ID

    @Column(nullable = false, length = 255)
    @Schema(description = "角色名称")
    private String name; // 角色名称

    @Column(nullable = false, length = 50)
    @Schema(description = "角色所属势力")
    private String faction; // 角色所属势力

    @Column(nullable = false)
    @Schema(description = "角色等级")
    private int level; // 角色等级

    @Column(nullable = true, length = 255)
    @Schema(description = "角色头像信息")
    private String avatar; // 角色头像信息（可为空）

    @Column(nullable = false)
    @Schema(description = "角色的战斗力值")
    private int combatPower; // 角色的战斗力值

    @Column(nullable = false)
    @Schema(description = "角色的爵位等级")
    private int titleLevel; // 角色的爵位等级

    @Column(columnDefinition = "JSON", nullable = true)
    @Schema(description = "角色爵位等级对应的特权列表，JSON格式")
    private String titlePrivileges; // 角色爵位等级对应的特权列表

    @Column(nullable = false)
    @Schema(description = "角色的国家官职")
    private String officialPosition; // 角色的国家官职

    @Column(nullable = true)
    @Schema(description = "角色担任太守的城市ID")
    private Long governorCityId; // 角色担任太守的城市ID

    // 资源字段
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "元宝")
    private int yuanbao; // 元宝

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "战功")
    private int warMerits; // 战功

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "声望")
    private int reputation; // 声望

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "铜币")
    private int copperCoins; // 铜币

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "粮食")
    private int food; // 粮食

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "木材")
    private int wood; // 木材

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "铁矿")
    private int ironOre; // 铁矿

    @Column(nullable = false, length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'normal'")
    @Schema(description = "角色状态")
    private String status; // 角色状态

    @Column(nullable = false)
    @Schema(description = "创建时间")
    private LocalDateTime createdAt; // 创建时间

    @Column(nullable = false)
    @Schema(description = "最后更新时间")
    private LocalDateTime updatedAt; // 最后更新时间
}
