package com.xiuxian.xiuxianserver.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 枚举类定义道具的稀有度。
 */
@Schema(description = "道具稀有度枚举")
public enum RarityEnum {

    @Schema(description = "普通道具")
    COMMON_ITEM,

    @Schema(description = "稀有道具")
    RARE_ITEM,

    @Schema(description = "史诗道具")
    EPIC_ITEM,

    @Schema(description = "传奇道具")
    LEGENDARY_ITEM;
}
