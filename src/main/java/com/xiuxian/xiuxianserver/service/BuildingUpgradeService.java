package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.BuildingUpgrade;

import java.util.List;

public interface BuildingUpgradeService {

    List<BuildingUpgrade> getAllBuildingUpgrades();

    BuildingUpgrade getBuildingUpgradeByTemplateIdAndLevel(Long buildingTemplateId, int level);
}
