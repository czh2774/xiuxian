package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.User;
import com.xiuxian.xiuxianserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 保存用户
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // 通过 OpenID 查找用户
    public Optional<User> findUserByOpenid(String openid) {
        return userRepository.findByOpenid(openid);
    }

    // 查找用户通过 ID
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}
