package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.dto.CharacterProfileDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;

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
    private Long id; // 角色唯一ID

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
    @Builder.Default
    private int level = 1; // 角色等级

    @Column(nullable = true, length = 255)
    @Schema(description = "角色的头像路径", example = "/images/avatar.png")
    @Builder.Default
    private String avatar = "/images/avatar.png"; // 角色头像路径

    @Column(nullable = false)
    @Schema(description = "角色的战斗力", example = "1500")
    @Builder.Default
    private int combatPower = 0; // 角色战斗力

    @Column(nullable = false)
    @Schema(description = "角色的头衔等级", example = "3")
    @Builder.Default
    private int titleLevel = 1; // 角色头衔等级

    @Column(columnDefinition = "JSON", nullable = true)
    @Schema(description = "头衔特权，JSON格式", example = "{\"bonus\":\"10%\"}")
    private String titlePrivileges; // 头衔特权（JSON格式）

    @Column(nullable = false)
    @Schema(description = "角色的官方职位", example = "Governor")
    @Builder.Default
    private String officialPosition = "Governor"; // 角色的官方职位

    @Column(nullable = true)
    @Schema(description = "角色担任城主的城市ID", example = "3001")
    private Long governorCityId; // 角色担任城主的城市ID

    // 资源信息
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的元宝数量", example = "1000")
    @Builder.Default
    private int yuanbao = 0; // 元宝数量

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的战功数量", example = "200")
    @Builder.Default
    private int warMerits = 0; // 战功数量

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的声望值", example = "1500")
    @Builder.Default
    private int reputation = 0; // 声望值

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的铜币数量", example = "5000")
    @Builder.Default
    private int copperCoins = 5000; // 铜币数量

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的粮食数量", example = "3000")
    @Builder.Default
    private int food = 3000; // 粮食数量

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的木材数量", example = "2000")
    @Builder.Default
    private int wood = 2000; // 木材数量

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Schema(description = "角色的铁矿数量", example = "1000")
    @Builder.Default
    private int ironOre = 1000; // 铁矿数量

    @Column(nullable = false)
    @Schema(description = "最大建筑升级队列数量", example = "2")
    @Builder.Default
    private int maxBuildingUpgradeQueues = 2; // 最大建筑升级队列数量

    @Column(nullable = false)
    @Schema(description = "当前使用的建筑升级队列数量", example = "1")
    @Builder.Default
    private int currentBuildingUpgradeQueues = 0; // 当前使用的建筑升级队列数量

    @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'Active'")
    @Schema(description = "角色当前状态", example = "Active")
    @Builder.Default
    private String status = "Active"; // 角色当前状态

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Schema(description = "角色创建时间", example = "2024-01-01T12:00:00")
    private LocalDateTime createdAt; // 角色创建时间

    @CreationTimestamp
    @Column(nullable = false)
    @Schema(description = "角色最后更新时间", example = "2024-01-02T15:00:00")
    private LocalDateTime updatedAt; // 角色最后更新时间

    // 转换为 DTO 的方法
    public CharacterProfileDTO toDTO() {
        CharacterProfileDTO dto = new CharacterProfileDTO();
        dto.setId(this.id);
        dto.setPlayerId(this.playerId);
        dto.setName(this.name);
        dto.setFaction(this.faction);
        dto.setLevel(this.level);
        dto.setAvatar(this.avatar);
        dto.setCombatPower(this.combatPower);
        dto.setTitleLevel(this.titleLevel);
        dto.setTitlePrivileges(this.titlePrivileges);
        dto.setOfficialPosition(this.officialPosition);
        dto.setGovernorCityId(this.governorCityId);
        dto.setYuanbao(this.yuanbao);
        dto.setWarMerits(this.warMerits);
        dto.setReputation(this.reputation);
        dto.setCopperCoins(this.copperCoins);
        dto.setFood(this.food);
        dto.setWood(this.wood);
        dto.setIronOre(this.ironOre);
        dto.setStatus(this.status);
        dto.setCreatedAt(this.createdAt);
        dto.setUpdatedAt(this.updatedAt);

        // 添加建筑队列字段
        dto.setMaxBuildingUpgradeQueues(this.maxBuildingUpgradeQueues);
        dto.setCurrentBuildingUpgradeQueues(this.currentBuildingUpgradeQueues);

        return dto;
    }

}

