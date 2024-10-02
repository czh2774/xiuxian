package com.xiuxian.xiuxianserver.enums;

/**
 * 枚举类，表示角色在国家中的官职。
 * 包含平民以及其他国家职位。
 */
public enum OfficialPositionEnum {
    COMMONER("平民"),   // 平民
    GOVERNOR("太守"),   // 太守
    GENERAL("将军"),   // 将军
    MINISTER("大臣"),  // 大臣
    EMPEROR("皇帝");   // 皇帝

    private final String description;

    OfficialPositionEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
