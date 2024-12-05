package com.xiuxian.xiuxianserver.manager;

import com.xiuxian.xiuxianserver.entity.*;
import com.xiuxian.xiuxianserver.repository.*;
import com.xiuxian.xiuxianserver.exception.BusinessException;
import com.xiuxian.xiuxianserver.enums.RewardType;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.xiuxian.xiuxianserver.service.BuildingLocationService;
import cn.hutool.core.lang.Snowflake;
import lombok.Data;
import com.xiuxian.xiuxianserver.dto.response.CharacterInfo;
import com.xiuxian.xiuxianserver.dto.response.BuildingInfo;
import com.xiuxian.xiuxianserver.dto.response.RewardInfo;
import com.xiuxian.xiuxianserver.mapper.CharacterProfileMapper;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class CharacterLevelManager {
    private final CharacterProfileRepository characterProfileRepository;
    private final LevelTemplateRepository levelTemplateRepository;
    private final LevelRewardRepository levelRewardRepository;
    private final LevelLimitRepository levelLimitRepository;
    private final ResourceManager resourceManager;
    private final BuildingLocationService buildingLocationService;
    private final CharacterBuildingRepository characterBuildingRepository;
    private final Snowflake snowflake;
    private final CharacterProfileMapper characterMapper;

    @Data
    public class LevelUpResult {
        private CharacterInfo characterInfo;
        private List<BuildingInfo> newBuildings;
        private List<RewardInfo> rewards;
    }

    @Transactional
    public LevelUpResult levelUp(Long characterId, int newLevel) {
        // 1. 验证等级配置是否存在
        if (!levelTemplateRepository.existsById(newLevel)) {
            throw new BusinessException("等级配置不存在");
        }

        // 2. 更新角色等级
        CharacterProfile character = characterProfileRepository.findById(characterId)
            .orElseThrow(() -> new BusinessException("角色不存在"));
        int oldLevel = character.getLevel();
        character.setLevel(newLevel);
        characterProfileRepository.save(character);

        LevelUpResult result = new LevelUpResult();
        List<BuildingInfo> newBuildings = new ArrayList<>();
        List<RewardInfo> rewards = new ArrayList<>();

        // 3. 处理建筑位置解锁和建筑创建
        handleLocationUnlock(characterId, newLevel);

        // 4. 处理等级奖励
        handleLevelRewards(characterId, newLevel);

        // 5. 更新各种上限
        handleLevelLimits(characterId, newLevel);

        log.info("角色等级提升 - 角色ID: {}, 旧等级: {}, 新等级: {}", 
            characterId, oldLevel, newLevel);

        result.setNewBuildings(newBuildings);
        result.setRewards(rewards);
        result.setCharacterInfo(characterMapper.toCharacterInfo(character));

        return result;
    }

    private void handleLocationUnlock(Long characterId, int level) {
        // 1. 获取当前等级对应的建筑位置模板
        List<BuildingLocationTemplate> locationTemplates = buildingLocationService
            .findByRequiredCharacterLevel(level);
            
        // 2. 为每个位置创建角色建筑（如果不存在）
        for (BuildingLocationTemplate template : locationTemplates) {
            // 检查该位置是否已经有建筑
            if (characterBuildingRepository.existsByCharacterIdAndLocationId(characterId, template.getId())) {
                log.debug("位置已有建筑 - 角色ID: {}, 位置ID: {}", characterId, template.getId());
                continue;
            }

            CharacterBuilding building = new CharacterBuilding();
            building.setId(snowflake.nextId());
            building.setCharacterId(characterId);
            building.setBuildingTemplateId(template.getBuildingTemplateId());
            building.setLocationId(template.getId());
            building.setCurrentLevel(1);
            building.setBuildingStatus(BuildingStatusType.IDLE);
            
            characterBuildingRepository.save(building);
            
            log.debug("创建角色建筑 - 角色ID: {}, 建筑ID: {}, 位置ID: {}", 
                characterId, building.getId(), template.getId());
        }
    }

    private void handleLevelRewards(Long characterId, int level) {
        List<LevelReward> rewards = levelRewardRepository.findByLevel(level);
        for (LevelReward reward : rewards) {
            if (reward.getRewardType() == RewardType.RESOURCE) {
                Map<String, Integer> resources = Map.of(
                    reward.getRewardId().toString(), reward.getAmount()
                );
                resourceManager.addResource(characterId, resources);
                log.debug("发放资源奖励 - 角色ID: {}, 资源ID: {}, 数量: {}", 
                    characterId, reward.getRewardId(), reward.getAmount());
            } else if (reward.getRewardType() == RewardType.ITEM) {
                // TODO: 调用道具系统发放道具
                log.debug("发放道具奖励 - 角色ID: {}, 道具ID: {}, 数量: {}", 
                    characterId, reward.getRewardId(), reward.getAmount());
            }
        }
    }

    private void handleLevelLimits(Long characterId, int level) {
        List<LevelLimit> limits = levelLimitRepository.findByLevel(level);
        for (LevelLimit limit : limits) {
            log.debug("更新上限 - 角色ID: {}, 类型: {}, 新上限: {}", 
                characterId, limit.getLimitType(), limit.getLimitValue());
            // TODO: 根据不同类型更新对应系统的上限
        }
    }
} 