package com.xiuxian.xiuxianserver.entity;
import cn.hutool.core.util.IdUtil;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * CharacterProfile 实体类
 * 用于存储与角色相关的所有基础信息和资源信息
 */
@Data
@NoArgsConstructor  // 无参构造函数
@AllArgsConstructor // 全参构造函数
@Entity
@Builder
@Table(name = "character_profile")
@Schema(description = "角色档案实体类，存储角色的基础信息和资源信息")
public class CharacterProfile {

    @Id
    @Column(nullable = false, updatable = false)
    @Schema(description = "角色的唯一标识符", example = "1001")
    private Long characterId; // 角色唯一ID

    @Column(nullable = false)
    @Schema(description = "角色所属的用户ID")
    private Long playerId; // 关联的用户ID


    @Column(nullable = false, length = 255)
    @Schema(description = "角色名称", example = "Hero")
    private String name; // 角色名称

    @Column(nullable = false, length = 50)
    @Schema(description = "角色所属的派系", example = "Warrior")
    private String faction; // 角色所属派系

    @Column(nullable = false)
    @Schema(description = "角色等级", example = "10")
    private int level=1; // 角色等级

    @Column(nullable = true, length = 255)
    @Schema(description = "角色的头像路径", example = "/images/avatar.png")
    private String avatar="/images/avatar.png"; // 角色头像路径

    @Column(nullable = false)
    @Schema(description = "角色的战斗力", example = "1500")
    private int combatPower=0; // 角色战斗力

    @Column(nullable = false)
    @Schema(description = "角色的头衔等级", example = "3")
    private int titleLevel=1; // 角色头衔等级

    @Column(columnDefinition = "JSON", nullable = true)
    @Schema(description = "头衔特权，JSON格式", example = "{\"bonus\":\"10%\"}")
    private String titlePrivileges; // 头衔特权（JSON格式）

    @Column(nullable = false)
    @Schema(description = "角色的官方职位", example = "Governor")
    private String officialPosition="Governor"; // 角色的官方职位

    @Column(nullable = true)
    @Schema(description = "角色担任城主的城市ID", example = "3001")
    private Long governorCityId; // 角色担任城主的城市ID

    // 资源信息
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的元宝数量", example = "1000")
    private int yuanbao=0; // 元宝数量

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的战功数量", example = "200")
    private int warMerits=0; // 战功数量

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的声望值", example = "1500")
    private int reputation=0; // 声望值

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的铜币数量", example = "5000")
    private int copperCoins=5000; // 铜币数量

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的粮食数量=3000", example = "3000")
    private int food; // 粮食数量

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的木材数量", example = "2000")
    private int wood=2000; // 木材数量

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的铁矿数量", example = "1000")
    private int ironOre=1000; // 铁矿数量

    @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'Active'")
    @Schema(description = "角色当前状态", example = "Active")
    private String status="Active"; // 角色当前状态

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Schema(description = "角色创建时间", example = "2024-01-01T12:00:00")
    private LocalDateTime createdAt; // 角色创建时间

    @CreationTimestamp
    @Column(nullable = false)
    @Schema(description = "角色最后更新时间", example = "2024-01-02T15:00:00")
    private LocalDateTime updatedAt; // 角色最后更新时间


}
