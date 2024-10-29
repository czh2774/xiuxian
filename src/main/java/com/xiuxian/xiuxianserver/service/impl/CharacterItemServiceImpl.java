package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.CharacterItemDTO;
import com.xiuxian.xiuxianserver.entity.CharacterItem;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterItemRepository;
import com.xiuxian.xiuxianserver.service.CharacterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CharacterItemServiceImpl实现类，负责处理角色道具实例的具体业务逻辑。
 */
@Service
public class CharacterItemServiceImpl implements CharacterItemService {

    @Autowired
    private CharacterItemRepository characterItemRepository;

    @Override
    public CharacterItemDTO getCharacterItemById(long itemInstanceId) {
        CharacterItem item = characterItemRepository.findById(itemInstanceId)
                .orElseThrow(() -> new ResourceNotFoundException("CharacterItem not found with ID: " + itemInstanceId));
        return convertToDTO(item);
    }

    @Override
    public List<CharacterItemDTO> getCharacterItemsByCharacterId(long characterId) {
        List<CharacterItem> items = characterItemRepository.findByCharacterId(characterId);
        return items.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CharacterItemDTO createCharacterItem(CharacterItemDTO request) {
        CharacterItem item = new CharacterItem();
        item.setCharacterId(request.getCharacterId());
        item.setItemTemplateId(request.getItemTemplateId());
        item.setQuantity(request.getQuantity());
        item.setAcquiredAt(request.getAcquiredAt());
        item.setItemCategory(request.getItemCategory());
        item.setLastUsedAt(request.getLastUsedAt());
        item.setEquipped(request.isEquipped());
        item.setCreatedAt(request.getCreatedAt());
        item.setUpdatedAt(request.getUpdatedAt());

        characterItemRepository.save(item);
        return convertToDTO(item);
    }

    @Override
    public CharacterItemDTO updateCharacterItem(long itemInstanceId, CharacterItemDTO request) {
        CharacterItem item = characterItemRepository.findById(itemInstanceId)
                .orElseThrow(() -> new ResourceNotFoundException("CharacterItem not found with ID: " + itemInstanceId));

        item.setQuantity(request.getQuantity());
        item.setLastUsedAt(request.getLastUsedAt());
        item.setEquipped(request.isEquipped());
        item.setUpdatedAt(request.getUpdatedAt());

        characterItemRepository.save(item);
        return convertToDTO(item);
    }

    @Override
    public void deleteCharacterItem(long itemInstanceId) {
        CharacterItem item = characterItemRepository.findById(itemInstanceId)
                .orElseThrow(() -> new ResourceNotFoundException("CharacterItem not found with ID: " + itemInstanceId));
        characterItemRepository.delete(item);
    }

    // DTO转换方法
    private CharacterItemDTO convertToDTO(CharacterItem item) {
        CharacterItemDTO dto = new CharacterItemDTO();
        dto.setItemInstanceId(item.getId());
        dto.setCharacterId(item.getCharacterId());
        dto.setItemTemplateId(item.getItemTemplateId());
        dto.setQuantity(item.getQuantity());
        dto.setAcquiredAt(item.getAcquiredAt());
        dto.setItemCategory(item.getItemCategory());
        dto.setLastUsedAt(item.getLastUsedAt());
        dto.setEquipped(item.isEquipped());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setUpdatedAt(item.getUpdatedAt());
        return dto;
    }
}
