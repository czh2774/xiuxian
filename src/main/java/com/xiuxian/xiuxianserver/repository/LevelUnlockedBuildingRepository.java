package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.LevelUnlockedBuilding;
import com.xiuxian.xiuxianserver.entity.LevelUnlockedBuildingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelUnlockedBuildingRepository extends JpaRepository<LevelUnlockedBuilding, LevelUnlockedBuildingId> {
    List<LevelUnlockedBuilding> findByLevel(Integer level);
} 