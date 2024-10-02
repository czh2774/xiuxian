package com.xiuxian.xiuxianserver.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 兵种类型枚举类，定义前军和后军的兵种种类。
 */
@Schema(description = "兵种类型枚举")
public enum TroopType {

    @Schema(description = "步兵")
    INFANTRY,

    @Schema(description = "骑兵")
    CAVALRY,

    @Schema(description = "弓兵")
    ARCHER,

    @Schema(description = "枪兵")
    SPEARMAN
}
