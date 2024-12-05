package com.xiuxian.xiuxianserver.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class WebSocketSessionManager {
    private final ConcurrentHashMap<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Long> lastHeartbeats = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final ObjectMapper objectMapper;
    
    private static final long HEARTBEAT_TIMEOUT = 30000; // 30秒
    
    public WebSocketSessionManager(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        // 动心跳检查任务
        scheduler.scheduleAtFixedRate(this::checkHeartbeats, 10, 10, TimeUnit.SECONDS);
    }
    
    public void addSession(Long userId, WebSocketSession session) {
        sessions.put(userId, session);
        updateHeartbeat(userId);
        log.info("Session added for user: {}", userId);
    }
    
    public void removeSession(Long userId) {
        WebSocketSession session = sessions.remove(userId);
        lastHeartbeats.remove(userId);
        if (session != null && session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                log.error("Error closing session for user {}: ", userId, e);
            }
        }
        log.info("Session removed for user: {}", userId);
    }
    
    public WebSocketSession getSession(Long userId) {
        return sessions.get(userId);
    }
    
    public boolean hasSession(Long userId) {
        return sessions.containsKey(userId);
    }
    
    public void updateHeartbeat(Long userId) {
        lastHeartbeats.put(userId, System.currentTimeMillis());
    }
    
    private void checkHeartbeats() {
        long now = System.currentTimeMillis();
        lastHeartbeats.forEach((userId, lastHeartbeat) -> {
            if (now - lastHeartbeat > HEARTBEAT_TIMEOUT) {
                log.warn("Heartbeat timeout for user: {}", userId);
                removeSession(userId);
            }
        });
    }
    
    public void sendMessage(Long userId, com.xiuxian.xiuxianserver.dto.websocket.WebSocketMessage<?> message) {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String textMessage = objectMapper.writeValueAsString(message);
                session.sendMessage(new TextMessage(textMessage));
            } catch (Exception e) {
                log.error("Error sending message to user {}: ", userId, e);
                removeSession(userId);
            }
        }
    }
} 