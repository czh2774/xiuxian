package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.Faction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FactionRepository 接口，继承自 JpaRepository，
 * 用于执行与数据库的CRUD操作。
 */
@Repository
public interface FactionRepository extends JpaRepository<Faction, Long> {
}
