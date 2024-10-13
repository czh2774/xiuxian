package com.xiuxian.xiuxianserver.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ItemCategory 枚举类
 * 定义道具的背包标签类型
 */
@Schema(description = "道具背包标签类型")
public enum ItemCategory {

    @Schema(description = "道具")
    ITEM,

    @Schema(description = "技能碎片")
    SKILL_FRAGMENT,

    @Schema(description = "军械碎片")
    ARMAMENT_FRAGMENT,

    @Schema(description = "宝物碎片")
    TREASURE_FRAGMENT,

    @Schema(description = "特产")
    SPECIALITY
}
