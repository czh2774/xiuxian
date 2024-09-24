package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.entity.LoginLog;
import com.xiuxian.xiuxianserver.entity.User;
import com.xiuxian.xiuxianserver.service.LoginLogService;
import com.xiuxian.xiuxianserver.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginLogService loginLogService;

    // 创建用户
    @PostMapping("/create")
    public User createUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    // 查找用户通过 OpenID
    @GetMapping("/findByOpenid")
    public Optional<User> findUserByOpenid(@RequestParam String openid) {
        return userService.findUserByOpenid(openid);
    }

    // 记录登录日志
    @PostMapping("/logLogin")
    public LoginLog logUserLogin(@RequestBody LoginLog loginLog) {
        return loginLogService.saveLoginLog(loginLog);
    }
}
