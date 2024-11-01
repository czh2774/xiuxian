package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.CharacterGeneralDTO;
import com.xiuxian.xiuxianserver.entity.CharacterGeneral;

import java.util.List;

/**
 * CharacterGeneralService接口
 * 定义角色武将相关的业务逻辑接口
 */
public interface CharacterGeneralService {

    /**
     * 根据ID获取武将数据
     *
     * @param id 武将ID
     * @return 武将数据传输对象
     */
    CharacterGeneralDTO getCharacterGeneralById(Long id);

    /**
     * 根据角色ID获取武将列表
     *
     * @param characterId 角色ID
     * @return 武将数据传输对象列表
     */
    List<CharacterGeneralDTO> getCharacterGeneralsByCharacterId(Long characterId);

    /**
     * 创建新的武将
     *
     * @param request 武将请求数据传输对象
     * @return 创建的武将数据传输对象
     */
    CharacterGeneralDTO createCharacterGeneral(CharacterGeneralDTO request);

    /**
     * 更新武将数据
     *
     * @param id      武将ID
     * @param request 武将请求数据传输对象
     * @return 更新后的武将数据传输对象
     */
    CharacterGeneralDTO updateCharacterGeneral(Long id, CharacterGeneralDTO request);

    /**
     * 删除武将
     *
     * @param id 武将ID
     */
    void deleteCharacterGeneral(Long id);

    /**
     * 初始化默认的武将列表
     *
     * @param characterId 角色ID
     * @return 初始化后的武将列表
     */
    List<CharacterGeneral> initializeDefaultGenerals(Long characterId);
}
