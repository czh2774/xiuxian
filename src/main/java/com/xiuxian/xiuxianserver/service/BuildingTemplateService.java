package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.BuildingTemplateDTO;
import com.xiuxian.xiuxianserver.entity.BuildingTemplate;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;

import java.util.List;

/**
 * BuildingTemplateService 定义建筑模板的服务接口。
 */
public interface BuildingTemplateService {

    /**
     * 获取所有建筑模板的列表。
     *
     * @return 建筑模板DTO的列表
     */
    List<BuildingTemplateDTO> getAllBuildingTemplates();

    /**
     * 根据ID获取建筑模板。
     *
     * @param id 建筑模板的唯一标识符
     * @return 建筑模板DTO
     * @throws ResourceNotFoundException 如果建筑模板未找到
     */
    BuildingTemplateDTO getBuildingTemplateById(Long id);


    /**
     * 根据ID列表获取对应的建筑模板
     *
     * @param templateIds 建筑模板ID列表
     * @return 对应的建筑模板列表
     */
    List<BuildingTemplate> getTemplatesByIds(List<Long> templateIds);
}
