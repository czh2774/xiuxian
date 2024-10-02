package com.xiuxian.xiuxianserver.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 城市等级枚举类，定义不同城市的等级。
 */
@Schema(description = "城市等级枚举")
public enum CityLevelEnum {
    COUNTY("县城"),
    PREFECTURE("郡城"),
    PASS("关城"),
    CAPITAL("都城"),
    METROPOLIS("首都");

    private final String description;

    CityLevelEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
