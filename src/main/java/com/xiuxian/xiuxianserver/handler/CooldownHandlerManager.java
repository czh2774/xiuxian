package com.xiuxian.xiuxianserver.handler;

import com.xiuxian.xiuxianserver.entity.Cooldown;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CooldownHandlerManager {
    private final Map<CooldownType, CooldownCompletionHandler> handlers;
    
    public CooldownHandlerManager(List<CooldownCompletionHandler> handlerList) {
        handlers = handlerList.stream()
            .collect(Collectors.toMap(
                handler -> Arrays.stream(CooldownType.values())
                    .filter(handler::supports)
                    .findFirst()
                    .orElseThrow(),
                Function.identity()
            ));
    }
    
    public void handleCooldownComplete(Cooldown cooldown) {
        CooldownCompletionHandler handler = handlers.get(cooldown.getType());
        if (handler != null) {
            handler.onCooldownComplete(cooldown);
        }
    }
} 