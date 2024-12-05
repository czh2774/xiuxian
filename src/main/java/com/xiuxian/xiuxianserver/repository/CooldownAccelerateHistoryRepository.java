package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.CooldownAccelerateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CooldownAccelerateHistoryRepository extends JpaRepository<CooldownAccelerateHistory, Long> {
} 