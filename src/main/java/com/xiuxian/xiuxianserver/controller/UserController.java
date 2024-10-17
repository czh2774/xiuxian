package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.entity.UserModel;
import com.xiuxian.xiuxianserver.exception.AuthenticationException;
import com.xiuxian.xiuxianserver.exception.InvalidParameterException;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.service.UserService;
import com.xiuxian.xiuxianserver.util.ApiResponse;
import com.xiuxian.xiuxianserver.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * UserController 负责处理用户相关的 API 请求。
 *
 * 本控制器包含获取用户信息的相关方法，
 * 并通过 JWT 令牌验证用户身份。
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 获取用户信息。
     *
     * @return 用户信息或 404 状态
     */
    @Operation(summary = "获取用户信息")
    @PostMapping("/getUser")
    public ResponseEntity<ApiResponse<UserModel>> getUser() {
        String token = extractTokenFromContext();
        try {
            logger.info("开始处理用户请求，获取 JWT Token: {}", token);

            // 使用 getClaim 提取 platformUserId
            Long platformUserId = jwtTokenUtil.getClaim(token, "platformUserId", Long.class);

            // 仅验证 Token 的有效性，不与具体用户信息耦合
            if (!jwtTokenUtil.isTokenValid(token)) {
                logger.warn("无效的 JWT Token: {}", token);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("UNAUTHORIZED", "JWT Token 无效或已过期", null, "/getUser"));
            }
            UserModel user = userService.findByPlatformUserId(platformUserId);
            if (user != null) {
                logger.info("成功获取用户信息: {}", user.getName());
                return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "用户信息获取成功", user, "/getUser"));
            } else {
                logger.warn("未找到用户信息，platformUserId: {}", platformUserId);
                throw new ResourceNotFoundException("用户未找到");
            }
        } catch (AuthenticationException | InvalidParameterException e) {
            logger.error("处理用户请求时发生错误: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null, "/getUser"));
        }
    }

    // 提取 JWT Token 方法
    private String extractTokenFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getCredentials().toString() : null;
    }
}
