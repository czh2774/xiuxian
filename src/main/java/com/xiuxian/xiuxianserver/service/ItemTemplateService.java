package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.ItemTemplateDTO;

import java.util.List;

/**
 * ItemTemplateService接口，定义道具模板管理的服务合同。
 */
public interface ItemTemplateService {

    /**
     * 根据ID获取道具模板
     * @param id 道具模板ID
     * @return 道具模板的DTO对象
     */
    ItemTemplateDTO getItemTemplateById(long id);

    /**
     * 获取所有道具模板
     * @return 道具模板DTO对象列表
     */
    List<ItemTemplateDTO> getAllItemTemplates();

    /**
     * 创建道具模板
     * @param itemTemplateDTO 创建道具模板的请求DTO
     * @return 创建后的道具模板DTO对象
     */
    ItemTemplateDTO createItemTemplate(ItemTemplateDTO itemTemplateDTO);

    /**
     * 更新道具模板
     * @param id 道具模板ID
     * @param itemTemplateDTO 更新请求的DTO对象
     * @return 更新后的道具模板DTO对象
     */
    ItemTemplateDTO updateItemTemplate(long id, ItemTemplateDTO itemTemplateDTO);

    /**
     * 删除道具模板
     * @param id 道具模板ID
     */
    void deleteItemTemplate(long id);
}
