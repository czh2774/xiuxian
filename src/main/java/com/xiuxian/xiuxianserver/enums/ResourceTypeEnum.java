package com.xiuxian.xiuxianserver.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 资源类型枚举类，定义资源点所生产的资源类型。
 */
@Schema(description = "资源类型枚举")
public enum ResourceTypeEnum {
    WOOD("木材"),
    IRON("铁矿"),
    GRAIN("粮食");

    private final String description;

    ResourceTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
