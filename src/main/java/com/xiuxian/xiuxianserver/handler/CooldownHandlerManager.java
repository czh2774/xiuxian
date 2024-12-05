package com.xiuxian.xiuxianserver.handler;

import com.xiuxian.xiuxianserver.entity.Cooldown;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import jakarta.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CooldownHandlerManager {
    private final List<CooldownCompletionHandler> handlerList;
    private Map<CooldownType, CooldownCompletionHandler> handlers;
    
    @PostConstruct
    public void init() {
        // 初始化处理器映射
        handlers = handlerList.stream()
            .collect(Collectors.toMap(
                handler -> Arrays.stream(CooldownType.values())
                    .filter(handler::supports)
                    .findFirst()
                    .orElseThrow(),
                Function.identity()
            ));
    }
    
    @Transactional
    public void handleCooldownComplete(Cooldown cooldown) {
        CooldownCompletionHandler handler = handlers.get(cooldown.getType());
        if (handler == null) {
            throw new IllegalStateException("未找到冷却类型的处理器: " + cooldown.getType());
        }
        handler.onCooldownComplete(cooldown);
    }
} 