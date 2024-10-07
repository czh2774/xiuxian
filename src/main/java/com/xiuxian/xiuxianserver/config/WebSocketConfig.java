package com.xiuxian.xiuxianserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 配置类，启用 STOMP 消息代理，并配置 WebSocket 端点。
 */
@Configuration
@EnableWebSocketMessageBroker  // 启用 WebSocket 消息代理
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 配置消息代理，启用简单的内存消息代理，订阅 "/topic" 前缀的消息
        config.enableSimpleBroker("/topic");
        // 配置应用程序的消息前缀，所有发送到控制器的消息需要以 "/app" 开头
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册 WebSocket 端点，允许 SockJS 回退选项并设置允许的跨域来源
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:8080")  // 设置允许的来源
                .withSockJS();  // 启用 SockJS 回退选项，支持不支持 WebSocket 的浏览器
    }
}
