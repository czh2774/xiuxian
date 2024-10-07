package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.CharacterProfileResourceInfoDTO;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;

import java.util.List;

/**
 * CharacterProfileService 接口，定义与角色相关的业务逻辑。
 */
public interface CharacterProfileService {

    /**
     * 创建新角色
     * @param characterProfile 新角色信息
     * @return 创建的角色
     */
    CharacterProfile createCharacterProfile(CharacterProfile characterProfile);

    /**
     * 根据角色ID获取角色信息
     * @param characterId 角色ID
     * @return 角色信息
     */
    CharacterProfile getCharacterProfileById(Long characterId);

    /**
     * 更新角色信息
     * @param characterProfile 角色信息
     * @return 更新后的角色信息
     */
    CharacterProfile updateCharacterProfile(CharacterProfile characterProfile);

    /**
     * 根据角色ID删除角色
     * @param characterId 角色ID
     */
    void deleteCharacterProfile(Long characterId);

    /**
     * 获取所有角色信息
     * @return 角色信息列表
     */
    List<CharacterProfile> getAllCharacterProfiles();
    /**
     * 获取角色的资源信息
     * @param characterId 角色ID
     * @return 角色资源信息
     */
    CharacterProfileResourceInfoDTO getResourceInfo(Long characterId);
}
