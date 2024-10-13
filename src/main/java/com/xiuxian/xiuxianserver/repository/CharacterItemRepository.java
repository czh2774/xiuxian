package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.CharacterItem;
import com.xiuxian.xiuxianserver.enums.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CharacterItemRepository
 * 处理与 CharacterItem 实体相关的数据库操作
 */
@Repository
public interface CharacterItemRepository extends JpaRepository<CharacterItem, String> {

    /**
     * 根据角色ID查找该角色拥有的所有道具及其数量
     *
     * @param characterId 角色的唯一标识符
     * @return 玩家拥有的所有道具及其数量列表
     */
    List<Object[]> findAllByCharacterId(String characterId);



    /**
     * 根据角色ID和背包标签类型查找该角色的道具
     *
     * @param characterId 角色的唯一标识符
     * @param itemCategory 道具的背包标签类型
     * @return 符合条件的道具及其数量列表
     */
    List<Object[]> findItemsByItemCategoryAndCharacterId(String characterId, ItemCategory itemCategory);

    /**
     * 根据角色ID和道具模板ID获取玩家道具的数量
     *
     * @param characterId 角色的唯一标识符
     * @param itemTemplateId 道具模板的唯一标识符
     * @return 玩家拥有的道具数量
     */
    int findQuantityByCharacterIdAndItemTemplateId(String characterId, String itemTemplateId);


}
