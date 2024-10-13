package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色信息传输对象，用于展示完整角色信息")
public class CharacterProfileDTO {
    @NotNull
    @Schema(description = "角色唯一ID")
    private Long characterId; // 角色唯一ID

    @Schema(description = "所属玩家的token")
    private String token; // 所属玩家token

    @Schema(description = "角色名称")
    private String name; // 角色名称

    @Schema(description = "角色所属势力")
    private String faction; // 角色所属势力

    @Schema(description = "角色等级")
    private int level; // 角色等级

    @Schema(description = "角色头像信息")
    private String avatar; // 角色头像信息

    @Schema(description = "角色的战斗力值")
    private int combatPower; // 角色的战斗力值

    @Schema(description = "角色的爵位等级")
    private int titleLevel; // 角色的爵位等级

    @Schema(description = "角色爵位等级对应的特权列表，JSON格式")
    private String titlePrivileges; // 角色爵位等级对应的特权列表

    @Schema(description = "角色的国家官职")
    private String officialPosition; // 角色的国家官职

    @Schema(description = "角色担任太守的城市ID")
    private Long governorCityId; // 角色担任太守的城市ID

    @Schema(description = "元宝")
    private int yuanbao; // 元宝

    @Schema(description = "战功")
    private int warMerits; // 战功

    @Schema(description = "声望")
    private int reputation; // 声望

    @Schema(description = "铜币")
    private int copperCoins; // 铜币

    @Schema(description = "粮食")
    private int food; // 粮食

    @Schema(description = "木材")
    private int wood; // 木材

    @Schema(description = "铁矿")
    private int ironOre; // 铁矿

    @Schema(description = "角色状态")
    private String status; // 角色状态

    @Schema(description = "创建时间")
    private LocalDateTime createdAt; // 创建时间

    @Schema(description = "最后更新时间")
    private LocalDateTime updatedAt; // 最后更新时间
    public CharacterProfileDTO(CharacterProfile profile) {

        this.characterId = profile.getCharacterId();         // 角色唯一ID
        this.token = profile.getToken();               // 关联玩家token
        this.name = profile.getName();                       // 角色名称
        this.faction = profile.getFaction();                 // 角色派系
        this.level = profile.getLevel();                     // 角色等级
        this.avatar = profile.getAvatar();                   // 角色头像
        this.combatPower = profile.getCombatPower();         // 角色战斗力
        this.titleLevel = profile.getTitleLevel();           // 头衔等级
        this.titlePrivileges = profile.getTitlePrivileges(); // 头衔特权
        this.officialPosition = profile.getOfficialPosition(); // 官方职位
        this.governorCityId = profile.getGovernorCityId();   // 担任城主的城市ID
        this.yuanbao = profile.getYuanbao();                 // 元宝数量
        this.warMerits = profile.getWarMerits();             // 战功数量
        this.reputation = profile.getReputation();           // 声望值
        this.copperCoins = profile.getCopperCoins();         // 铜币数量
        this.food = profile.getFood();                       // 粮食数量
        this.wood = profile.getWood();                       // 木材数量
        this.ironOre = profile.getIronOre();                 // 铁矿数量
        this.status = profile.getStatus();                   // 角色状态
        this.createdAt = profile.getCreatedAt();             // 创建时间，类型为 LocalDateTime
        this.updatedAt = profile.getUpdatedAt();             // 更新时间，类型为 LocalDateTime
    }

}
