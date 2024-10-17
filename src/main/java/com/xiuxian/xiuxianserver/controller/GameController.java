package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.UserDTO;
import com.xiuxian.xiuxianserver.entity.UserModel;
import com.xiuxian.xiuxianserver.service.UserService;
import com.xiuxian.xiuxianserver.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Operation(summary = "游戏初始化接口，验证用户并返回用户信息")
    @PostMapping("/init")
    public ResponseEntity<ApiResponse<?>> initOrCreateUserGame() {
        // 获取已通过认证的用户信息
        UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 查找用户
        UserModel user = userService.findByPlatformUserId(userDTO.getPlatformUserId());

        if (user == null) {
            logger.info("用户未找到，创建新用户");
            try {
                user = userService.createNewUser(userDTO);
                logger.info("新用户创建成功: 用户ID = {}", user.getPlatformUserId());
            } catch (Exception e) {
                logger.error("用户创建过程中出现错误: {}", e.getMessage(), e);
                return ResponseEntity.status(500).body(new ApiResponse<>("USER_CREATION_ERROR", "用户创建失败", null, "/game/init"));
            }
        } else {
            logger.debug("用户已存在: 用户ID = {}", user.getPlatformUserId());
        }

        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "游戏开始成功", user, "/game/init"));
    }
}
