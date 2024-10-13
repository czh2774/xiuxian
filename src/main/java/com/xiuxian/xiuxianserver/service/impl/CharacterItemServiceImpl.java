package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.entity.CharacterItem;
import com.xiuxian.xiuxianserver.enums.ItemCategory;
import com.xiuxian.xiuxianserver.repository.CharacterItemRepository;
import com.xiuxian.xiuxianserver.service.CharacterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CharacterItemServiceImpl
 * 实现了 CharacterItemService 接口，处理与玩家道具相关的业务逻辑
 */
@Service
public class CharacterItemServiceImpl implements CharacterItemService {

    private static final Logger logger = LoggerFactory.getLogger(CharacterItemServiceImpl.class);

    private final CharacterItemRepository characterItemRepository;

    @Autowired
    public CharacterItemServiceImpl(CharacterItemRepository characterItemRepository) {
        this.characterItemRepository = characterItemRepository;
    }

    /**
     * 根据角色ID查询该角色拥有的所有道具及其数量
     *
     * @param characterId 角色的唯一标识符
     * @return 玩家拥有的所有道具及其数量的列表
     */
    @Override
    public List<Object[]> getAllItemsByCharacterId(String characterId) {
        logger.info("开始查询玩家 {} 拥有的所有道具", characterId);
        List<Object[]> items = characterItemRepository.findAllByCharacterId(characterId);
        logger.info("成功查询玩家 {} 的道具，找到 {} 个道具", characterId, items.size());
        return items;
    }




    /**
     * 根据角色ID和背包标签类型查询该角色的道具
     *
     * @param characterId 角色的唯一标识符
     * @param itemCategory 道具的背包标签类型
     * @return 符合条件的道具及其数量的列表
     */
    @Override
    public List<Object[]> findItemsByItemCategoryAndCharacterId(String characterId, ItemCategory itemCategory) {
        logger.info("开始查询玩家 {} 的背包类型为 {} 的道具", characterId, itemCategory);
        List<Object[]> items = characterItemRepository.findItemsByItemCategoryAndCharacterId(characterId, itemCategory);
        logger.info("成功查询玩家 {} 的 {} 背包类型的道具，找到 {} 个道具", characterId, itemCategory, items.size());
        return items;
    }




    /**
     * 创建新的角色道具记录
     *
     * @param characterId 角色的唯一标识符
     * @param itemTemplateId 道具模板的唯一标识符
     * @param quantity 道具的数量
     * @param itemCategory 道具的背包标签类型
     * @return 创建后的 CharacterItem 实体对象
     */
    @Override
    public CharacterItem createCharacterItem(String characterId, String itemTemplateId, int quantity, ItemCategory itemCategory) {
        logger.info("开始为玩家 {} 创建新的道具记录，模板ID为 {}，数量为 {}，道具类别为 {}", characterId, itemTemplateId, quantity, itemCategory);

        CharacterItem newItem = CharacterItem.builder()
                .characterId(characterId)
                .itemTemplateId(itemTemplateId)
                .itemCategory(itemCategory)
                .quantity(quantity)
                .acquiredAt(LocalDateTime.now())
                .isEquipped(false)
                .build();

        CharacterItem savedItem = characterItemRepository.save(newItem);
        logger.info("成功为玩家 {} 创建道具记录，模板ID为 {}，道具实例ID为 {}", characterId, itemTemplateId, savedItem.getItemInstanceId());
        return savedItem;
    }
}
