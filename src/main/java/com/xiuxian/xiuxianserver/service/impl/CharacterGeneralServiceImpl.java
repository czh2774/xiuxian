package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.CharacterGeneralDTO;
import com.xiuxian.xiuxianserver.entity.CharacterGeneral;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterGeneralRepository;
import com.xiuxian.xiuxianserver.service.CharacterGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CharacterGeneralServiceImpl实现类，负责处理角色武将实例的具体业务逻辑。
 */
@Service
public class CharacterGeneralServiceImpl implements CharacterGeneralService {

    @Autowired
    private CharacterGeneralRepository characterGeneralRepository;

    @Override
    public CharacterGeneralDTO getCharacterGeneralById(Long id) {
        CharacterGeneral general = characterGeneralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CharacterGeneral not found with ID: " + id));
        return convertToDTO(general);
    }

    @Override
    public List<CharacterGeneralDTO> getCharacterGeneralsByCharacterId(Long characterId) {
        List<CharacterGeneral> generals = characterGeneralRepository.findByCharacterId(characterId);
        return generals.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CharacterGeneralDTO createCharacterGeneral(CharacterGeneralDTO request) {
        CharacterGeneral general = new CharacterGeneral();
        general.setCharacterId(request.getCharacterId());
        general.setGeneralTemplateId(request.getGeneralTemplateId());
        general.setLevel(request.getLevel());
        general.setStars(request.getStars());
        general.setExperience(request.getExperience());
        general.setStatus(request.getStatus());
        general.setEquippedItems(request.getEquippedItems());
        general.setCurrentSkills(request.getCurrentSkills());

        characterGeneralRepository.save(general);
        return convertToDTO(general);
    }

    @Override
    public CharacterGeneralDTO updateCharacterGeneral(Long id, CharacterGeneralDTO request) {
        CharacterGeneral general = characterGeneralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CharacterGeneral not found with ID: " + id));

        general.setLevel(request.getLevel());
        general.setStars(request.getStars());
        general.setExperience(request.getExperience());
        general.setStatus(request.getStatus());
        general.setEquippedItems(request.getEquippedItems());
        general.setCurrentSkills(request.getCurrentSkills());

        characterGeneralRepository.save(general);
        return convertToDTO(general);
    }

    @Override
    public void deleteCharacterGeneral(Long id) {
        CharacterGeneral general = characterGeneralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CharacterGeneral not found with ID: " + id));
        characterGeneralRepository.delete(general);
    }

    // DTO转换方法
    private CharacterGeneralDTO convertToDTO(CharacterGeneral general) {
        CharacterGeneralDTO dto = new CharacterGeneralDTO();
        dto.setId(general.getId());
        dto.setCharacterId(general.getCharacterId());
        dto.setGeneralTemplateId(general.getGeneralTemplateId());
        dto.setLevel(general.getLevel());
        dto.setStars(general.getStars());
        dto.setExperience(general.getExperience());
        dto.setStatus(general.getStatus());
        dto.setEquippedItems(general.getEquippedItems());
        dto.setCurrentSkills(general.getCurrentSkills());
        return dto;
    }
}
