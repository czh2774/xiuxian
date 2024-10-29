package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.GeneralsUpgrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * GeneralsUpgradeRepository接口，继承JpaRepository，
 * 提供武将升级记录的CRUD操作。
 */
@Repository
public interface GeneralsUpgradeRepository extends JpaRepository<GeneralsUpgrade, Long> {

    /**
     * 根据武将模板ID查找升级记录
     * @param generalTemplateId 武将模板ID
     * @return 升级记录列表
     */
    List<GeneralsUpgrade> findByGeneralTemplateId(Long generalTemplateId);
}
