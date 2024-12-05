package com.xiuxian.xiuxianserver.dto.websocket;

import lombok.Data;
import java.util.UUID;

@Data
public class WebSocketMessage<T> {
    private String type;        // 消息类型
    private T payload;          // 消息内容
    private long timestamp = System.currentTimeMillis();
    private String messageId = UUID.randomUUID().toString();
    
    @SuppressWarnings("unchecked")
    public WebSocketMessage<T> cast() {
        return (WebSocketMessage<T>) this;
    }
} 