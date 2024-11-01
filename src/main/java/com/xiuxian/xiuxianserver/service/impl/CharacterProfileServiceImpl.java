package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.dto.*;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.Mapper.CharacterProfileMapper;
import com.xiuxian.xiuxianserver.repository.CharacterProfileRepository;
import com.xiuxian.xiuxianserver.service.CharacterItemService;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import com.xiuxian.xiuxianserver.service.CharacterGeneralService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.xiuxian.xiuxianserver.util.UserContextUtil.getCurrentUser;

@Service
public class CharacterProfileServiceImpl implements CharacterProfileService {

    private static final Logger logger = LoggerFactory.getLogger(CharacterProfileServiceImpl.class);

    private final CharacterProfileRepository characterProfileRepository;
    private final CharacterItemService characterItemService;
    private final CharacterGeneralService characterGeneralService;
    private final Snowflake snowflake;
    private final CharacterProfileMapper mapper = CharacterProfileMapper.INSTANCE;
    private static final int MAX_NAME_LENGTH = 50;

    @Autowired
    public CharacterProfileServiceImpl(CharacterProfileRepository characterProfileRepository,
                                       CharacterItemService characterItemService,
                                       CharacterGeneralService characterGeneralService,
                                       Snowflake snowflake) {
        this.characterProfileRepository = characterProfileRepository;
        this.characterItemService = characterItemService;
        this.characterGeneralService = characterGeneralService;
        this.snowflake = snowflake;
    }

    /**
     * 根据玩家ID获取角色数据
     *
     * @param playerId 平台用户的ID
     * @return CharacterProfileDTO 角色数据传输对象，如果不存在则返回null
     */
    @Override
    public CharacterProfileDTO getCharacterByPlayerId(Long playerId) {
        logger.info("根据玩家ID获取角色数据，Player ID: {}", playerId);
        return characterProfileRepository.findByPlayerId(playerId)
                .map(mapper::toDTO)
                .orElse(null);
    }

    /**
     * 创建角色档案，并初始化角色的基本信息和默认武将
     *
     * @param createDTO 角色创建请求数据传输对象
     * @return 新创建的角色数据传输对象
     */
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
        characterGeneralService.initializeDefaultGenerals(profile.getId());
        characterItemService.initializeDefaultItems(profile.getId());

        return mapper.toDTO(savedProfile);
    }

    /**
     * 更新角色档案的主要信息，包括部分或全部字段
     *
     * @param updateDTO 更新请求数据传输对象
     * @return 更新后的角色数据传输对象
     */
    @Override
    @Transactional
    public CharacterProfileDTO updateCharacterProfile(CharacterProfileUpdateDTO updateDTO) {
        logger.info("更新角色档案，角色ID: {}", updateDTO.getCharacterId());
        CharacterProfile profile = characterProfileRepository.findById(updateDTO.getCharacterId())
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + updateDTO.getCharacterId()));

        // 使用 Mapper 进行部分更新
        mapper.updateEntityFromDTO(updateDTO, profile);

        CharacterProfile updatedProfile = characterProfileRepository.save(profile);
        logger.info("角色档案更新成功，角色ID: {}", updateDTO.getCharacterId());
        return mapper.toDTO(updatedProfile);
    }


    /**
     * 检查指定玩家ID是否已存在角色档案
     *
     * @param playerId 平台用户ID
     * @return 存在返回true，否则返回false
     */
    @Override
    public boolean existsByPlayerId(Long playerId) {
        logger.info("检查玩家是否已存在角色档案，Player ID: {}", playerId);
        return characterProfileRepository.existsByPlayerId(playerId);
    }

    /**
     * 检查指定角色名称是否已存在
     *
     * @param name 角色名称
     * @return 存在返回true，否则返回false
     */
    @Override
    public boolean existsByName(String name) {
        logger.info("检查角色名称是否已存在: {}", name);
        return characterProfileRepository.existsByName(name);
    }

    /**
     * 删除角色档案
     *
     * @param characterId 角色ID
     */
    @Override
    public void deleteCharacterProfile(Long characterId) {
        logger.info("删除角色档案，角色ID: {}", characterId);
        characterProfileRepository.deleteById(characterId);
        logger.info("角色档案已删除，角色ID: {}", characterId);
    }

    /**
     * 获取角色的资源信息
     *
     * @param characterId 角色ID
     * @return 角色资源信息数据传输对象
     */
    @Override
    public CharacterProfileResourceInfoDTO getCharacterProfileResourceInfo(Long characterId) {
        logger.info("获取角色资源信息，角色ID: {}", characterId);
        CharacterProfile profile = characterProfileRepository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + characterId));
        return new CharacterProfileResourceInfoDTO(profile);
    }

    /**
     * 获取角色的基本信息
     *
     * @param characterId 角色ID
     * @return 角色基本信息数据传输对象
     */
    @Override
    public CharacterProfileBasicInfoDTO getCharacterProfileBasicInfo(Long characterId) {
        logger.info("获取角色的基本信息，角色ID: {}", characterId);
        CharacterProfile profile = characterProfileRepository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + characterId));
        return new CharacterProfileBasicInfoDTO(profile);
    }

    /**
     * 获取角色的完整信息
     *
     * @param characterId 角色ID
     * @return 角色完整信息数据传输对象
     */
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
