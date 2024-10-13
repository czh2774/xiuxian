package com.xiuxian.xiuxianserver.config;

import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeIdConfig {

    @Bean
    public cn.hutool.core.lang.Snowflake snowflake() {
        // 数据中心ID（0~31），机器ID（0~31），根据需求设置
        return IdUtil.getSnowflake(1, 1);  // 数据中心ID=1，机器ID=1
    }
}
