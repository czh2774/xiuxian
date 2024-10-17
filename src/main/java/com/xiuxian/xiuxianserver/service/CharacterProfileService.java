package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.*;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;

import java.util.List;

/**
 * CharacterProfileService
 * 角色档案服务接口，定义角色档案相关的业务逻辑接口。
 */
public interface CharacterProfileService {
    /**
     * 根据用户ID查找角色
     *
     * @param playerId 根据用户ID查找角色
     * @return 根据用户ID查找角色
     */

    CharacterProfile findByPlayerId(Long playerId);

    /**
     * 创建角色档案
     *
     * @param createDTO 创建角色档案的请求DTO
     * @return 新创建的角色档案DTO
     */
    CharacterProfileDTO createCharacterProfile(CharacterProfileCreateDTO createDTO);

    /**
     * 获取角色的基本信息
     *
     * @param characterId 角色ID
     * @return 角色的基本信息DTO
     */
    CharacterProfileBasicInfoDTO getCharacterProfileBasicInfo(Long characterId);
    /**
     * 获取角色的所有信息
     *
     * @param characterId 角色ID
     * @return 角色的所有信息DTO
     */
    CharacterProfileDTO getCharacterProfileAllInfo(Long characterId);


    /**
     * 获取角色的资源信息
     *
     * @param characterId 角色ID
     * @return 角色的资源信息DTO
     */
    CharacterProfileResourceInfoDTO getCharacterProfileResourceInfo(Long characterId);

    /**
     * 更新角色的资源信息
     *
     * @param resourceUpdateDTO 更新资源的DTO
     */
    void updateCharacterProfileResource(CharacterProfileResourceUpdateDTO resourceUpdateDTO);

    /**
     * 更新角色的派系信息
     *
     * @param factionUpdateDTO 派系更新的DTO
     */
    void updateCharacterFaction(CharacterProfileFactionUpdateDTO factionUpdateDTO);

    /**
     * 部分更新角色档案
     *
     * @param partialUpdateDTO 角色档案部分更新请求DTO
     * @return 更新后的角色档案DTO
     */
    CharacterProfileDTO partialUpdateCharacterProfile(CharacterProfilePartialUpdateDTO partialUpdateDTO);

    /**
     * 更新角色档案
     *
     * @param updateDTO 角色档案更新请求DTO
     * @return 更新后的角色档案DTO
     */
    CharacterProfileDTO updateCharacterProfile(CharacterProfileUpdateDTO updateDTO);

    /**
     * 删除角色档案
     *
     * @param characterId 角色ID
     */
    void deleteCharacterProfile(Long characterId);
}
