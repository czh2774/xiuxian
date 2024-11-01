package com.xiuxian.xiuxianserver.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Xiuxian Server API",
                version = "1.0",
                description = "This is the API documentation for Xiuxian Server"
        ),
        security = @SecurityRequirement(name = "bearerAuth")  // 全局安全配置
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Xiuxian Server API")
                        .version("1.0")
                        .description("This is the API documentation for Xiuxian Server")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@xiuxian.com")
                                .url("https://your-support-url.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")  // 定义组名
                .pathsToMatch("/api/**")  // 仅匹配 /api 开头的路径
                .build();
    }

    @Bean
    public OpenApiCustomizer customerGlobalHeaderOpenApiCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {
                    // 添加自定义的请求头
                    operation.addParametersItem(new Parameter()
                            .in("header")
                            .name("X-Custom-Header")
                            .description("Custom header for all requests")
                            .required(false)
                            .schema(new StringSchema()));
                })
        );
    }
}
