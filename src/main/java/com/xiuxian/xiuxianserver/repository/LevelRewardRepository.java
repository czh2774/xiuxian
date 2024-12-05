package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.LevelReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelRewardRepository extends JpaRepository<LevelReward, Long> {
    List<LevelReward> findByLevel(Integer level);
} 