package com.xiuxian.xiuxianserver.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 武将品级枚举类，定义不同的武将等级。
 */
@Schema(description = "武将品级枚举")
public enum GeneralRankEnum {

    @Schema(description = "良才")
    TALENTED,

    @Schema(description = "名将")
    FAMOUS_GENERAL,

    @Schema(description = "国士")
    NATIONAL_TREASURE,

    @Schema(description = "巾帼")
    HEROINE,

    @Schema(description = "传奇")
    LEGENDARY;
}
