package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.entity.BuildingTemplate;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.BuildingTemplateRepository;
import com.xiuxian.xiuxianserver.service.BuildingTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BuildingTemplateServiceImpl 实现了建筑模板相关的业务逻辑。
 */
@Service
@RequiredArgsConstructor
public class BuildingTemplateServiceImpl implements BuildingTemplateService {

    private final BuildingTemplateRepository buildingTemplateRepository;

    /**
     * 获取所有建筑模板的列表。
     *
     * @return 包含所有建筑模板的列表
     */
    @Override
    public List<BuildingTemplate> getAllBuildingTemplates() {
        return buildingTemplateRepository.findAll();
    }

    /**
     * 根据ID获取建筑模板。
     *
     * @param id 建筑模板的唯一标识符
     * @return 建筑模板实体
     * @throws ResourceNotFoundException 如果建筑模板未找到
     */
    @Override
    public BuildingTemplate getBuildingTemplateById(Long id) {
        return buildingTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("建筑模板未找到，ID: " + id));
    }
}
