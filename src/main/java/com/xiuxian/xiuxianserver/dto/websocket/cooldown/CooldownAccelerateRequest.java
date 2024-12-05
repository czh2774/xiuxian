package com.xiuxian.xiuxianserver.dto.websocket.cooldown;

import com.xiuxian.xiuxianserver.enums.CooldownType;
import com.xiuxian.xiuxianserver.enums.AccelerateItemType;
import lombok.Data;

@Data
public class CooldownAccelerateRequest {
    private Long characterId;
    private CooldownType type;
    private Long targetId;
    private AccelerateItemType itemType;
    private int itemCount;
    private int queueId;
} 