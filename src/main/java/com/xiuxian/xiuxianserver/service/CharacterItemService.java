package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.CharacterItem;
import com.xiuxian.xiuxianserver.enums.ItemCategory;
import java.util.List;

public interface CharacterItemService {

    List<Object[]> getAllItemsByCharacterId(String characterId);



    // 新增：根据背包类型和角色ID查询道具的声明
    List<Object[]> findItemsByItemCategoryAndCharacterId(String characterId, ItemCategory itemCategory);
    /**
     * 创建新的角色道具记录
     *
     * @param characterId 角色的唯一标识符
     * @param itemTemplateId 道具模板的唯一标识符
     * @param quantity 道具的数量
     * @param itemCategory 道具的背包标签类型
     * @return 创建后的 CharacterItem 实体对象
     */
    CharacterItem createCharacterItem(String characterId, String itemTemplateId, int quantity, ItemCategory itemCategory);
}

