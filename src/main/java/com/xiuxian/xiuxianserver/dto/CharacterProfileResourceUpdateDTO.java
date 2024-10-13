package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色资源更新信息传输对象，用于更新角色的资源信息")
public class CharacterProfileResourceUpdateDTO {

    @Schema(description = "角色唯一ID", example = "1", required = true)
    private Long characterId; // 角色唯一ID

    @Schema(description = "元宝数量", example = "100")
    private Integer yuanbao; // 元宝

    @Schema(description = "战功数量", example = "50")
    private Integer warMerits; // 战功

    @Schema(description = "声望", example = "30")
    private Integer reputation; // 声望

    @Schema(description = "铜币", example = "200")
    private Integer copperCoins; // 铜币

    @Schema(description = "粮食", example = "150")
    private Integer food; // 粮食

    @Schema(description = "木材", example = "300")
    private Integer wood; // 木材

    @Schema(description = "铁矿", example = "100")
    private Integer ironOre; // 铁矿

    // 其他资源字段可以继续添加
}
