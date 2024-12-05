package com.xiuxian.xiuxianserver.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketMessageConfig {
    
    @Bean
    public ObjectMapper webSocketObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // 配置序列化选项
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        // 注册模块，支持Java 8日期时间类型
        mapper.registerModule(new JavaTimeModule());
        
        return mapper;
    }
} 