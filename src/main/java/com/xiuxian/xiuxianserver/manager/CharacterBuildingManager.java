package com.xiuxian.xiuxianserver.manager;

import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.entity.BuildingUpgrade;
import com.xiuxian.xiuxianserver.entity.Cooldown;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import com.xiuxian.xiuxianserver.enums.CooldownStatus;
import com.xiuxian.xiuxianserver.repository.CharacterBuildingRepository;
import com.xiuxian.xiuxianserver.repository.BuildingUpgradeRepository;
import com.xiuxian.xiuxianserver.repository.CooldownRepository;
import com.xiuxian.xiuxianserver.service.CooldownCompletionCallback;
import com.xiuxian.xiuxianserver.service.ValidationService;
import com.xiuxian.xiuxianserver.service.CooldownService;
import com.xiuxian.xiuxianserver.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import lombok.RequiredArgsConstructor;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import com.xiuxian.xiuxianserver.repository.CharacterProfileRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.xiuxian.xiuxianserver.enums.ResourceType;
import com.xiuxian.xiuxianserver.dto.response.MainCityUpgradeResponse;
import com.xiuxian.xiuxianserver.mapper.BuildingMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class CharacterBuildingManager implements CooldownCompletionCallback {
    private static final Logger logger = LoggerFactory.getLogger(CharacterBuildingManager.class);

    private final CharacterBuildingRepository characterBuildingRepository;
    private final BuildingUpgradeRepository buildingUpgradeRepository;
    private final ValidationService validationService;
    private final ResourceManager resourceManager;
    private final Snowflake snowflake;
    private final CooldownService cooldownService;
    private final CooldownRepository cooldownRepository;
    private final CharacterProfileRepository characterProfileRepository;
    private final CharacterLevelManager characterLevelManager;
    private final BuildingMapper buildingMapper;

    @PostConstruct
    public void init() {
        cooldownService.registerCallback(CooldownType.BUILDING_UPGRADE, this);
    }

    @Override
    public void onCooldownComplete(Cooldown cooldown) {
        try {
            // 只需要更新建筑状态和队列数量
            CharacterBuilding building = characterBuildingRepository.findById(cooldown.getTargetId())
                .orElseThrow(() -> new BusinessException("建筑不存在"));
                
            building.setBuildingStatus(BuildingStatusType.IDLE);
            building.setActionStartTime(null);
            characterBuildingRepository.save(building);
            
            // 减少队列数量
            CharacterProfile character = characterProfileRepository.findById(cooldown.getCharacterId())
                .orElseThrow(() -> new BusinessException("角色不存在"));
                
            character.setCurrentBuildingUpgradeQueues(character.getCurrentBuildingUpgradeQueues() - 1);
            characterProfileRepository.save(character);
            
        } catch (Exception e) {
            log.error("Failed to complete building upgrade - buildingId: {}, error: {}", 
                cooldown.getTargetId(), e.getMessage(), e);
        }
    }

    /**
     * 开始升级建筑
     */
    @Transactional
    public CharacterBuilding startUpgradeBuilding(Long buildingId) {
        // 1. 获取建筑和升级信息
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
            .orElseThrow(() -> new BusinessException("建筑不存在"));
            
        // 2. 检查建筑状态
        if (building.getBuildingStatus() != BuildingStatusType.IDLE) {
            String currentStatus = building.getBuildingStatus().getDescription();
            throw new BusinessException(
                String.format("建筑当前状态为[%s]，只有空闲状态的建筑才能升级", currentStatus)
            );
        }

        // 3. 检查角色等级限制（主城除外）
        if (building.getBuildingTemplateId() != 1001L) {
            CharacterProfile character = characterProfileRepository.findById(building.getCharacterId())
                .orElseThrow(() -> new BusinessException("角色不存在"));
                
            int nextLevel = building.getCurrentLevel() + 1;
            if (nextLevel > character.getLevel()) {
                throw new BusinessException("建筑等级不能超过角色等级");
            }
        }
        
        // 3. 检查队列上限
        CharacterProfile character = characterProfileRepository.findById(building.getCharacterId())
            .orElseThrow(() -> new BusinessException("角色不存在"));
            
        if (character.getCurrentBuildingUpgradeQueues() >= character.getMaxBuildingUpgradeQueues()) {
            throw new BusinessException("建筑升级队列已满");
        }
            
        // 4. 获取可用的队列ID
        int queueId = getAvailableQueueId(building.getCharacterId());
        if (queueId == -1) {
            throw new BusinessException("没有可用的升级队列");
        }
            
        BuildingUpgrade nextLevelUpgrade = buildingUpgradeRepository
            .findByBuildingTemplateIdAndLevel(building.getBuildingTemplateId(), building.getCurrentLevel() + 1)
            .orElseThrow(() -> new BusinessException("已达到最大等级"));
            
        // 3. 验证和扣除资源
        validateUpgradeResources(building.getCharacterId(), nextLevelUpgrade);
        
        // 记录资源变化
        Map<String, Integer> resourceChanges = new HashMap<>();
        resourceChanges.put("food", -nextLevelUpgrade.getFoodCost());
        resourceChanges.put("wood", -nextLevelUpgrade.getWoodCost());
        resourceChanges.put("ironOre", -nextLevelUpgrade.getIronCost());
        
        // 扣除资源
        deductUpgradeResources(building.getCharacterId(), nextLevelUpgrade);
        
        // 立即提升建筑等级
        building.setCurrentLevel(building.getCurrentLevel() + 1);
        building.setBuildingStatus(BuildingStatusType.UPGRADING);
        building.setActionStartTime(LocalDateTime.now());
        building.setLastModifiedTime(LocalDateTime.now());
        
        // 启动冷却，用于跟踪建筑状态恢复
        cooldownService.startCooldown(
            building.getCharacterId(),
            CooldownType.BUILDING_UPGRADE,
            buildingId,
            nextLevelUpgrade.getUpgradeTime(),
            queueId
        );
        
        // 更新队列数量
        character.setCurrentBuildingUpgradeQueues(character.getCurrentBuildingUpgradeQueues() + 1);
        characterProfileRepository.save(character);
        
        return characterBuildingRepository.save(building);
    }

    /**
     * 完成建筑升级
     */
    @Transactional
    public MainCityUpgradeResponse completeUpgrade(Long buildingId) {
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new BusinessException("建筑不存在"));
            
        // 完成升级
        building.setCurrentLevel(building.getCurrentLevel() + 1);
        building.setBuildingStatus(BuildingStatusType.IDLE);
        building.setActionStartTime(null);
        building.setLastModifiedTime(LocalDateTime.now());
        
        MainCityUpgradeResponse response = new MainCityUpgradeResponse();
        Map<String, Integer> resourceChanges = new HashMap<>();

        // 记录资源消耗
        BuildingUpgrade upgrade = buildingUpgradeRepository
            .findByBuildingTemplateIdAndLevel(building.getBuildingTemplateId(), building.getCurrentLevel())
            .orElseThrow(() -> new BusinessException("升级配置不存在"));

        resourceChanges.put("food", -upgrade.getFoodCost());
        resourceChanges.put("wood", -upgrade.getWoodCost());
        resourceChanges.put("ironOre", -upgrade.getIronCost());
        
        response.setResourceChanges(resourceChanges);
        
        // 如果是主城建筑(ID: 1001)，同步提升角色等级
        if (building.getBuildingTemplateId() == 1001L) {
            // 处理等提升，获取新建筑和奖励
            CharacterLevelManager.LevelUpResult levelUpResult = characterLevelManager.levelUp(
                building.getCharacterId(), 
                building.getCurrentLevel()
            );
            
            // ��置响应数据
            response.setCharacter(levelUpResult.getCharacterInfo());
            response.setBuildings(levelUpResult.getNewBuildings());
            response.setRewards(levelUpResult.getRewards());
        }
        
        // 设置升级后的建筑信息
        response.setBuilding(buildingMapper.toBuildingInfo(building));
        
        characterBuildingRepository.save(building);
        return response;
    }

    /**
     * 验证升级所需资源
     */
    private void validateUpgradeResources(Long characterId, BuildingUpgrade upgrade) {
        // 验证粮食
        if (!resourceManager.hasSufficientResources(characterId, ResourceType.FOOD.getFieldName(), upgrade.getFoodCost())) {
            throw new BusinessException("粮食不足");
        }
        // 验证木材
        if (!resourceManager.hasSufficientResources(characterId, ResourceType.WOOD.getFieldName(), upgrade.getWoodCost())) {
            throw new BusinessException("木材不足");
        }
        // 验证铁矿
        if (!resourceManager.hasSufficientResources(characterId, ResourceType.IRON_ORE.getFieldName(), upgrade.getIronCost())) {
            throw new BusinessException("铁矿不足");
        }
    }

    /**
     * 扣除升级需资源
     */
    private void deductUpgradeResources(Long characterId, BuildingUpgrade upgrade) {
        Map<ResourceType, Integer> resourceCosts = new HashMap<ResourceType, Integer>();
        resourceCosts.put(ResourceType.FOOD, upgrade.getFoodCost());
        resourceCosts.put(ResourceType.WOOD, upgrade.getWoodCost());
        resourceCosts.put(ResourceType.IRON_ORE, upgrade.getIronCost());

        resourceCosts.forEach((resourceType, amount) -> {
            if (amount > 0) {
                resourceManager.deductResources(characterId, resourceType.getFieldName(), amount);
                logger.debug("扣除资源 - 角色ID: {}, 资源类型: {}, 数量: {}", 
                    characterId, resourceType.getDescription(), amount);
            }
        });
    }

    /**
     * 创建建筑实例
     */
    @Transactional
    public CharacterBuilding createBuilding(Long characterId, Long buildingTemplateId, Long locationId) {
        logger.info("创建角色筑实例，角色ID: {}, 模板ID: {}", characterId, buildingTemplateId);

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

    private int getAvailableQueueId(Long characterId) {
        // 获取角色当前的级队列数量
        int maxQueues = 2; // 最大队列数，可以从配置或角色属性获取
        List<Cooldown> activeCooldowns = cooldownRepository
            .findByCharacterIdAndTypeAndStatus(
                characterId, 
                CooldownType.BUILDING_UPGRADE, 
                CooldownStatus.ACTIVE
            );
            
        // 找到未使用的最小队��ID
        Set<Integer> usedQueueIds = activeCooldowns.stream()
            .map(Cooldown::getQueueId)
            .collect(Collectors.toSet());
            
        for (int i = 1; i <= maxQueues; i++) {
            if (!usedQueueIds.contains(i)) {
                return i;
            }
        }
        
        return -1; // 没有可用队列
    }
}