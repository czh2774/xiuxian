package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.Mapper.CharacterBuildingMapper;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.enums.FeaturePromptType;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterBuildingRepository;
import com.xiuxian.xiuxianserver.service.BuildingLocationService;
import com.xiuxian.xiuxianserver.service.CharacterBuildingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterBuildingServiceImpl implements CharacterBuildingService {

    private static final Logger logger = LoggerFactory.getLogger(CharacterBuildingServiceImpl.class);
    private final CharacterBuildingRepository characterBuildingRepository;
    private final Snowflake snowflake;


    @Override
    public List<CharacterBuildingDTO> getAllCharacterBuildings() {
        logger.info("获取所有角色建筑实例");
        return characterBuildingRepository.findAll().stream()
                .map(CharacterBuildingMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacterBuildingDTO> getCharacterBuildingsByCharacterId(Long characterId) {
        logger.info("获取角色ID为 {} 的所有建筑实例", characterId);
        List<CharacterBuilding> buildings = characterBuildingRepository.findByCharacterId(characterId);
        if (buildings.isEmpty()) {
            logger.warn("未找到角色ID为 {} 的建筑实例", characterId);
            throw new ResourceNotFoundException("未找到角色ID为 " + characterId + " 的建筑实例");
        }
        return buildings.stream()
                .map(CharacterBuildingMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CharacterBuildingDTO getCharacterBuildingById(Long buildingId) {
        logger.info("获取建筑ID为 {} 的角色建筑实例", buildingId);
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到建筑ID为 " + buildingId + " 的角色建筑实例"));
        return CharacterBuildingMapper.INSTANCE.toDTO(building);
    }

    @Override
    public CharacterBuildingDTO createCharacterBuildingInstance(CharacterBuildingCreateRequestDTO request) {
        logger.info("创建角色ID为 {} 的建筑实例，模板ID为 {}", request.getCharacterId(), request.getBuildingTemplateId());
        CharacterBuilding building = CharacterBuildingMapper.INSTANCE.toEntity(request);
        building.setId(snowflake.nextId());
        characterBuildingRepository.save(building);
        logger.info("成功创建角色建筑实例，建筑ID为 {}", building.getId());
        return CharacterBuildingMapper.INSTANCE.toDTO(building);
    }

    @Override
    public void updateCharacterBuildingStatus(Long buildingId, BuildingStatusType newStatus) {
        logger.info("更新建筑ID为 {} 的角色建筑状态为 {}", buildingId, newStatus);
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到建筑ID为 " + buildingId + " 的角色建筑实例"));
        building.setBuildingStatus(newStatus);
        characterBuildingRepository.save(building);
        logger.info("成功更新建筑ID为 {} 的状态为 {}", buildingId, newStatus);
    }

    @Override
    public void completeBuildingUpgrade(Long buildingId) {
        logger.info("完成建筑ID为 {} 的升级操作", buildingId);
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到建筑ID为 " + buildingId + " 的角色建筑实例"));
        building.setCurrentLevel(building.getCurrentLevel() + 1);
        building.setBuildingStatus(BuildingStatusType.IDLE);
        characterBuildingRepository.save(building);
        logger.info("建筑ID为 {} 的升级完成，当前等级为 {}", buildingId, building.getCurrentLevel());
    }

    @Override
    public void updateFeaturePrompt(Long buildingId, FeaturePromptType newPrompt) {
        logger.info("更新建筑ID为 {} 的功能提示为 {}", buildingId, newPrompt);
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到建筑ID为 " + buildingId + " 的角色建筑实例"));
        building.setFeaturePrompt(newPrompt);
        characterBuildingRepository.save(building);
        logger.info("成功更新建筑ID为 {} 的功能提示为 {}", buildingId, newPrompt);
    }


}
