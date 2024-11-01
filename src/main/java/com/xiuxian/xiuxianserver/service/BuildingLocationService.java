package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.BuildingLocationTemplate;

import java.util.List;

public interface BuildingLocationService {
    /**
     * 获取所有建筑位置模板数据。
     *
     * @return 建筑位置模板的列表
     */
    List<BuildingLocationTemplate> getBuildingLocationTemplates();
}
