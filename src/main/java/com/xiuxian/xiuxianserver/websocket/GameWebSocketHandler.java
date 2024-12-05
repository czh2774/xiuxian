package com.xiuxian.xiuxianserver.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiuxian.xiuxianserver.dto.websocket.WebSocketMessage;
import com.xiuxian.xiuxianserver.enums.WebSocketMessageType;
import com.xiuxian.xiuxianserver.websocket.handler.WebSocketMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameWebSocketHandler extends TextWebSocketHandler {
    
    private final WebSocketSessionManager sessionManager;
    private final ObjectMapper objectMapper;
    private final Map<String, WebSocketMessageHandler> messageHandlers;
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long userId = (Long) session.getAttributes().get("platformUserId");
        sessionManager.addSession(userId, session);
        log.info("WebSocket connection established for user: {}", userId);
        
        // 发送连接成功消息
        sendMessage(userId, createMessage("CONNECTED", "Connection established"));
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            Long userId = (Long) session.getAttributes().get("platformUserId");
            Long characterId = getCurrentCharacterId(session);
            
            WebSocketMessage<?> wsMessage = objectMapper.readValue(message.getPayload(), WebSocketMessage.class);
            log.debug("Received message from user {}, character {}: {}", userId, characterId, wsMessage);
            
            if ("PING".equals(wsMessage.getType())) {
                handlePing(userId);
                return;
            }
            
            // 找到合适的处理器处理消息
            WebSocketMessageHandler handler = messageHandlers.get(wsMessage.getType());
            if (handler != null) {
                handler.handle(userId, characterId, wsMessage);
            } else {
                log.warn("No handler found for message type: {}", wsMessage.getType());
            }
            
        } catch (Exception e) {
            Long userId = (Long) session.getAttributes().get("platformUserId");
            sendErrorMessage(userId, "Error handling message: " + e.getMessage());
        }
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        Long userId = (Long) session.getAttributes().get("platformUserId");
        log.error("Transport error for user {}: ", userId, exception);
        sessionManager.removeSession(userId);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = (Long) session.getAttributes().get("platformUserId");
        sessionManager.removeSession(userId);
        log.info("WebSocket connection closed for user: {} with status: {}", userId, status);
    }
    
    public void sendMessage(Long userId, WebSocketMessage<?> message) {
        sessionManager.sendMessage(userId, message);
    }
    
    private void handlePing(Long userId) {
        sendMessage(userId, createMessage("PONG", System.currentTimeMillis()));
    }


    private <T> WebSocketMessage<T> createMessage(String type, T payload) {
        WebSocketMessage<T> message = new WebSocketMessage<>();
        message.setType(type);
        message.setPayload(payload);
        message.setTimestamp(System.currentTimeMillis());
        message.setMessageId(generateMessageId());
        return message;
    }
    
    private String generateMessageId() {
        return java.util.UUID.randomUUID().toString();
    }
    
    private Long getCurrentCharacterId(WebSocketSession session) {
        Long characterId = (Long) session.getAttributes().get("currentCharacterId");
        if (characterId == null) {
            throw new IllegalStateException("No character selected");
        }
        return characterId;
    }
    
    public void sendErrorMessage(Long userId, String message) {
        WebSocketMessage<String> errorResponse = new WebSocketMessage<>();
        errorResponse.setType(WebSocketMessageType.ERROR.name());
        errorResponse.setPayload(message);
        sendMessage(userId, errorResponse);
    }
} 