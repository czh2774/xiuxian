package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.CharacterItemDTO;
import com.xiuxian.xiuxianserver.dto.ItemTemplateDTO;
import com.xiuxian.xiuxianserver.entity.CharacterItem;
import com.xiuxian.xiuxianserver.enums.ItemCategory;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterItemRepository;
import com.xiuxian.xiuxianserver.service.CharacterItemService;
import com.xiuxian.xiuxianserver.service.ItemTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;

import jakarta.validation.Valid; // 使用 Jakarta Validation

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterItemServiceImpl implements CharacterItemService {

    private static final Logger logger = LoggerFactory.getLogger(CharacterItemServiceImpl.class);
    @Autowired
    private ItemTemplateService itemTemplateService;
    private final CharacterItemRepository characterItemRepository;
    private final Snowflake snowflake;

    @Autowired
    public CharacterItemServiceImpl(CharacterItemRepository characterItemRepository, Snowflake snowflake) {
        this.characterItemRepository = characterItemRepository;
        this.snowflake = snowflake;
    }

    @Override
    public CharacterItemDTO getCharacterItemById(long itemInstanceId) {
        logger.info("开始获取角色道具，ID: {}", itemInstanceId);
        CharacterItem item = characterItemRepository.findById(itemInstanceId)
                .orElseThrow(() -> new ResourceNotFoundException("道具未找到，ID: " + itemInstanceId));
        logger.info("成功获取角色道具，ID: {}", itemInstanceId);
        return new CharacterItemDTO(item);
    }

    @Override
    public List<CharacterItemDTO> getCharacterItemsByCharacterId(long characterId) {
        logger.info("开始获取角色的所有道具，角色ID: {}", characterId);
        List<CharacterItem> items = characterItemRepository.findByCharacterId(characterId);
        List<CharacterItemDTO> itemDTOs = items.stream().map(CharacterItemDTO::new).collect(Collectors.toList());
        logger.info("成功获取角色的所有道具，角色ID: {}, 道具数量: {}", characterId, items.size());
        return itemDTOs;
    }

    @Override
    @Transactional
    public CharacterItemDTO createCharacterItem(@Valid CharacterItemDTO request) {
        logger.info("开始创建角色道具实例，角色ID: {}, 模板ID: {}", request.getCharacterId(), request.getItemTemplateId());

        CharacterItem item = new CharacterItem();
        item.setId(snowflake.nextId());
        item.setCharacterId(request.getCharacterId());
        item.setItemTemplateId(request.getItemTemplateId());
        item.setQuantity(request.getQuantity());
        item.setAcquiredAt(request.getAcquiredAt());
        item.setItemCategory(request.getItemCategory());
        item.setLastUsedAt(request.getLastUsedAt());
        item.setEquipped(request.isEquipped());

        CharacterItem savedItem = characterItemRepository.save(item);
        logger.info("成功创建角色道具实例，道具ID: {}", savedItem.getId());
        return new CharacterItemDTO(savedItem);
    }

    @Override
    @Transactional
    public CharacterItemDTO updateCharacterItem(long itemInstanceId, @Valid CharacterItemDTO request) {
        logger.info("开始更新角色道具实例，ID: {}", itemInstanceId);
        CharacterItem item = characterItemRepository.findById(itemInstanceId)
                .orElseThrow(() -> new ResourceNotFoundException("道具未找到，ID: " + itemInstanceId));

        item.setQuantity(request.getQuantity());
        item.setLastUsedAt(request.getLastUsedAt());
        item.setEquipped(request.isEquipped());

        CharacterItem updatedItem = characterItemRepository.save(item);
        logger.info("成功更新角色道具实例，ID: {}", itemInstanceId);
        return new CharacterItemDTO(updatedItem);
    }

    @Override
    @Transactional
    public void deleteCharacterItem(long itemInstanceId) {
        logger.info("开始删除角色道具实例，ID: {}", itemInstanceId);
        if (!characterItemRepository.existsById(itemInstanceId)) {
            logger.error("删除失败，道具未找到，ID: {}", itemInstanceId);
            throw new ResourceNotFoundException("道具未找到，ID: " + itemInstanceId);
        }
        characterItemRepository.deleteById(itemInstanceId);
        logger.info("成功删除角色道具实例，ID: {}", itemInstanceId);
    }

    @Override
    @Transactional
    public void useItem(long itemInstanceId, int quantity) {
        logger.info("开始使用道具，道具ID: {}, 使用数量: {}", itemInstanceId, quantity);
        CharacterItem item = characterItemRepository.findById(itemInstanceId)
                .orElseThrow(() -> new ResourceNotFoundException("道具未找到，ID: " + itemInstanceId));

        if (item.getQuantity() < quantity) {
            logger.error("使用道具失败，数量不足，道具ID: {}", itemInstanceId);
            throw new IllegalArgumentException("道具数量不足，无法使用");
        }

        item.setQuantity(item.getQuantity() - quantity);
        characterItemRepository.save(item);
        logger.info("成功使用道具，道具ID: {}, 剩余数量: {}", itemInstanceId, item.getQuantity());
    }


    @Override
    @Transactional
    public List<CharacterItemDTO> initializeDefaultItems(long characterId) {
        logger.info("开始初始化角色的默认道具列表，角色ID: {}", characterId);

        // 假设存在的 ItemTemplateService 用于获取道具模板数据
        List<Long> defaultTemplateIds = List.of(1L, 2L); // 默认的道具模板 ID 列表，可以根据需要调整

        List<CharacterItemDTO> defaultItems = defaultTemplateIds.stream()
                .map(templateId -> {
                    ItemTemplateDTO template = itemTemplateService.getItemTemplateById(templateId); // 获取道具模板
                    return new CharacterItemDTO(
                            null,
                            characterId,
                            template.getId(), // 使用模板 ID
                            10,               // 默认数量，可以自定义
                            LocalDateTime.now(),
                            template.getItemCategory(), // 从模板中获取道具类别
                            null,
                            false,
                            LocalDateTime.now(),
                            LocalDateTime.now()
                    );
                })
                .collect(Collectors.toList());

        List<CharacterItem> savedItems = defaultItems.stream().map(itemDTO -> {
            CharacterItem item = new CharacterItem();
            item.setId(snowflake.nextId());
            item.setCharacterId(characterId);
            item.setItemTemplateId(itemDTO.getItemTemplateId());
            item.setQuantity(itemDTO.getQuantity());
            item.setAcquiredAt(itemDTO.getAcquiredAt());
            item.setItemCategory(itemDTO.getItemCategory());
            item.setLastUsedAt(itemDTO.getLastUsedAt());
            item.setEquipped(itemDTO.isEquipped());
            return item;
        }).collect(Collectors.toList());

        // 保存所有道具实例
        characterItemRepository.saveAll(savedItems);

        logger.info("成功初始化角色的默认道具列表，角色ID: {}, 道具数量: {}", characterId, savedItems.size());
        return savedItems.stream().map(CharacterItemDTO::new).collect(Collectors.toList());
    }






}
