package com.xiuxian.xiuxianserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.annotation.PostConstruct;  // 新增
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;  // 新增
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableTransactionManagement  // 添加事务管理支持
@EnableJpaRepositories(basePackages = "com.xiuxian.xiuxianserver.repository")  // 扫描仓库
@EntityScan(basePackages = "com.xiuxian.xiuxianserver.entity")  // 扫描实体类
@EnableScheduling  // 启用定时任务
public class XiuxianServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiuxianServerApplication.class, args);
    }

    // 使用 @PostConstruct 确保时区在应用启动时被设置
    @PostConstruct
    public void init(){
        // 设置 JVM 全局时区为 UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println("Spring Boot 应用启动，默认时区为: " + TimeZone.getDefault().getID());
    }

}
