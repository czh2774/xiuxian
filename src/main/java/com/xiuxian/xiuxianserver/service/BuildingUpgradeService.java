package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.BuildingUpgradeDTO;

import java.util.List;

public interface BuildingUpgradeService {

    /**
     * 获取所有建筑升级信息。
     *
     * @return 返回建筑升级DTO的列表
     */
    List<BuildingUpgradeDTO> getAllBuildingUpgrades();

    /**
     * 根据模板ID和等级获取建筑升级信息。
     *
     * @param buildingTemplateId 建筑模板ID
     * @param level 升级的等级
     * @return 返回指定模板ID和等级的建筑升级DTO
     */
    BuildingUpgradeDTO getBuildingUpgradeByTemplateIdAndLevel(Long buildingTemplateId, int level);
}
