package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.entity.BuildingLocationTemplate;
import com.xiuxian.xiuxianserver.repository.BuildingLocationTemplateRepository;
import com.xiuxian.xiuxianserver.repository.CharacterBuildingRepository;
import com.xiuxian.xiuxianserver.service.BuildingLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingLocationServiceImpl implements BuildingLocationService {
    private final BuildingLocationTemplateRepository buildingLocationTemplateRepository;
    private final CharacterBuildingRepository characterBuildingRepository;

    @Override
    public List<BuildingLocationTemplate> findByRequiredCharacterLevel(int level) {
        return buildingLocationTemplateRepository.findByRequiredCharacterLevel(level);
    }

    

    @Override
    public Optional<BuildingLocationTemplate> getLocationByTemplateId(Long templateId) {
        return buildingLocationTemplateRepository.findByBuildingTemplateId(templateId);
    }

    @Override
    public boolean isLocationAvailable(Long locationId, Long characterId) {
        // 1. 检查位置是否存在
        Optional<BuildingLocationTemplate> template = buildingLocationTemplateRepository.findById(locationId);
        if (template.isEmpty()) {
            return false;
        }
        
        // 2. 检查该位置是否已被使用
        return !characterBuildingRepository.existsByCharacterIdAndLocationId(characterId, locationId);
    }
}
