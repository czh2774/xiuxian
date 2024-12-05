package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.BuildingLocationTemplate;
import java.util.List;
import java.util.Optional;

public interface BuildingLocationService {
    List<BuildingLocationTemplate> findByRequiredCharacterLevel(int level);
    Optional<BuildingLocationTemplate> getLocationByTemplateId(Long templateId);
    boolean isLocationAvailable(Long locationId, Long characterId);
}
