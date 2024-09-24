package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LoginLogRepository接口，提供对LoginLog实体的数据库操作方法。
 */
public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
}
