package com.xiuxian.xiuxianserver.config;

import com.xiuxian.xiuxianserver.util.SnowflakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，负责创建 SnowflakeIdGenerator 的 Bean
 */
@Configuration
public class SnowflakeIdGeneratorConfig {

    private final long dataCenterId = 25; // 数据中心ID，根据实际情况设置
    private final long machineId = 21; // 机器ID，根据实际情况设置

    /**
     * 创建 SnowflakeIdGenerator 的 Bean
     *
     * @return SnowflakeIdGenerator 实例
     */
    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator(dataCenterId, machineId);
    }
}
