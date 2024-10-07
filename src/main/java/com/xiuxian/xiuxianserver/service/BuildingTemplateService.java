package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.BuildingTemplate;
import java.util.List;

/**
 * BuildingTemplateService 定义建筑模板的服务接口。
 */
public interface BuildingTemplateService {

    /**
     * 获取所有建筑模板的列表。
     *
     * @return 建筑模板的列表
     */
    List<BuildingTemplate> getAllBuildingTemplates();

    /**
     * 根据ID获取建筑模板。
     *
     * @param id 建筑模板的唯一标识符
     * @return 建筑模板实体
     * @throws ResourceNotFoundException 如果建筑模板未找到
     */
    BuildingTemplate getBuildingTemplateById(Long id);
}
