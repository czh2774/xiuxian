package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    // 根据平台用户ID查找用户
    UserModel findByPlatformUserId(Long platformUserId);

}
