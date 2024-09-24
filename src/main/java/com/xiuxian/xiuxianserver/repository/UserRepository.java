package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * UserRepository接口，提供对User实体的数据库操作方法。
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOpenid(String openid);
}
