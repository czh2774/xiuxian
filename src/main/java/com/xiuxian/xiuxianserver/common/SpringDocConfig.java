package com.xiuxian.xiuxianserver.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    // 自定义 OpenAPI 信息
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Xiuxian Server API")
                        .version("v1")
                        .description("This is the API documentation for Xiuxian Server")
                        .contact(new Contact()
                                .name("Support Team")
                                .url("http://your-support-url.com")
                                .email("support@xiuxian.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }

    // 定义 API 组
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .packagesToScan("com.xiuxian.xiuxianserver.controller") // 只扫描你自己控制器的包
                .pathsToMatch("/api/**") // 仅匹配 "/api" 开头的路径
                .build();
    }
}
