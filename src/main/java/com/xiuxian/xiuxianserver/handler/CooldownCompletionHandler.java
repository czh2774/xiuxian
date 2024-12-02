package com.xiuxian.xiuxianserver.handler;

import com.xiuxian.xiuxianserver.entity.Cooldown;
import com.xiuxian.xiuxianserver.enums.CooldownType;

public interface CooldownCompletionHandler {
    void onCooldownComplete(Cooldown cooldown);
    boolean supports(CooldownType type);
} 