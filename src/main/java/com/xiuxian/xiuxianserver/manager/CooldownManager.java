package com.xiuxian.xiuxianserver.manager;

import com.xiuxian.xiuxianserver.dto.CooldownDTO;
import com.xiuxian.xiuxianserver.dto.websocket.WebSocketMessage;
import com.xiuxian.xiuxianserver.entity.CooldownAccelerateHistory;
import com.xiuxian.xiuxianserver.enums.AccelerateItemType;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import com.xiuxian.xiuxianserver.enums.WebSocketMessageType;
import com.xiuxian.xiuxianserver.exception.BusinessException;
import com.xiuxian.xiuxianserver.repository.CooldownAccelerateHistoryRepository;
import com.xiuxian.xiuxianserver.service.CooldownService;
import com.xiuxian.xiuxianserver.websocket.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CooldownManager {
    private final CooldownService cooldownService;
    private final ResourceManager resourceManager;
    private final CooldownAccelerateHistoryRepository accelerateHistoryRepository;
    private final WebSocketSessionManager webSocketSessionManager;

    @Transactional
    public boolean accelerateCooldown(Long characterId, CooldownType type, Long targetId, 
            AccelerateItemType itemType, int itemCount, int queueId) {
        
        // 1. 验证并扣除加速道具
        String resourceType = "ITEM_" + itemType.getItemId();
        if (!resourceManager.hasSufficientResources(characterId, resourceType, itemCount)) {
            throw new BusinessException("加速道具不足");
        }
        resourceManager.deductResources(characterId, resourceType, itemCount);

        // 2. 执行加速
        int accelerationTime = itemType.getSeconds() * itemCount;
        cooldownService.accelerateCooldown(characterId, type, targetId, accelerationTime, queueId);

        // 3. 记录加速历史
        CooldownAccelerateHistory history = new CooldownAccelerateHistory(
            characterId, 
            cooldownService.getCooldownStatus(characterId, type, targetId, queueId).getId(),
            itemType, 
            itemCount, 
            accelerationTime
        );
        accelerateHistoryRepository.save(history);

        // 4. 发送WebSocket通知
        CooldownDTO cooldown = cooldownService.getCooldownStatus(characterId, type, targetId, queueId);
        WebSocketMessage<CooldownDTO> message = new WebSocketMessage<>();
        message.setType(WebSocketMessageType.COOLDOWN_ACCELERATE.name());
        message.setPayload(cooldown);
        webSocketSessionManager.sendMessage(characterId, message);

        return true;
    }

    // 可以添加其他跨模块的方法...
} 