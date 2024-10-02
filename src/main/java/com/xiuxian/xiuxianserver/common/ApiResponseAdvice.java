package com.xiuxian.xiuxianserver.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.xiuxian.xiuxianserver.dto.ApiResponse;

/**
 * 全局响应封装器，自动将所有控制器的响应封装为 ApiResponse 类型。
 * 保证了返回数据的一致性和标准化，并增加了日志记录功能。
 */
@ControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger logger = LoggerFactory.getLogger(ApiResponseAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 检查如果响应体为 OpenAPI 文档请求，则跳过封装处理
        String packageName = returnType.getContainingClass().getPackageName();
        if (packageName.startsWith("org.springdoc")) {
            return false;  // 跳过 OpenAPI 文档的响应封装
        }
        // 确保不重复封装已经是 ApiResponse 类型的响应
        return !ApiResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 排除 SpringDoc 相关路径，避免干扰 OpenAPI 文档的生成
        String path = request.getURI().getPath();
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            return body;
        }

        // 如果响应体已经是 ApiResponse 类型，则直接返回
        if (body instanceof ApiResponse) {
            logger.info("Returning existing ApiResponse without modification: {}", body);
            return body;
        }

        // 封装非 ApiResponse 类型的数据，并记录日志
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Success", body);
        logger.info("Wrapping response into ApiResponse: {}", apiResponse);
        return apiResponse;
    }

}

