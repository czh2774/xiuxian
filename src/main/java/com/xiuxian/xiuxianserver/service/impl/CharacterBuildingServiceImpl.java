package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.mapper.CharacterBuildingMapper;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterBuildingRepository;
import com.xiuxian.xiuxianserver.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CharacterBuildingServiceImpl
 * 实现角色建筑服务的所有方法
 */
@Service
@RequiredArgsConstructor
public class CharacterBuildingServiceImpl implements CharacterBuildingService {

    private static final Logger logger = LoggerFactory.getLogger(CharacterBuildingServiceImpl.class);
    private final CharacterBuildingRepository characterBuildingRepository;
    private final CharacterBuildingMapper buildingMapper;

    @Override
    public List<CharacterBuildingDTO> getCharacterBuildingsByCharacterId(Long characterId) {
        logger.info("获取角色ID为 {} 的所有建筑实例", characterId);
        return characterBuildingRepository.findByCharacterId(characterId).stream()
                .map(buildingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CharacterBuildingDTO getCharacterBuildingById(Long buildingId) {
        logger.info("获取建筑ID为 {} 的角色建筑实例", buildingId);
        return characterBuildingRepository.findById(buildingId)
                .map(buildingMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("未找到建筑ID为 " + buildingId + " 的角色建筑实例"));
    }

    @Override
    public void updateBuildingStatus(Long buildingId, BuildingStatusType status) {
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到建筑ID为 " + buildingId + " 的建筑实例"));

        building.setBuildingStatus(status);
        characterBuildingRepository.save(building);
    }

    @Override
    public void completeBuildingUpgrade(Long characterId, Long buildingId) {
        CharacterBuilding building = characterBuildingRepository.findById(buildingId)
            .orElseThrow(() -> new ResourceNotFoundException("建筑不存在"));
        
        building.setCurrentLevel(building.getCurrentLevel() + 1);
        building.setBuildingStatus(BuildingStatusType.IDLE);
        characterBuildingRepository.save(building);
        
        logger.info("建筑升级完成 - 角色ID: {}, 建筑ID: {}, 新等级: {}", 
            characterId, buildingId, building.getCurrentLevel());
    }
}
