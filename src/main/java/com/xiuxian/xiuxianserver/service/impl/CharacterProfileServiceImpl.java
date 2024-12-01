package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.dto.*;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.Mapper.CharacterProfileMapper;
import com.xiuxian.xiuxianserver.repository.CharacterProfileRepository;
import com.xiuxian.xiuxianserver.service.CharacterInitializationService;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.xiuxian.xiuxianserver.util.UserContextUtil.getCurrentUser;

@Service
public class CharacterProfileServiceImpl implements CharacterProfileService {

    private static final Logger logger = LoggerFactory.getLogger(CharacterProfileServiceImpl.class);

    private final CharacterProfileRepository characterProfileRepository;
    private final CharacterInitializationService characterInitializationService;
    private final Snowflake snowflake;
    private final CharacterProfileMapper mapper = CharacterProfileMapper.INSTANCE;
    private static final int MAX_NAME_LENGTH = 50;

    @Autowired
    public CharacterProfileServiceImpl(CharacterProfileRepository characterProfileRepository,
                                       CharacterInitializationService characterInitializationService,
                                       Snowflake snowflake) {
        this.characterProfileRepository = characterProfileRepository;
        this.characterInitializationService = characterInitializationService;
        this.snowflake = snowflake;
    }

    @Override
    public CharacterProfileDTO getCharacterByPlayerId(Long playerId) {
        logger.info("根据玩家ID获取角色数据，Player ID: {}", playerId);
        return characterProfileRepository.findByPlayerId(playerId)
                .map(mapper::toDTO)
                .orElse(null);
    }

    @Override
    public CharacterProfileDTO createCharacterProfile(CharacterProfileCreateDTO createDTO) {
        logger.info("开始创建角色: {}", createDTO);

        if (createDTO.getName() == null || createDTO.getName().length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("角色名称不能为空或过长");
        }

        UserDTO currentUser = getCurrentUser();
        if (currentUser == null) throw new IllegalStateException("当前用户未登录");

        CharacterProfile profile = new CharacterProfile();
        profile.setId(snowflake.nextId());
        profile.setName(createDTO.getName());
        profile.setFaction(createDTO.getFaction());
        profile.setOfficialPosition("Governor");
        profile.setPlayerId(currentUser.getPlatformUserId());

        CharacterProfile savedProfile = characterProfileRepository.save(profile);

        // 使用 CharacterInitializationService 进行统一初始化
        characterInitializationService.initializeDefaultItems(profile.getId());
        characterInitializationService.initializeDefaultGenerals(profile.getId());
        characterInitializationService.initializeBuildingsForCharacter(profile.getId());

        logger.info("角色创建成功，角色ID: {}", profile.getId());
        return mapper.toDTO(savedProfile);
    }

    @Override
    @Transactional
    public CharacterProfileDTO updateCharacterProfile(CharacterProfileUpdateDTO updateDTO) {
        logger.info("更新角色档案，角色ID: {}", updateDTO.getCharacterId());
        CharacterProfile profile = characterProfileRepository.findById(updateDTO.getCharacterId())
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + updateDTO.getCharacterId()));

        mapper.updateEntityFromDTO(updateDTO, profile);

        CharacterProfile updatedProfile = characterProfileRepository.save(profile);
        logger.info("角色档案更新成功，角色ID: {}", updateDTO.getCharacterId());
        return mapper.toDTO(updatedProfile);
    }

    @Override
    public boolean existsByPlayerId(Long playerId) {
        logger.info("检查玩家是否已存在角色档案，Player ID: {}", playerId);
        return characterProfileRepository.existsByPlayerId(playerId);
    }

    @Override
    public boolean existsByName(String name) {
        logger.info("检查角色名称是否已存在: {}", name);
        return characterProfileRepository.existsByName(name);
    }

    @Override
    public void deleteCharacterProfile(Long characterId) {
        logger.info("删除角色档案，角色ID: {}", characterId);
        characterProfileRepository.deleteById(characterId);
        logger.info("角色档案已删除，角色ID: {}", characterId);
    }

    @Override
    public CharacterProfileResourceInfoDTO getCharacterProfileResourceInfo(Long characterId) {
        logger.info("获取角色资源信息，角色ID: {}", characterId);
        CharacterProfile profile = characterProfileRepository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + characterId));
        return new CharacterProfileResourceInfoDTO(profile);
    }

    @Override
    public CharacterProfileBasicInfoDTO getCharacterProfileBasicInfo(Long characterId) {
        logger.info("获取角色的基本信息，角色ID: {}", characterId);
        CharacterProfile profile = characterProfileRepository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + characterId));
        return new CharacterProfileBasicInfoDTO(profile);
    }

    @Override
    public CharacterProfileDTO getCharacterProfileAllInfo(Long characterId) {
        logger.info("获取角色的完整信息，角色ID: {}", characterId);
        CharacterProfile profile = characterProfileRepository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + characterId));
        return mapper.toDTO(profile);
    }

    @Override
    public CharacterProfile findByPlayerId(Long playerId) {
        logger.info("根据玩家ID查找角色档案，Player ID: {}", playerId);
        return characterProfileRepository.findByPlayerId(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("角色未找到，Player ID: " + playerId));
    }
}
