package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.UserDTO;
import com.xiuxian.xiuxianserver.entity.UserModel;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserModel findByPlatformUserId(Long platformUserId);
    UserModel createUser(UserModel user);
    UserModel updateUser(UserModel user);
    UserModel createNewUser(UserDTO userDTO);

    void deleteUser(Long playerId);

    UserDetails loadUserByPlatformUserId(Long platformUserId);


}
