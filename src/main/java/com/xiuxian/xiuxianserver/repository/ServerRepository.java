package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 服务器仓库接口，用于数据库操作
 */
public interface ServerRepository extends JpaRepository<Server, Long> {

    /**
     * 按创建时间倒序获取所有服务器
     *
     * @return 服务器列表
     */
    List<Server> findAllByOrderByCreatedAtDesc();
}
