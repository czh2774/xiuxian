package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.BuildingTemplateDTO;
import com.xiuxian.xiuxianserver.entity.BuildingTemplate;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.mapper.BuildingTemplateMapper;
import com.xiuxian.xiuxianserver.repository.BuildingTemplateRepository;
import com.xiuxian.xiuxianserver.service.BuildingTemplateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BuildingTemplateServiceImpl 实现了建筑模板相关的业务逻辑。
 */
@Service
@RequiredArgsConstructor
public class BuildingTemplateServiceImpl implements BuildingTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(BuildingTemplateServiceImpl.class);

    private final BuildingTemplateRepository buildingTemplateRepository;
    private final BuildingTemplateMapper buildingTemplateMapper;

    /**
     * 获取所有建筑模板的列表。
     *
     * @return 包含所有建筑模板DTO的列表
     */
    @Override
    public List<BuildingTemplateDTO> getAllBuildingTemplates() {
        logger.info("开始获取所有建筑模板");

        List<BuildingTemplate> templates = buildingTemplateRepository.findAll();

        // 将实体列表转换为DTO列表
        List<BuildingTemplateDTO> templateDTOs = templates.stream()
                .map(buildingTemplateMapper::toDTO)
                .collect(Collectors.toList());

        logger.info("成功获取所有建筑模板，总计: {} 个", templateDTOs.size());

        return templateDTOs;
    }

    /**
     * 根据ID获取建筑模板。
     *
     * @param id 建筑模板的唯一标识符
     * @return 对应的建筑模板DTO
     * @throws ResourceNotFoundException 如果建筑模板未找到
     */
    @Override
    public BuildingTemplateDTO getBuildingTemplateById(Long id) {
        logger.info("根据ID获取建筑模板，ID: {}", id);

        BuildingTemplate template = buildingTemplateRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("未找到建筑模板，ID: {}", id);
                    return new ResourceNotFoundException("建筑模板未找到，ID: " + id);
                });

        logger.info("成功获取建筑模板，ID: {}", id);

        return buildingTemplateMapper.toDTO(template);
    }

    @Override
    public List<BuildingTemplate> getTemplatesByIds(List<Long> templateIds) {
        // 使用 Repository 方法根据 ID 列表查询模板
        return buildingTemplateRepository.findByIdIn(templateIds);
    }
}
