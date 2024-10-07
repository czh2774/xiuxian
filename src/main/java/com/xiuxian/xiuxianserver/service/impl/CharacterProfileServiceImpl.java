package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.dto.CharacterProfileResourceInfoDTO;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import com.xiuxian.xiuxianserver.repository.CharacterProfileRepository;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CharacterProfileServiceImpl 实现了角色相关的业务逻辑。
 */
@Service
public class CharacterProfileServiceImpl implements CharacterProfileService {

    @Autowired
    private CharacterProfileRepository characterProfileRepository;

    @Override
    public CharacterProfile createCharacterProfile(CharacterProfile characterProfile) {
        return characterProfileRepository.save(characterProfile);
    }

    @Override
    public CharacterProfile getCharacterProfileById(Long characterId) {
        return characterProfileRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found with id " + characterId));
    }

    @Override
    public CharacterProfile updateCharacterProfile(CharacterProfile characterProfile) {
        return characterProfileRepository.save(characterProfile);
    }

    @Override
    public void deleteCharacterProfile(Long characterId) {
        characterProfileRepository.deleteById(characterId);
    }

    @Override
    public List<CharacterProfile> getAllCharacterProfiles() {
        return characterProfileRepository.findAll();
    }
    @Override
    public CharacterProfileResourceInfoDTO getResourceInfo(Long characterId) {
        CharacterProfile characterProfile = getCharacterProfileById(characterId);

        CharacterProfileResourceInfoDTO resourceInfoDTO = new CharacterProfileResourceInfoDTO();
        resourceInfoDTO.setCharacterId(characterProfile.getCharacterId());
        resourceInfoDTO.setYuanbao(characterProfile.getYuanbao());
        resourceInfoDTO.setCopperCoins(characterProfile.getCopperCoins());
        resourceInfoDTO.setFood(characterProfile.getFood());
        // 其他资源字段...

        return resourceInfoDTO;
    }
}
