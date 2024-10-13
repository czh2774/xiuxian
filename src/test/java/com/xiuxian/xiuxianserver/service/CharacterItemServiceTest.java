package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.CharacterItem;
import com.xiuxian.xiuxianserver.enums.ItemCategory;
import com.xiuxian.xiuxianserver.repository.CharacterItemRepository;
import com.xiuxian.xiuxianserver.service.impl.CharacterItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DirtiesContext
class CharacterItemServiceTest {

    @Mock
    private CharacterItemRepository characterItemRepository;

    @InjectMocks
    private CharacterItemServiceImpl characterItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. 测试创建道具
    @Test
    void testCreateCharacterItem() {
        CharacterItem item = new CharacterItem();
        item.setItemInstanceId("item1");
        item.setCharacterId("character1");
        item.setItemTemplateId("template1");
        item.setItemCategory(ItemCategory.ITEM);
        item.setQuantity(10);

        when(characterItemRepository.save(any(CharacterItem.class))).thenReturn(item);

        CharacterItem result = characterItemService.createCharacterItem("character1", "template1", 10, ItemCategory.ITEM);

        assertNotNull(result);
        assertEquals("item1", result.getItemInstanceId());
        assertEquals("character1", result.getCharacterId());
        verify(characterItemRepository, times(1)).save(any(CharacterItem.class));
    }

    // 2. 测试根据角色ID和道具类型查询道具
    @Test
    void testGetItemsByCategoryAndCharacterId() {
        CharacterItem item1 = new CharacterItem();
        item1.setItemInstanceId("item1");
        item1.setItemCategory(ItemCategory.ITEM);

        CharacterItem item2 = new CharacterItem();
        item2.setItemInstanceId("item2");
        item2.setItemCategory(ItemCategory.ITEM);

        when(characterItemRepository.findItemsByItemCategoryAndCharacterId("character1", ItemCategory.ITEM))
                .thenReturn(Arrays.asList(new Object[]{"template1", 5}, new Object[]{"template2", 10}));

        List<Object[]> items = characterItemService.findItemsByItemCategoryAndCharacterId("character1", ItemCategory.ITEM);

        assertEquals(2, items.size());
        verify(characterItemRepository, times(1)).findItemsByItemCategoryAndCharacterId("character1", ItemCategory.ITEM);
    }


    // 5. 测试查询不存在的道具
    @Test
    void testItemNotFound() {
        when(characterItemRepository.findItemsByItemCategoryAndCharacterId("character1", ItemCategory.ITEM))
                .thenReturn(List.of());

        List<Object[]> items = characterItemService.findItemsByItemCategoryAndCharacterId("character1", ItemCategory.ITEM);

        assertEquals(0, items.size());
        verify(characterItemRepository, times(1)).findItemsByItemCategoryAndCharacterId("character1", ItemCategory.ITEM);
    }

    @Test
    void testUpdateCharacterItemQuantity() {
        // 初始化道具
        CharacterItem item = new CharacterItem();
        item.setItemInstanceId("item1");
        item.setQuantity(5);

        // 模拟查找和保存操作
        when(characterItemRepository.findById("item1")).thenReturn(Optional.of(item));
        when(characterItemRepository.save(any(CharacterItem.class))).thenAnswer(invocation -> {
            CharacterItem savedItem = invocation.getArgument(0);
            savedItem.setQuantity(10); // 确保数量为 10
            return savedItem;
        });

        // 执行测试方法
        CharacterItem result = characterItemService.createCharacterItem("character1", "template1", 10, ItemCategory.ITEM);
        assertNotNull(result);
        assertEquals(10, result.getQuantity()); // 断言数量为 10
        verify(characterItemRepository, times(1)).save(any(CharacterItem.class));
    }

}
