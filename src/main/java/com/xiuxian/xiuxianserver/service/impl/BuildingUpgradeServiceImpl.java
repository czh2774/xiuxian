package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.BuildingUpgradeDTO;
import com.xiuxian.xiuxianserver.entity.BuildingTemplate;
import com.xiuxian.xiuxianserver.entity.BuildingUpgrade;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.BuildingUpgradeRepository;
import com.xiuxian.xiuxianserver.service.BuildingUpgradeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BuildingUpgradeServiceImpl 实现了建筑升级相关的业务逻辑。
 */
@Service
@RequiredArgsConstructor
public class BuildingUpgradeServiceImpl implements BuildingUpgradeService {

    private static final Logger logger = LoggerFactory.getLogger(BuildingUpgradeServiceImpl.class);

    private final BuildingUpgradeRepository buildingUpgradeRepository;

    /**
     * 获取所有建筑升级信息。
     *
     * @return 返回建筑升级DTO的列表
     */
    @Override
    public List<BuildingUpgradeDTO> getAllBuildingUpgrades() {
        logger.info("开始获取所有建筑升级信息");

        List<BuildingUpgrade> upgrades = buildingUpgradeRepository.findAll();

        // 将实体列表转换为DTO列表
        List<BuildingUpgradeDTO> upgradeDTOs = upgrades.stream()
                .map(this::convertToDTO)  // 使用单一参数的 convertToDTO 方法
                .collect(Collectors.toList());

        logger.info("成功获取所有建筑升级信息，共 {} 条记录", upgradeDTOs.size());

        return upgradeDTOs;
    }

    /**
     * 根据模板ID和等级获取建筑升级信息。
     *
     * @param buildingTemplateId 建筑模板ID
     * @param level 升级的等级
     * @return 返回指定模板ID和等级的建筑升级DTO
     * @throws ResourceNotFoundException 如果未找到对应的升级信息
     */
    @Override
    public BuildingUpgradeDTO getBuildingUpgradeByTemplateIdAndLevel(Long buildingTemplateId, int level) {
        logger.info("根据模板ID: {} 和等级: {} 获取建筑升级信息", buildingTemplateId, level);

        BuildingUpgrade upgrade = buildingUpgradeRepository.findByBuildingTemplateIdAndLevel(buildingTemplateId, level)
                .orElseThrow(() -> {
                    logger.error("未找到建筑升级信息，模板ID: {}, 等级: {}", buildingTemplateId, level);
                    return new ResourceNotFoundException("未找到建筑升级信息，模板ID: " + buildingTemplateId + ", 等级: " + level);
                });

        logger.info("成功获取建筑升级信息，模板ID: {}, 等级: {}", buildingTemplateId, level);

        // 将实体转换为DTO
        return convertToDTO(upgrade);
    }

    /**
     * 将 BuildingUpgrade 实体转换为 DTO。
     *
     * @param upgrade 实体对象
     * @return DTO对象
     */
    private BuildingUpgradeDTO convertToDTO(BuildingUpgrade upgrade) {
        return new BuildingUpgradeDTO(
                upgrade.getId(),
                upgrade.getBuildingTemplateId(),  // 使用 buildingTemplateId 关联
                upgrade.getLevel(),
                upgrade.getUpgradeTime(),
                upgrade.getFoodCost(),
                upgrade.getWoodCost(),
                upgrade.getIronCost()
        );
    }

}
