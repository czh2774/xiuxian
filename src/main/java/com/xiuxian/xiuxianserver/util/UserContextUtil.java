package com.xiuxian.xiuxianserver.util;

import com.xiuxian.xiuxianserver.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextUtil {

    // 静态方法，用于获取当前用户
    public static UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDTO) {
            return (UserDTO) authentication.getPrincipal();
        }
        return null;
    }
}
