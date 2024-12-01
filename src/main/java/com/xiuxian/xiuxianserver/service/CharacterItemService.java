package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.CharacterItemDTO;
import java.util.List;

/**
 * CharacterItemService接口，定义角色道具管理的服务合同。
 */
public interface CharacterItemService {

    /**
     * 根据ID获取角色道具实例
     * @param itemInstanceId 道具实例ID
     * @return 角色道具的DTO对象
     */
    CharacterItemDTO getCharacterItemById(long itemInstanceId);

    /**
     * 获取指定角色的所有道具实例
     * @param characterId 角色ID
     * @return 角色道具实例DTO对象列表
     */
    List<CharacterItemDTO> getCharacterItemsByCharacterId(long characterId);

    /**
     * 创建角色道具实例
     * @param request 创建角色道具的请求DTO
     * @return 创建后的角色道具DTO对象
     */
    CharacterItemDTO createCharacterItem(CharacterItemDTO request);

    /**
     * 更新指定道具实例的记录
     * @param itemInstanceId 道具实例ID
     * @param request 更新请求的DTO对象
     * @return 更新后的角色道具DTO对象
     */
    CharacterItemDTO updateCharacterItem(long itemInstanceId, CharacterItemDTO request);

    /**
     * 删除指定ID的角色道具实例
     * @param itemInstanceId 道具实例ID
     */
    void deleteCharacterItem(long itemInstanceId);

    /**
     * 使用指定的道具实例
     * @param itemInstanceId 道具实例ID
     * @param quantity 使用数量
     */
    void useItem(long itemInstanceId, int quantity);



}
