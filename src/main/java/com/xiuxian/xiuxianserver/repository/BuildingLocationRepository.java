package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.BuildingLocationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingLocationRepository extends JpaRepository<BuildingLocationTemplate, Long> {
    // JpaRepository 提供了 findAll() 方法
}
