package com.xiuxian.xiuxianserver.dto.websocket.cooldown;

import com.xiuxian.xiuxianserver.enums.CooldownType;
import lombok.Data;

@Data
public class CooldownStartRequest {
    private CooldownType type;
    private Long targetId;
    private int queueId;
    private int durationInSeconds;
} 