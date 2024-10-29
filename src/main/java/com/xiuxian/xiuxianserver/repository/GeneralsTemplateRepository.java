package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.GeneralsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * GeneralsTemplateRepository接口，继承JpaRepository，
 * 提供与数据库进行CRUD（创建、读取、更新、删除）操作的功能。
 */
public interface GeneralsTemplateRepository extends JpaRepository<GeneralsTemplate, Long> {
    // 继承JpaRepository后，已经具备了基本的CRUD操作（如save, findById, findAll, delete等），无需额外定义方法
}
