package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色信息传输对象，用于展示完整角色信息")
public class CharacterProfileDTO {
    @NotNull
    @Schema(description = "角色唯一ID")
    private Long id;

    @Schema(description = "角色所属的用户ID")
    private Long playerId;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色所属势力")
    private String faction;

    @Schema(description = "角色等级")
    private int level;

    @Schema(description = "角色头像信息")
    private String avatar;

    @Schema(description = "角色的战斗力值")
    private int combatPower;

    @Schema(description = "角色的爵位等级")
    private int titleLevel;

    @Schema(description = "角色爵位等级对应的特权列表，JSON格式")
    private String titlePrivileges;

    @Schema(description = "角色的国家官职")
    private String officialPosition;

    @Schema(description = "角色担任太守的城市ID")
    private Long governorCityId;

    @Schema(description = "元宝")
    private int yuanbao;

    @Schema(description = "战功")
    private int warMerits;

    @Schema(description = "声望")
    private int reputation;

    @Schema(description = "铜币")
    private int copperCoins;

    @Schema(description = "粮食")
    private int food;

    @Schema(description = "木材")
    private int wood;

    @Schema(description = "铁矿")
    private int ironOre;

    @Schema(description = "角色状态")
    private String status;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "最后更新时间")
    private LocalDateTime updatedAt;

    // 新增构造函数
    public CharacterProfileDTO(CharacterProfile profile) {
        this.id = profile.getId();
        this.playerId = profile.getPlayerId();
        this.name = profile.getName();
        this.faction = profile.getFaction();
        this.level = profile.getLevel();
        this.avatar = profile.getAvatar();
        this.combatPower = profile.getCombatPower();
        this.titleLevel = profile.getTitleLevel();
        this.titlePrivileges = profile.getTitlePrivileges();
        this.officialPosition = profile.getOfficialPosition();
        this.governorCityId = profile.getGovernorCityId();
        this.yuanbao = profile.getYuanbao();
        this.warMerits = profile.getWarMerits();
        this.reputation = profile.getReputation();
        this.copperCoins = profile.getCopperCoins();
        this.food = profile.getFood();
        this.wood = profile.getWood();
        this.ironOre = profile.getIronOre();
        this.status = profile.getStatus();
        this.createdAt = profile.getCreatedAt();
        this.updatedAt = profile.getUpdatedAt();
    }
}
