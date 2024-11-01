package com.xiuxian.xiuxianserver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * 更新角色基本信息 DTO，包含角色的基础和资源信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "更新角色基本信息和资源信息的 DTO")
public class CharacterProfileUpdateDTO {
    @NotNull(message = "Character ID cannot be null")
    @Schema(description = "角色唯一ID，必填")
    private Long characterId;

    // 基础信息字段
    @Schema(description = "角色名称，可选")
    private String name;

    @Schema(description = "角色头像路径，可选")
    private String avatar;

    @Schema(description = "角色所属派系，可选")
    private String faction;

    @Schema(description = "角色等级，可选")
    private Integer level;

    @Schema(description = "角色战斗力，可选")
    private Integer combatPower;

    @Schema(description = "角色头衔等级，可选")
    private Integer titleLevel;

    @Schema(description = "角色头衔特权，JSON格式，可选")
    private String titlePrivileges;

    @Schema(description = "角色的官方职位，可选")
    private String officialPosition;

    @Schema(description = "角色担任城主的城市ID，可选")
    private Long governorCityId;

    @Schema(description = "角色当前状态，可选")
    private String status;

    // 资源信息字段
    @Schema(description = "元宝数量，可选")
    private Integer yuanbao;

    @Schema(description = "战功数量，可选")
    private Integer warMerits;

    @Schema(description = "声望值，可选")
    private Integer reputation;

    @Schema(description = "铜币数量，可选")
    private Integer copperCoins;

    @Schema(description = "粮食数量，可选")
    private Integer food;

    @Schema(description = "木材数量，可选")
    private Integer wood;

    @Schema(description = "铁矿数量，可选")
    private Integer ironOre;

    // 仅供显示的时间戳字段
    @Schema(description = "角色创建时间，自动填充")
    private LocalDateTime createdAt;

    @Schema(description = "角色最后更新时间，自动填充")
    private LocalDateTime updatedAt;
}
