package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.dto.*;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterProfileRepository;
import com.xiuxian.xiuxianserver.service.CharacterItemService;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import com.xiuxian.xiuxianserver.service.CharacterGeneralService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.xiuxian.xiuxianserver.util.UserContextUtil.getCurrentUser;

/**
 * CharacterProfileServiceImpl
 * 角色档案服务实现类，负责角色的创建、查询和更新逻辑
 */
@Service
public class CharacterProfileServiceImpl implements CharacterProfileService {
    private static final Logger logger = LoggerFactory.getLogger(CharacterProfileServiceImpl.class);
    @Autowired
    private CharacterItemService characterItemService;
    private final CharacterProfileRepository characterProfileRepository;
    private final CharacterGeneralService characterGeneralService;
    private final Snowflake snowflake;
    private static final int MAX_NAME_LENGTH = 50; // 角色名称最大长度

    @Autowired
    public CharacterProfileServiceImpl(CharacterProfileRepository characterProfileRepository, Snowflake snowflake,
                                       CharacterGeneralService characterGeneralService) {
        this.characterProfileRepository = characterProfileRepository;
        this.snowflake = snowflake;
        this.characterGeneralService = characterGeneralService;
    }

    /**
     * 根据玩家ID获取角色数据
     *
     * @param playerId 平台用户的ID
     * @return CharacterProfileDTO 角色数据传输对象，如果不存在则返回null
     */
    @Override
    public CharacterProfileDTO getCharacterByPlayerId(Long playerId) {
        Optional<CharacterProfile> characterProfileOptional = characterProfileRepository.findByPlayerId(playerId);

        if (characterProfileOptional.isEmpty()) {
            return null;
        }

        CharacterProfile characterProfile = characterProfileOptional.get();
        return new CharacterProfileDTO(characterProfile);  // 手动转换
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

        // 校验角色名称
        if (createDTO.getName() == null || createDTO.getName().length() > MAX_NAME_LENGTH) {
            logger.error("角色名称无效: {}", createDTO.getName());
            throw new IllegalArgumentException("角色名称不能为空或过长");
        }

        // 获取当前登录用户信息
        UserDTO currentUser = getCurrentUser();
        if (currentUser == null) {
            logger.error("当前用户未登录");
            throw new IllegalStateException("当前用户未登录");
        }
        logger.info("当前用户: {}", currentUser.getPlatformUserId());

        // 创建角色档案实例
        CharacterProfile profile = new CharacterProfile();
        Long characterId = snowflake.nextId();  // 生成唯一角色ID
        logger.debug("生成角色ID: {}", characterId);

        // 设置角色的基本属性
        profile.setId(characterId);
        profile.setName(createDTO.getName());
        profile.setFaction(createDTO.getFaction());
        profile.setOfficialPosition("Governor");  // 默认职位为Governor
        profile.setPlayerId(currentUser.getPlatformUserId());

        // 保存角色档案到数据库
        CharacterProfile savedProfile = characterProfileRepository.save(profile);
        logger.info("角色档案保存成功，角色ID: {}", savedProfile.getId());

        // 初始化默认武将列表，通过逻辑上的 characterId 关联
        characterGeneralService.initializeDefaultGenerals(characterId);
        characterItemService.initializeDefaultItems(characterId);
        return new CharacterProfileDTO(savedProfile);  // 转换为数据传输对象返回
    }

