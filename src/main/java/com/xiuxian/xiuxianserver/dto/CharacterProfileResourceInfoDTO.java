package com.xiuxian.xiuxianserver.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 角色资源信息 DTO
 */
@Data
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
}
