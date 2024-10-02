package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.enums.OfficialPositionEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色表，存储与玩家角色相关的基础信息、资源及官职信息。
 */
@Data
@Entity
@Table(name = "character_profile")
public class CharacterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long characterId; // 角色唯一ID

    @Column(nullable = false)
    private Long playerId; // 所属玩家ID

    @Column(nullable = false, length = 255)
    private String name; // 角色名称

    @Column(nullable = false, length = 50)
    private String faction; // 角色所属势力（枚举类型）

    @Column(nullable = false)
    private int level; // 角色等级，用于在世界地图展示

    @Column(nullable = true, length = 255)
    private String avatar; // 角色头像信息（可为空，用于在世界地图展示）

    @Column(nullable = false)
    private int combatPower; // 角色的战斗力值

    @Column(nullable = false)
    private int titleLevel; // 角色的爵位等级

    @Column(columnDefinition = "JSON", nullable = true)
    private String titlePrivileges; // 角色爵位等级对应的特权列表，JSON 格式（可为空）

    @Column(nullable = false)
    private OfficialPositionEnum officialPosition; // 角色的国家官职（枚举类）

    @Column(nullable = true)
    private Long governorCityId; // 角色担任太守的城市ID（同一时间只能担任一个城市的太守，可为空）

    // 资源字段
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int yuanbao; // 元宝

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int warMerits; // 战功

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int reputation; // 声望

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int copperCoins; // 铜币

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int food; // 粮食

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int wood; // 木材

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int ironOre; // 铁矿

    @Column(nullable = false, length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'normal'")
    private String status; // 角色状态（默认“normal”）

    @Column(nullable = false)
    private LocalDateTime createdAt; // 创建时间

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 最后更新时间
}
