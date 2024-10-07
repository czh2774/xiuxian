package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.BuildingTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BuildingTemplateRepository 用于处理建筑模板的数据访问。
 */
@Repository
public interface BuildingTemplateRepository extends JpaRepository<BuildingTemplate, Long> {
    // JpaRepository 已经提供了常用的数据访问方法
}
