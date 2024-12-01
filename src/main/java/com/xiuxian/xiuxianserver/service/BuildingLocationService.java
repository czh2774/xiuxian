package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.BuildingLocationTemplate;

import java.util.List;
import java.util.Optional;

public interface BuildingLocationService {
    /**
     * 获取所有建筑位置模板数据。
     *
     * @return 建筑位置模板的列表
     */
    List<BuildingLocationTemplate> getBuildingLocationTemplates();


    /**
     * 根据建筑模板ID获取对应的建筑位置模板
     *
     * @param templateId 建筑模板ID
     * @return 对应的建筑位置模板，如果不存在则返回空Optional
     */
    Optional<BuildingLocationTemplate> getLocationByTemplateId(Long templateId);
}
