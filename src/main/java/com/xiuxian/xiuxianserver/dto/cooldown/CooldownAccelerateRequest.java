package com.xiuxian.xiuxianserver.dto.cooldown;

import lombok.Data;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import com.xiuxian.xiuxianserver.enums.AccelerateItemType;

@Data
public class CooldownAccelerateRequest {
    private Long characterId;
    private CooldownType type;
    private Long targetId;
    private int queueId;
    private AccelerateItemType itemType;  // 使用的加速道具类型
    private int itemCount;                // 使用的道具数量
} 