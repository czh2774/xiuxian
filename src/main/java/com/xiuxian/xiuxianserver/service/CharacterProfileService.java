package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.*;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;

/**
 * CharacterProfileService
 * 角色档案服务接口，定义角色档案相关的业务逻辑接口。
 */
public interface CharacterProfileService {

    /**
     * 根据用户ID查找角色
     *
     * @param playerId 根据用户ID查找角色
     * @return 角色实体对象
     */
    CharacterProfile findByPlayerId(Long playerId);

    /**
     * 根据玩家ID获取角色数据传输对象
     *
     * @param playerId 平台用户的ID
     * @return CharacterProfileDTO 角色数据传输对象，如果不存在则返回null
     */
    CharacterProfileDTO getCharacterByPlayerId(Long playerId);

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
     * 更新角色的资源和其他信息
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

    /**
     * 检查指定用户ID是否已创建角色
     *
     * @param playerId 用户ID
     * @return 如果存在返回true，否则返回false
     */
    boolean existsByPlayerId(Long playerId);

    /**
     * 检查角色名称是否重复
     *
     * @param name 角色名称
     * @return 如果名称已存在返回true，否则返回false
     */
    boolean existsByName(String name);


    /**
     * 检查角色资源是否足够
     * @param characterId 角色ID
     * @param resourceType 资源类型
     * @param requiredAmount 所需数量
     * @return true 如果资源足够
     */
    boolean hasSufficientResource(Long characterId, String resourceType, int requiredAmount);

    /**
     * 扣除角色资源
     * @param characterId 角色ID
     * @param resourceType 资源类型
     * @param amount 扣除数量
     */
    void deductResource(Long characterId, String resourceType, int amount);

    /**
     *增加角色资源
     * @param characterId
     * @param resourceType
     * @param amount
     */
    void addResource(Long characterId, String resourceType, int amount) ;

}
