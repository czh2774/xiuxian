package com.xiuxian.xiuxianserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 配置类，启用 STOMP 消息代理，并配置 WebSocket 端点。
 */
@Configuration
@EnableWebSocketMessageBroker  // 启用 WebSocket 消息代理
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final HandshakeInterceptor handshakeInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 配置消息代理，启用简单的内存消息代理，订阅 "/topic" 前缀的消息
        config.enableSimpleBroker("/topic");
        // 配置应用程序的消息前缀，所有发送到控制器的消息需要以 "/app" 开头
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(handshakeInterceptor)
                .setAllowedOrigins("http://localhost:8080")
                .withSockJS();
    }
}
