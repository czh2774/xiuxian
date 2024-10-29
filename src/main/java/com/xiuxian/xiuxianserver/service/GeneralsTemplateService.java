package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.GeneralsTemplateCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsTemplateUpdateRequestDTO;

import java.util.List;

/**
 * GeneralsTemplateService接口，定义武将模板管理的服务合同。
 */
public interface GeneralsTemplateService {

    /**
     * 根据ID获取武将模板信息
     * @param id 武将模板ID
     * @return 武将模板的DTO对象
     */
    GeneralsTemplateDTO getGeneralTemplateById(Long id);

    /**
     * 获取所有武将模板
     * @return 武将模板DTO对象列表
     */
    List<GeneralsTemplateDTO> getAllGeneralTemplates();

    /**
     * 创建一个新的武将模板
     * @param request 创建模板请求的DTO对象
     * @return 创建后的武将模板DTO对象
     */
    GeneralsTemplateDTO createGeneralTemplate(GeneralsTemplateCreateRequestDTO request);

    /**
     * 更新指定ID的武将模板信息
     * @param id 武将模板ID
     * @param request 更新请求的DTO对象
     * @return 更新后的武将模板DTO对象
     */
    GeneralsTemplateDTO updateGeneralTemplate(Long id, GeneralsTemplateUpdateRequestDTO request);

    /**
     * 删除指定ID的武将模板
     * @param id 武将模板ID
     */
    void deleteGeneralTemplate(Long id);
}
