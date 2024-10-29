package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TaskTemplateRepository 接口，继承 JpaRepository
 * 提供对 TaskTemplate 实体的数据库操作
 */
@Repository
public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
    // 可以根据需求在这里添加自定义的查询方法
}
