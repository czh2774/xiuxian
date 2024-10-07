package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.entity.BuildingUpgrade;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.BuildingUpgradeRepository;
import com.xiuxian.xiuxianserver.service.BuildingUpgradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BuildingUpgradeServiceImpl 实现了建筑升级相关的业务逻辑。
 */
@Service
@RequiredArgsConstructor
public class BuildingUpgradeServiceImpl implements BuildingUpgradeService {

    private final BuildingUpgradeRepository buildingUpgradeRepository;

    /**
     * 获取所有建筑升级的列表。
     *
     * @return 包含所有建筑升级的列表
     */
    @Override
    public List<BuildingUpgrade> getAllBuildingUpgrades() {
        return buildingUpgradeRepository.findAll();
    }

    /**
     * 根据建筑模板ID和目标升级等级获取建筑升级。
     *
     * @param buildingTemplateId 建筑模板ID
     * @param level 目标升级等级
     * @return 建筑升级实体
     * @throws ResourceNotFoundException 如果建筑升级未找到
     */
    @Override
    public BuildingUpgrade getBuildingUpgradeByTemplateIdAndLevel(Long buildingTemplateId, int level) {
        return buildingUpgradeRepository.findByBuildingTemplateIdAndLevel(buildingTemplateId, level)
                .orElseThrow(() -> new ResourceNotFoundException("建筑升级未找到，模板ID: " + buildingTemplateId + "，等级: " + level));
    }
}
