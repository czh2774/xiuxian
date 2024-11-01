package com.xiuxian.xiuxianserver.dto;

import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

/**
 * 角色资源信息 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色资源信息 DTO，包含角色的资源属性")
public class CharacterProfileResourceInfoDTO {
    @Schema(description = "角色唯一ID")
    private Long characterId; // 角色唯一ID

    @Schema(description = "元宝")
    private int yuanbao; // 元宝

    @Schema(description = "铜币")
    private int copperCoins; // 铜币

    @Schema(description = "粮食")
    private int food; // 粮食

    @Schema(description = "战功")
    private int warMerits; // 战功

    @Schema(description = "声望")
    private int reputation; // 声望

    @Schema(description = "木材")
    private int wood; // 木材

    @Schema(description = "铁矿")
    private int ironOre; // 铁矿
    public CharacterProfileResourceInfoDTO(CharacterProfile profile) {
        this.characterId = profile.getId();             // 角色唯一ID
        this.yuanbao = profile.getYuanbao();         // 元宝数量
        this.warMerits = profile.getWarMerits();     // 战功数量
        this.reputation = profile.getReputation();   // 声望值
        this.copperCoins = profile.getCopperCoins(); // 铜币数量
        this.food = profile.getFood();               // 粮食数量
        this.wood = profile.getWood();               // 木材数量
        this.ironOre = profile.getIronOre();         // 铁矿数量
    }
}
