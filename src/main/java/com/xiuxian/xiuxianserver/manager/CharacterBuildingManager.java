package com.xiuxian.xiuxianserver.manager;

import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.entity.BuildingUpgrade;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.repository.CharacterBuildingRepository;
import com.xiuxian.xiuxianserver.repository.BuildingUpgradeRepository;
import com.xiuxian.xiuxianserver.service.ValidationService;
import com.xiuxian.xiuxianserver.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Component
public class CharacterBuildingManager {
    private static final Logger logger = LoggerFactory.getLogger(CharacterBuildingManager.class);

    private final CharacterBuildingRepository characterBuildingRepository;
    private final BuildingUpgradeRepository buildingUpgradeRepository;
    private final ValidationService validationService;
    private final ResourceManager resourceManager;
    private final Snowflake snowflake;

    public CharacterBuildingManager(
            CharacterBuildingRepository characterBuildingRepository,
            BuildingUpgradeRepository buildingUpgradeRepository,
            ValidationService validationService,
            ResourceManager resourceManager,
            Snowflake snowflake) {
        this.characterBuildingRepository = characterBuildingRepository;
        this.buildingUpgradeRepository = buildingUpgradeRepository;
        this.validationService = validationService;
        this.resourceManager = resourceManager;
        this.snowflake = snowflake;
    }

    /**
     * 开始升级建筑
     */
    @Transactional
    public CharacterBuilding startUpgradeBuilding(Long buildingId) {
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new BusinessException("建筑不存在"));

        // 检查建筑状态
        if (building.getBuildingStatus() != BuildingStatusType.IDLE) {
            throw new BusinessException("建筑状态异常，无法升级");
        }

        // 获取下一级建筑升级信息
        BuildingUpgrade nextLevelUpgrade = buildingUpgradeRepository
                .findByBuildingTemplateIdAndLevel(building.getBuildingTemplateId(), building.getCurrentLevel() + 1)
                .orElseThrow(() -> new BusinessException("已达到最大等级"));

        // 验证资源是否足够
        validateUpgradeResources(building.getCharacterId(), nextLevelUpgrade);

        // 验证升级条件
        if (!validationService.validateConditions(building.getCharacterId(), nextLevelUpgrade.getUpgradeRequirements())) {
            throw new BusinessException("不满足升级条件");
        }

        // 扣除资源
        deductUpgradeResources(building.getCharacterId(), nextLevelUpgrade);

        // 设置建筑为升级状态
        building.setBuildingStatus(BuildingStatusType.UPGRADING);
        building.setActionStartTime(LocalDateTime.now());

        logger.info("开始升级建筑，建筑ID: {}, 角色ID: {}, 当前等级: {}", 
                   buildingId, building.getCharacterId(), building.getCurrentLevel());

        return characterBuildingRepository.save(building);
    }

    /**
     * 完成建筑升级
     */
    @Transactional
    public CharacterBuilding completeUpgrade(Long buildingId) {
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new BusinessException("建筑不存在"));

        if (building.getBuildingStatus() != BuildingStatusType.UPGRADING) {
            throw new BusinessException("建筑不在升级状态");
        }

        BuildingUpgrade upgrade = buildingUpgradeRepository
                .findByBuildingTemplateIdAndLevel(building.getBuildingTemplateId(), building.getCurrentLevel() + 1)
                .orElseThrow(() -> new BusinessException("升级信息不存在"));

        // 检查升级时间是否已到
        LocalDateTime expectedCompleteTime = building.getActionStartTime().plusSeconds(upgrade.getUpgradeTime());
        if (LocalDateTime.now().isBefore(expectedCompleteTime)) {
            throw new BusinessException("升级尚未完成");
        }

        // 完成升级
        building.setCurrentLevel(building.getCurrentLevel() + 1);
        building.setBuildingStatus(BuildingStatusType.IDLE);
        building.setActionStartTime(null);
        building.setLastModifiedTime(LocalDateTime.now());

        logger.info("完成建筑升级，建筑ID: {}, 角色ID: {}, 新等级: {}", 
                   buildingId, building.getCharacterId(), building.getCurrentLevel());

        return characterBuildingRepository.save(building);
    }

    /**
     * 验证升级所需资源
     */
    private void validateUpgradeResources(Long characterId, BuildingUpgrade upgrade) {
        // 验证粮食
        if (!resourceManager.hasSufficientResources(characterId, "FOOD", upgrade.getFoodCost())) {
            throw new BusinessException("粮食不足");
        }
        // 验证木材
        if (!resourceManager.hasSufficientResources(characterId, "WOOD", upgrade.getWoodCost())) {
            throw new BusinessException("木材不足");
        }
        // 验证铁矿
        if (!resourceManager.hasSufficientResources(characterId, "IRON", upgrade.getIronCost())) {
            throw new BusinessException("铁矿不足");
        }
    }

    /**
     * 扣除升级所需资源
     */
    private void deductUpgradeResources(Long characterId, BuildingUpgrade upgrade) {
        Map<String, Integer> resourceCosts = new HashMap<>();
        resourceCosts.put("FOOD", upgrade.getFoodCost());
        resourceCosts.put("WOOD", upgrade.getWoodCost());
        resourceCosts.put("IRON", upgrade.getIronCost());

        // 遍历并扣除每种资源
        resourceCosts.forEach((resourceType, amount) -> {
            if (amount > 0) {
                resourceManager.deductResources(characterId, resourceType, amount);
                logger.debug("扣除资源 - 角色ID: {}, 资源类型: {}, 数量: {}", 
                           characterId, resourceType, amount);
            }
        });
    }

    /**
     * 创建建筑实例
     */
    @Transactional
    public CharacterBuilding createBuilding(Long characterId, Long buildingTemplateId, Long locationId) {
        logger.info("创建角色建筑实例，角色ID: {}, 模板ID: {}", characterId, buildingTemplateId);

        // 获取建筑一级的升级信息
        BuildingUpgrade initialUpgrade = buildingUpgradeRepository
                .findByBuildingTemplateIdAndLevel(buildingTemplateId, 1)
                .orElseThrow(() -> new BusinessException("建筑模板配置错误，未找到初始等级信息"));

        // 验证资源是否足够
        validateUpgradeResources(characterId, initialUpgrade);

        // 验证建筑前置条件
        if (!validationService.validateConditions(characterId, initialUpgrade.getUpgradeRequirements())) {
            throw new BusinessException("不满足建造条件");
        }

        // 扣除资源
        deductUpgradeResources(characterId, initialUpgrade);

        // 创建建筑实例
        CharacterBuilding building = new CharacterBuilding();
        building.setId(snowflake.nextId());
        building.setCharacterId(characterId);
        building.setBuildingTemplateId(buildingTemplateId);
        building.setLocationId(locationId);
        building.setCurrentLevel(1);
        building.setBuildingStatus(BuildingStatusType.IDLE);
        building.setLastModifiedTime(LocalDateTime.now());

        return characterBuildingRepository.save(building);
    }
}