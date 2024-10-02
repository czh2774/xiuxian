package com.xiuxian.xiuxianserver.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 枚举类定义道具的视觉效果，如光圈、光效、火焰特效等。
 */
@Schema(description = "道具视觉效果枚举")
public enum VisualEffectEnum {

    @Schema(description = "无特效")
    NO_EFFECT("无特效", "noEffect"),

    @Schema(description = "光圈")
    AURA("光圈", "auraEffect"),

    @Schema(description = "光效")
    GLOW("光效", "glowEffect"),

    @Schema(description = "火焰特效")
    FLAME("火焰特效", "flameEffect"),

    @Schema(description = "彩虹特效")
    RAINBOW("彩虹特效", "rainbowEffect");

    private final String displayName;
    private final String clientEffectCode;

    VisualEffectEnum(String displayName, String clientEffectCode) {
        this.displayName = displayName;
        this.clientEffectCode = clientEffectCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getClientEffectCode() {
        return clientEffectCode;
    }

    public static VisualEffectEnum fromDisplayName(String displayName) {
        for (VisualEffectEnum effect : values()) {
            if (effect.getDisplayName().equals(displayName)) {
                return effect;
            }
        }
        return NO_EFFECT;
    }
}
