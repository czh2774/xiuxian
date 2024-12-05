package com.xiuxian.xiuxianserver.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiuxian.xiuxianserver.dto.CooldownDTO;
import com.xiuxian.xiuxianserver.dto.websocket.WebSocketMessage;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import com.xiuxian.xiuxianserver.enums.WebSocketMessageType;
import com.xiuxian.xiuxianserver.service.CooldownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.xiuxian.xiuxianserver.dto.websocket.cooldown.CooldownStartRequest;
import com.xiuxian.xiuxianserver.dto.websocket.cooldown.CooldownAccelerateRequest;
import com.xiuxian.xiuxianserver.websocket.WebSocketSessionManager;
import com.xiuxian.xiuxianserver.manager.CooldownManager;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CooldownMessageHandler implements WebSocketMessageHandler {
    
    private final CooldownService cooldownService;
    private final CooldownManager cooldownManager;
    private final WebSocketSessionManager sessionManager;
    private final ObjectMapper objectMapper;
    
    @Override
    public void handle(Long userId, Long characterId, WebSocketMessage<?> message) {
        // 处理冷却相关消息
    }
    
    @Override
    public boolean supports(String messageType) {
        try {
            WebSocketMessageType type = WebSocketMessageType.valueOf(messageType);
            return type == WebSocketMessageType.COOLDOWN_START
                || type == WebSocketMessageType.COOLDOWN_SYNC
                || type == WebSocketMessageType.COOLDOWN_ACCELERATE;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    public void handleCooldownComplete(Long characterId, CooldownType type, Long targetId, int queueId) {
        try {
            CooldownDTO cooldown = cooldownService.getCooldownStatus(characterId, type, targetId, queueId);
            
            WebSocketMessage<CooldownDTO> response = new WebSocketMessage<>();
            response.setType(WebSocketMessageType.COOLDOWN_COMPLETE.name());
            response.setPayload(cooldown);
            
            sessionManager.sendMessage(characterId, response);
            
        } catch (Exception e) {
            log.error("Error handling cooldown complete for character {} type {} targetId {}: ", 
                characterId, type, targetId, e);
            sendErrorMessage(characterId, "Failed to process cooldown completion");
        }
    }
    
    private void handleCooldownStart(Long characterId, WebSocketMessage<?> message) {
        try {
            // 反序列化请求
            CooldownStartRequest request = objectMapper.convertValue(
                message.getPayload(), 
                CooldownStartRequest.class
            );
            
            // 启动冷却
            CooldownDTO cooldown = cooldownService.startCooldown(
                characterId,
                request.getType(),
                request.getTargetId(),
                request.getDurationInSeconds(),
                request.getQueueId()
            );
            
            // 发送响应
            WebSocketMessage<CooldownDTO> response = new WebSocketMessage<>();
            response.setType(WebSocketMessageType.COOLDOWN_START.name());
            response.setPayload(cooldown);
            sessionManager.sendMessage(characterId, response);
            
        } catch (Exception e) {
            log.error("Error handling cooldown start for character {}: ", characterId, e);
            sendErrorMessage(characterId, "Failed to start cooldown");
        }
    }
    
    private void handleCooldownSync(Long characterId) {
        try {
            List<CooldownDTO> cooldowns = cooldownService.getCharacterCooldowns(characterId);
            
            WebSocketMessage<List<CooldownDTO>> response = new WebSocketMessage<>();
            response.setType(WebSocketMessageType.COOLDOWN_SYNC.name());
            response.setPayload(cooldowns);
            
            sessionManager.sendMessage(characterId, response);
            
        } catch (Exception e) {
            log.error("Error handling cooldown sync for character {}: ", characterId, e);
            sendErrorMessage(characterId, "Failed to sync cooldowns");
        }
    }
    
    private void handleCooldownAccelerate(Long characterId, WebSocketMessage<?> message) {
        try {
            CooldownAccelerateRequest request = objectMapper.convertValue(
                message.getPayload(), 
                CooldownAccelerateRequest.class
            );
            
            boolean success = cooldownManager.accelerateCooldown(
                characterId,
                request.getType(),
                request.getTargetId(),
                request.getItemType(),
                request.getItemCount(),
                request.getQueueId()
            );
            
            if (success) {
                CooldownDTO cooldown = cooldownService.getCooldownStatus(
                    characterId,
                    request.getType(),
                    request.getTargetId(),
                    request.getQueueId()
                );
                
                WebSocketMessage<CooldownDTO> response = new WebSocketMessage<>();
                response.setType(WebSocketMessageType.COOLDOWN_ACCELERATE.name());
                response.setPayload(cooldown);
                sessionManager.sendMessage(characterId, response);
            } else {
                sendErrorMessage(characterId, "Failed to accelerate cooldown");
            }
            
        } catch (Exception e) {
            log.error("Error handling cooldown accelerate for character {}: ", characterId, e);
            sendErrorMessage(characterId, "Failed to accelerate cooldown");
        }
    }
    
    private void sendErrorMessage(Long characterId, String errorMessage) {
        WebSocketMessage<String> errorResponse = new WebSocketMessage<>();
        errorResponse.setType(WebSocketMessageType.ERROR.name());
        errorResponse.setPayload(errorMessage);
        sessionManager.sendMessage(characterId, errorResponse);
    }
    
    public void notifyCooldownComplete(Long characterId, CooldownDTO cooldown) {
        WebSocketMessage<CooldownDTO> message = new WebSocketMessage<>();
        message.setType(WebSocketMessageType.COOLDOWN_COMPLETE.name());
        message.setPayload(cooldown);
        
        sessionManager.sendMessage(characterId, message);
    }
    
    private void sendTypedMessage(Long characterId, WebSocketMessage<?> message) {
        @SuppressWarnings("unchecked")
        WebSocketMessage<Object> typedMessage = (WebSocketMessage<Object>) message;
        sessionManager.sendMessage(characterId, typedMessage);
    }
} 