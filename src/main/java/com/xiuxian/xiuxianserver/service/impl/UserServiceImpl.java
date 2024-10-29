package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.xiuxian.xiuxianserver.dto.UserDTO;
import com.xiuxian.xiuxianserver.entity.UserModel;
import com.xiuxian.xiuxianserver.repository.UserRepository;
import com.xiuxian.xiuxianserver.service.UserService;
import com.xiuxian.xiuxianserver.util.SnowflakeIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    // 初始化 Snowflake 实例，参数为workerId 和 datacenterId
    private final Snowflake snowflake = IdUtil.getSnowflake(1, 2);
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel findByPlatformUserId(Long platformUserId) {
        return userRepository.findByPlatformUserId(platformUserId);
    }

    @Override
    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public UserModel updateUser(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long playerId) {
        userRepository.deleteById(playerId);
    }

    @Override
    public UserDetails loadUserByPlatformUserId(Long platformUserId) throws UsernameNotFoundException {
        // 使用 platformUserId 查找用户
        UserModel user = userRepository.findByPlatformUserId(platformUserId);

        if (user == null) {
            throw new UsernameNotFoundException("用户未找到，平台用户ID：" + platformUserId);
        }

        // 返回 Spring Security 的 UserDetails 对象
        return new User(user.getName(), "", Collections.emptyList());
    }
    // 生成 playerId 的方法
    private Long generatePlayerId() {
        // 统一调用 SnowflakeIdUtil 来生成 ID
        return SnowflakeIdUtil.generateId();
    }
    // 创建新用户的方法
    public UserModel createNewUser(UserDTO userDTO) {
        UserModel newUser = UserModel.builder()
                .id(userDTO.getPlayId() != null ? userDTO.getPlayId() : generatePlayerId())  // 使用 DTO 中的 playId 或生成新 playId
                .platformUserId(userDTO.getPlatformUserId())  // 确保 platformUserId 不为空
                .name(userDTO.getName() != null ? userDTO.getName() : generateDefaultName(userDTO))  // 生成默认名称
                .loginType(userDTO.getLoginType() != null ? userDTO.getLoginType() : "WECHAT")  // 设置默认登录方式
                .enabled(true)  // 默认启用
                .banned(false)  // 默认未被封禁
                .build();

        // 保存新用户到数据库
        return userRepository.save(newUser);
    }

    private String generateDefaultName(UserDTO userDTO) {
        // 将 platformUserId 转换为 String 后，再截取前 5 位
        return "newuser_" + String.valueOf(userDTO.getPlatformUserId()).substring(0, 5);
    }

}
