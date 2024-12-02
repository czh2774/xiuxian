package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.dto.CharacterProfileDTO;
import com.xiuxian.xiuxianserver.entity.BuildingLocationTemplate;
import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.entity.CharacterGeneral;
import com.xiuxian.xiuxianserver.entity.CharacterItem;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.enums.ItemCategory;
import com.xiuxian.xiuxianserver.mapper.CharacterProfileMapper;
import com.xiuxian.xiuxianserver.repository.CharacterBuildingRepository;
import com.xiuxian.xiuxianserver.repository.CharacterGeneralRepository;
import com.xiuxian.xiuxianserver.repository.CharacterItemRepository;
import com.xiuxian.xiuxianserver.repository.CharacterProfileRepository;
import com.xiuxian.xiuxianserver.service.BuildingLocationService;
import com.xiuxian.xiuxianserver.service.CharacterInitializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterInitializationServiceImpl implements CharacterInitializationService {

    private static final Logger logger = LoggerFactory.getLogger(CharacterInitializationServiceImpl.class);

    private final CharacterItemRepository characterItemRepository;
    private final CharacterGeneralRepository characterGeneralRepository;
    private final CharacterBuildingRepository characterBuildingRepository;
    private final CharacterProfileRepository characterProfileRepository;
    private final BuildingLocationService buildingLocationService;
    private final Snowflake snowflake;
    @Autowired
    private CharacterProfileMapper characterProfileMapper;

    public CharacterInitializationServiceImpl(CharacterItemRepository characterItemRepository,
                                              CharacterGeneralRepository characterGeneralRepository,
                                              CharacterBuildingRepository characterBuildingRepository,
                                              CharacterProfileRepository characterProfileRepository,
                                              BuildingLocationService buildingLocationService, Snowflake snowflake) {
        this.characterItemRepository = characterItemRepository;
        this.characterGeneralRepository = characterGeneralRepository;
        this.characterBuildingRepository = characterBuildingRepository;
        this.characterProfileRepository = characterProfileRepository;
        this.buildingLocationService = buildingLocationService;
        this.snowflake = snowflake;
    }

    @Transactional
    public void initializeDefaultItems(long characterId) {
        logger.info("开始初始化角色的默认道具列表，角色ID: {}", characterId);
        List<Long> defaultTemplateIds = List.of(1001L, 1002L);

        List<CharacterItem> defaultItems = defaultTemplateIds.stream()
                .map(templateId -> {
                    CharacterItem item = new CharacterItem();
                    item.setId(snowflake.nextId());
                    item.setCharacterId(characterId);
                    item.setItemTemplateId(templateId);
                    item.setQuantity(10);
                    item.setAcquiredAt(java.time.LocalDateTime.now());
                    item.setEquipped(false);
                    item.setItemCategory(ItemCategory.ITEM); // 添加默认分类
                    return item;
                }).collect(Collectors.toList());

        characterItemRepository.saveAll(defaultItems);
        logger.info("成功初始化角色的默认道具列表，角色ID: {}", characterId);
    }

    @Transactional
    public List<CharacterGeneral> initializeDefaultGenerals(Long characterId) {
        List<CharacterGeneral> defaultGenerals = new ArrayList<>();

        CharacterGeneral general1 = new CharacterGeneral();
        general1.setId(snowflake.nextId());
        general1.setCharacterId(characterId);
        general1.setGeneralTemplateId(1L);
        general1.setLevel(1);
        general1.setStars(3);
        general1.setExperience(0);
        general1.setStatus("Active");
        defaultGenerals.add(general1);

        CharacterGeneral general2 = new CharacterGeneral();
        general2.setId(snowflake.nextId());
        general2.setCharacterId(characterId);
        general2.setGeneralTemplateId(2L);
        general2.setLevel(1);
        general2.setStars(3);
        general2.setExperience(0);
        general2.setStatus("Active");
        defaultGenerals.add(general2);

        characterGeneralRepository.saveAll(defaultGenerals);
        logger.info("初始化角色默认武将列表完成，角色ID：{}", characterId);
        return defaultGenerals;
    }


    @Transactional
    public void initializeBuildingsForCharacter(Long characterId) {
        CharacterProfileDTO character = characterProfileRepository.findById(characterId)
                .map(characterProfileMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("角色档案未找到，ID: " + characterId));

        List<Long> initialBuildingTemplateIds = List.of(1001L, 1002L);
        List<CharacterBuilding> initialBuildings = initialBuildingTemplateIds.stream()
                .map(templateId -> {
                    CharacterBuilding building = new CharacterBuilding();
                    building.setId(snowflake.nextId());
                    building.setCharacterId(character.getId());
                    building.setBuildingTemplateId(templateId);
                    building.setCurrentLevel(1);
                    building.setBuildingStatus(BuildingStatusType.IDLE);

                    // 使用 getLocationByTemplateId 查找并设置位置 ID
                    Optional<BuildingLocationTemplate> locationTemplateOptional = buildingLocationService.getLocationByTemplateId(templateId);
                    if (locationTemplateOptional.isPresent()) {
                        Long locationId = locationTemplateOptional.get().getId();
                        building.setLocationId(locationId);
                    } else {
                        logger.warn("未找到匹配的位置模板，建筑模板ID: {}", templateId);
                    }

                    // 完整赋值其他字段
                    building.setActionStartTime(null); // 或默认值
                    building.setActionTotalDuration(0); // 设置为初始值
                    building.setTransactionId(null); // 或默认值
                    building.setFeaturePrompt(null); // 或默认值
                    building.setRewardPending(false); // 设置为初始状态
                    building.setGrowthType(null); // 或默认值
                    building.setGrowthId(null); // 或默认值
                    building.setGrowthValue(0); // 设置为初始值

                    return building;
                })
                .collect(Collectors.toList());

        characterBuildingRepository.saveAll(initialBuildings);
        logger.info("成功初始化角色的建筑，角色ID: {}", characterId);
    }


    @Transactional
    public void createDefaultBuildingsForCharacter(Long characterId) {
        logger.info("为角色创建默认建筑，角色ID：{}", characterId);
        CharacterBuilding building1 = new CharacterBuilding();
        building1.setId(snowflake.nextId());
        building1.setCharacterId(characterId);
        building1.setBuildingTemplateId(1001L);
        building1.setLocationId(1L);
        building1.setCurrentLevel(1);
        building1.setBuildingStatus(BuildingStatusType.IDLE);

        characterBuildingRepository.save(building1);
        logger.info("默认建筑创建成功，角色ID: {}, 建筑ID: {}", characterId, building1.getId());
    }
}
