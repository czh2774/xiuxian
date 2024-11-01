package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.entity.BuildingLocationTemplate;
import com.xiuxian.xiuxianserver.repository.BuildingLocationRepository;
import com.xiuxian.xiuxianserver.service.BuildingLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingLocationServiceImpl implements BuildingLocationService {

    private final BuildingLocationRepository buildingLocationRepository;

    @Override
    public List<BuildingLocationTemplate> getBuildingLocationTemplates() {
        System.out.println("Fetching building location templates using repository...");
        return buildingLocationRepository.findAll();
    }
}
