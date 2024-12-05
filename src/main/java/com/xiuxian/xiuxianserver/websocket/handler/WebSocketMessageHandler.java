package com.xiuxian.xiuxianserver.websocket.handler;

import com.xiuxian.xiuxianserver.dto.websocket.WebSocketMessage;

public interface WebSocketMessageHandler {
    void handle(Long userId, Long characterId, WebSocketMessage<?> message);
    boolean supports(String messageType);
} 