package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.config.SecurityConfig;
import com.xiuxian.xiuxianserver.dto.*;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import com.xiuxian.xiuxianserver.entity.CharacterProfileMapper;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.repository.CharacterProfileRepository;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.xiuxian.xiuxianserver.util.UserContextUtil.getCurrentUser;

/**
 * CharacterProfileServiceImpl
 * 实现角色档案服务接口的具体业务逻辑
 */
@Service
public class CharacterProfileServiceImpl implements CharacterProfileService {
    private static final Logger logger = LoggerFactory.getLogger(CharacterProfileServiceImpl.class);

    private final CharacterProfileRepository characterProfileRepository;
    private static final int MAX_NAME_LENGTH = 50; // 定义常量
    private  final  Snowflake snowflake ; // 初始化雪花ID生成器
    @Autowired
    public CharacterProfileServiceImpl(CharacterProfileRepository characterProfileRepository,Snowflake snowflake) {
        this.characterProfileRepository = characterProfileRepository;
        this.snowflake = snowflake;

    }

    @Override
    public CharacterProfile findByPlayerId(Long playerId) {
        return characterProfileRepository.findByPlayerId(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("角色未找到，Player ID: " + playerId));
    }




    /**
     * 根据玩家ID获取角色数据
     *
     * @param playerId 平台用户的ID
     * @return CharacterProfileDTO 角色数据传输对象，如果不存在则返回null
     */
    @Override
    public CharacterProfileDTO getCharacterByPlayerId(Long playerId) {
        // 使用 Optional 解包处理
        Optional<CharacterProfile> characterProfileOptional = characterProfileRepository.findByPlayerId(playerId);

        // 检查是否存在角色数据
        if (characterProfileOptional.isEmpty()) {
            return null;
        }

        // 获取角色数据并转换为 DTO
        CharacterProfile characterProfile = characterProfileOptional.get();
        return CharacterProfileMapper.INSTANCE.toDTO(characterProfile);
    }



    @Override
    public CharacterProfileDTO createCharacterProfile(CharacterProfileCreateDTO createDTO) {
        // 开始记录创建角色日志
        logger.info("开始创建角色: {}", createDTO);

        if (createDTO.getName() == null || createDTO.getName().length() > MAX_NAME_LENGTH) {
            logger.error("角色名称无效: {}", createDTO.getName());
            throw new IllegalArgumentException("Name cannot be null or too long");
        }

        // 检查当前用户是否登录
        UserDTO currentUser = getCurrentUser();
        if (currentUser == null) {
            logger.error("当前用户未登录");
            throw new IllegalStateException("当前用户未登录");
        }
        logger.info("当前用户: {}", currentUser.getPlatformUserId());

        // 初始化角色档案属性，如角色名、资源等
        CharacterProfile profile = new CharacterProfile();
        logger.debug("初始化角色档案对象");

        // 生成雪花ID
        Long characterId = Long.valueOf(snowflake.nextId());
        logger.debug("生成角色ID: {}", characterId);

        profile.setId(characterId);
        profile.setName(createDTO.getName());
        profile.setFaction(createDTO.getFaction());
        profile.setOfficialPosition("Governor");  // 固定值
        profile.setPlayerId(currentUser.getPlatformUserId());

        // 将角色保存到数据库
        CharacterProfile savedProfile = characterProfileRepository.save(profile);
        logger.info("角色档案保存成功，角色ID: {}", savedProfile.getId());

        // 返回保存的角色信息
        return new CharacterProfileDTO(savedProfile);
    }


    @Override
    public CharacterProfileBasicInfoDTO getCharacterProfileBasicInfo(Long characterId) {
        CharacterProfile profile = characterProfileRepository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + characterId));

        return new CharacterProfileBasicInfoDTO(profile);
    }

    @Override
    public CharacterProfileDTO getCharacterProfileAllInfo(Long characterId) {
        CharacterProfile profile = characterProfileRepository.findById(characterId)
            .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + characterId));

        return new CharacterProfileDTO(profile);
    }

    @Override
    public CharacterProfileResourceInfoDTO getCharacterProfileResourceInfo(Long characterId) {
        CharacterProfile profile = characterProfileRepository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + characterId));

        return new CharacterProfileResourceInfoDTO(profile);
    }



    @Override
    public void updateCharacterProfileResource(CharacterProfileResourceUpdateDTO dto) {
        // 实现逻辑
    }

    @Override
    public void updateCharacterFaction(CharacterProfileFactionUpdateDTO factionUpdateDTO) {
        CharacterProfile profile = characterProfileRepository.findById(factionUpdateDTO.getCharacterId())
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + factionUpdateDTO.getCharacterId()));

        // 更新角色派系信息
        // profile.setFaction(factionUpdateDTO.getNewFaction());

        characterProfileRepository.save(profile);
    }

    @Override
    public CharacterProfileDTO partialUpdateCharacterProfile(CharacterProfilePartialUpdateDTO partialUpdateDTO) {
        CharacterProfile profile = characterProfileRepository.findById(partialUpdateDTO.getCharacterId())
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + partialUpdateDTO.getCharacterId()));

        // 更新部分字段：只有 DTO 中非 null 的字段才会被更新
        if (partialUpdateDTO.getName() != null) {
            profile.setName(partialUpdateDTO.getName());
        }



        if (partialUpdateDTO.getAvatar() != null) {
            profile.setAvatar(partialUpdateDTO.getAvatar());
        }

        // 其他需要更新的字段可以按此方式添加...

        // 保存更新后的角色档案
        characterProfileRepository.save(profile);

        // 返回更新后的角色档案数据
        return new CharacterProfileDTO(profile);
    }

    @Override
    public boolean existsByPlayerId(Long playerId) {
        return characterProfileRepository.existsByPlayerId(playerId); // 假设你在 repository 中实现了这个查询方法
    }

    @Override
    public boolean existsByName(String name) {
        return characterProfileRepository.existsByName(name); // 假设你在 repository 中实现了这个查询方法
    }
    @Override
    public CharacterProfileDTO updateCharacterProfile(@Valid CharacterProfileUpdateDTO updateDTO) {
        System.out.println("Character ID: " + updateDTO.getCharacterId());

        // 查找角色档案，如果不存在则抛出异常
        CharacterProfile profile = characterProfileRepository.findById(updateDTO.getCharacterId())
                .orElseThrow(() -> new ResourceNotFoundException("角色档案未找到，ID：" + updateDTO.getCharacterId()));

        // 更新角色档案中的各个字段
        if (updateDTO.getName() != null) {
            profile.setName(updateDTO.getName());
        }

        if (updateDTO.getFaction() != null) {
            profile.setFaction(updateDTO.getFaction());
        }

        if (updateDTO.getAvatar() != null) {
            profile.setAvatar(updateDTO.getAvatar());
        }


        // 保存更新后的角色档案
        characterProfileRepository.save(profile);

        // 返回更新后的角色档案数据
        return new CharacterProfileDTO(profile);
    }


    @Override
    public void deleteCharacterProfile(Long characterId) {
        characterProfileRepository.deleteById(characterId);
    }
}
