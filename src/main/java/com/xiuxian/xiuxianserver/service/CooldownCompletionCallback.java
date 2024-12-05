package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.Cooldown;

public interface CooldownCompletionCallback {
    void onCooldownComplete(Cooldown cooldown);
} 