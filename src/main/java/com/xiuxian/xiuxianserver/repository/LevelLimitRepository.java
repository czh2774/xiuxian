package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.LevelLimit;
import com.xiuxian.xiuxianserver.entity.LevelLimitId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelLimitRepository extends JpaRepository<LevelLimit, LevelLimitId> {
    List<LevelLimit> findByLevel(Integer level);
} 