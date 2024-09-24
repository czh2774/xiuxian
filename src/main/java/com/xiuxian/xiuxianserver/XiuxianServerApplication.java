package com.xiuxian.xiuxianserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.xiuxian.xiuxianserver.repository")  // 扫描仓库
@EntityScan(basePackages = "com.xiuxian.xiuxianserver.entity")  // 扫描实体类
public class XiuxianServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiuxianServerApplication.class, args);
    }

}
