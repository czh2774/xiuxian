package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.UserDTO;
import com.xiuxian.xiuxianserver.entity.UserModel;
import com.xiuxian.xiuxianserver.service.UserService;
import com.xiuxian.xiuxianserver.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CustomApiResponse<UserModel>> initOrCreateUserGame() {
        // 获取已通过认证的用户信息
        UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("已认证用户: 平台用户ID = {}", userDTO.getPlatformUserId());

        // 查找用户
        UserModel user = userService.findByPlatformUserId(userDTO.getPlatformUserId());

        if (user == null) {
            logger.info("用户未找到，创建新用户");
            try {
                user = userService.createNewUser(userDTO);
                logger.info("新用户创建成功: 用户ID = {}", user.getPlatformUserId());
            } catch (Exception e) {
                logger.error("用户创建过程中出现错误: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new CustomApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "用户创建失败", null, "/init"));
            }
        } else {
            logger.debug("用户已存在: 用户ID = {}", user.getPlatformUserId());
        }

        return ResponseEntity.ok(new CustomApiResponse<>(HttpStatus.OK.value(), "游戏开始成功", user, "/init"));
    }
}
