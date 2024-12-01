package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.Mapper.CharacterItemMapper;
import com.xiuxian.xiuxianserver.dto.CharacterItemDTO;
import com.xiuxian.xiuxianserver.entity.CharacterItem;
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

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterItemServiceImpl implements CharacterItemService {

    private static final Logger logger = LoggerFactory.getLogger(CharacterItemServiceImpl.class);

    private final ItemTemplateService itemTemplateService;
    private final CharacterItemRepository characterItemRepository;
    private final Snowflake snowflake;

    @Autowired
    public CharacterItemServiceImpl(ItemTemplateService itemTemplateService,
                                    CharacterItemRepository characterItemRepository,
                                    Snowflake snowflake) {
        this.itemTemplateService = itemTemplateService;
        this.characterItemRepository = characterItemRepository;
        this.snowflake = snowflake;
    }

    @Override
    public CharacterItemDTO getCharacterItemById(long itemInstanceId) {
        logger.info("开始获取角色道具，ID: {}", itemInstanceId);
        CharacterItem item = characterItemRepository.findById(itemInstanceId)
                .orElseThrow(() -> new ResourceNotFoundException("道具未找到，ID: " + itemInstanceId));
        logger.info("成功获取角色道具，ID: {}", itemInstanceId);
        return CharacterItemMapper.INSTANCE.toDTO(item);
    }

    @Override
    public List<CharacterItemDTO> getCharacterItemsByCharacterId(long characterId) {
        logger.info("开始获取角色的所有道具，角色ID: {}", characterId);
        List<CharacterItem> items = characterItemRepository.findByCharacterId(characterId);
        logger.info("成功获取角色的所有道具，角色ID: {}, 道具数量: {}", characterId, items.size());
        return items.stream()
                .map(CharacterItemMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CharacterItemDTO createCharacterItem(@Valid CharacterItemDTO request) {
        logger.info("开始创建角色道具实例，角色ID: {}, 模板ID: {}", request.getCharacterId(), request.getItemTemplateId());
        CharacterItem item = CharacterItemMapper.INSTANCE.toEntity(request);
        item.setId(snowflake.nextId());
        CharacterItem savedItem = characterItemRepository.save(item);
        logger.info("成功创建角色道具实例，道具ID: {}", savedItem.getId());
        return CharacterItemMapper.INSTANCE.toDTO(savedItem);
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
        return CharacterItemMapper.INSTANCE.toDTO(updatedItem);
    }



    @Override
    @Transactional
    public void deleteCharacterItem(long id) {
        logger.info("开始删除角色道具，ID: {}", id);
        if (!characterItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("道具未找到，ID: " + id);
        }
        characterItemRepository.deleteById(id);
        logger.info("成功删除角色道具，ID: {}", id);
    }

    @Override
    @Transactional
    public void useItem(long itemInstanceId, int quantity) {
        logger.info("开始使用道具，ID: {}, 数量: {}", itemInstanceId, quantity);
        CharacterItem item = characterItemRepository.findById(itemInstanceId)
                .orElseThrow(() -> new ResourceNotFoundException("道具未找到，ID: " + itemInstanceId));

        if (item.getQuantity() < quantity) {
            throw new IllegalArgumentException("道具数量不足，无法使用");
        }

        item.setQuantity(item.getQuantity() - quantity);
        if (item.getQuantity() == 0) {
            characterItemRepository.delete(item);
            logger.info("道具已用尽并删除，ID: {}", itemInstanceId);
        } else {
            characterItemRepository.save(item);
            logger.info("道具使用成功，剩余数量: {}", item.getQuantity());
        }
    }
}