    /**
     * 获取角色的基本信息
     *
     * @param characterId 角色ID
     * @return 角色基本信息数据传输对象
     */
    @Override
    public CharacterProfileBasicInfoDTO getCharacterProfileBasicInfo(Long characterId) {
        CharacterProfile profile = characterProfileRepository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + characterId));

        return new CharacterProfileBasicInfoDTO(profile);  // 转换为基本信息DTO返回
    }

    /**
     * 获取角色的完整信息
     *
     * @param characterId 角色ID
     * @return 角色完整信息数据传输对象
     */
    @Override
    public CharacterProfileDTO getCharacterProfileAllInfo(Long characterId) {
        CharacterProfile profile = characterProfileRepository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + characterId));

        return new CharacterProfileDTO(profile);  // 转换为完整信息DTO返回
    }

    /**
     * 检查指定玩家ID是否已存在角色档案
     *
     * @param playerId 平台用户ID
     * @return 存在返回true，否则返回false
     */
    @Override
    public boolean existsByPlayerId(Long playerId) {
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
        return characterProfileRepository.existsByName(name);
    }




    /**
     * 更新角色的派系
     *
     * @param factionUpdateDTO 更新请求数据传输对象
     */
    @Override
    @Transactional
    public void updateCharacterFaction(CharacterProfileFactionUpdateDTO factionUpdateDTO) {
        CharacterProfile profile = characterProfileRepository.findById(factionUpdateDTO.getCharacterId())
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + factionUpdateDTO.getCharacterId()));

        // 更新派系信息
        profile.setFaction(factionUpdateDTO.getFaction());
        characterProfileRepository.save(profile);
        logger.info("角色派系更新成功，角色ID: {}，新派系: {}", factionUpdateDTO.getCharacterId(), factionUpdateDTO.getFaction());
    }

    /**
     * 部分更新角色档案
     *
     * @param partialUpdateDTO 部分更新请求数据传输对象
     * @return 更新后的角色数据传输对象
     */
    @Override
    @Transactional
    public CharacterProfileDTO partialUpdateCharacterProfile(CharacterProfilePartialUpdateDTO partialUpdateDTO) {
        CharacterProfile profile = characterProfileRepository.findById(partialUpdateDTO.getCharacterId())
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + partialUpdateDTO.getCharacterId()));

        // 更新可选字段
        if (partialUpdateDTO.getName() != null) {
            profile.setName(partialUpdateDTO.getName());
            logger.debug("更新角色名称为: {}", partialUpdateDTO.getName());
        }

        if (partialUpdateDTO.getAvatar() != null) {
            profile.setAvatar(partialUpdateDTO.getAvatar());
            logger.debug("更新角色头像");
        }

        characterProfileRepository.save(profile);
        logger.info("角色档案部分更新成功，角色ID: {}", partialUpdateDTO.getCharacterId());

        return new CharacterProfileDTO(profile);  // 转换为数据传输对象返回
    }

    /**
     * 删除角色档案
     *
     * @param characterId 角色ID
     */
    @Override
    public void deleteCharacterProfile(Long characterId) {
        characterProfileRepository.deleteById(characterId);
        logger.info("角色档案已删除，角色ID: {}", characterId);
    }

    /**
     * 根据玩家ID查找角色档案
     *
     * @param playerId 玩家ID
     * @return 角色档案
     */
    @Override
    public CharacterProfile findByPlayerId(Long playerId) {
        return characterProfileRepository.findByPlayerId(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("角色未找到，Player ID: " + playerId));
    }
    /**
     * 更新角色的资源信息
     *
     * @param dto 更新请求数据传输对象
     */
    @Override
    @Transactional
    public void updateCharacterProfileResource(CharacterProfileResourceUpdateDTO dto) {
        CharacterProfile profile = characterProfileRepository.findById(dto.getCharacterId())
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + dto.getCharacterId()));

        // 更新资源信息逻辑
        profile.setYuanbao(dto.getYuanbao());
        profile.setWarMerits(dto.getWarMerits());
        profile.setReputation(dto.getReputation());
        profile.setCopperCoins(dto.getCopperCoins());
        profile.setFood(dto.getFood());
        profile.setWood(dto.getWood());
        profile.setIronOre(dto.getIronOre());

        characterProfileRepository.save(profile);
    }


    @Override
    public CharacterProfileResourceInfoDTO getCharacterProfileResourceInfo(Long characterId) {
        CharacterProfile profile = characterProfileRepository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + characterId));

        // 使用 CharacterProfile 对象来构造 DTO
        return new CharacterProfileResourceInfoDTO(profile);
    }
    @Override
    public CharacterProfileDTO updateCharacterProfile(CharacterProfileUpdateDTO updateDTO) {
        // 查找角色档案
        CharacterProfile profile = characterProfileRepository.findById(updateDTO.getCharacterId())
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + updateDTO.getCharacterId()));

        // 更新角色的可选字段
        if (updateDTO.getName() != null) {
            profile.setName(updateDTO.getName());
        }
        if (updateDTO.getAvatar() != null) {
            profile.setAvatar(updateDTO.getAvatar());
        }
        if (updateDTO.getFaction() != null) {
            profile.setFaction(updateDTO.getFaction());
        }

        // 保存更新后的角色档案
        CharacterProfile updatedProfile = characterProfileRepository.save(profile);

        // 返回更新后的角色DTO
        return new CharacterProfileDTO(updatedProfile);
    }


}


