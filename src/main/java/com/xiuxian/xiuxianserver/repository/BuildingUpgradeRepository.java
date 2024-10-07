package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.BuildingUpgrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingUpgradeRepository extends JpaRepository<BuildingUpgrade, Long> {

    Optional<BuildingUpgrade> findByBuildingTemplateIdAndLevel(Long buildingTemplateId, int level);
}
