package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.CharacterGeneralDTO;
import java.util.List;

/**
 * CharacterGeneralService接口，定义角色武将管理的服务合同。
 */
public interface CharacterGeneralService {

    /**
     * 根据ID获取角色武将实例
     * @param id 角色武将实例ID
     * @return 角色武将的DTO对象
     */
    CharacterGeneralDTO getCharacterGeneralById(Long id);

    /**
     * 获取指定角色的所有武将实例
     * @param characterId 角色ID
     * @return 武将实例DTO对象列表
     */
    List<CharacterGeneralDTO> getCharacterGeneralsByCharacterId(Long characterId);

    /**
     * 创建角色武将实例
     * @param request 创建角色武将的请求DTO
     * @return 创建后的角色武将DTO对象
     */
    CharacterGeneralDTO createCharacterGeneral(CharacterGeneralDTO request);

    /**
     * 更新指定ID的角色武将实例
     * @param id 角色武将实例ID
     * @param request 更新请求的DTO对象
     * @return 更新后的角色武将DTO对象
     */
    CharacterGeneralDTO updateCharacterGeneral(Long id, CharacterGeneralDTO request);

    /**
     * 删除指定ID的角色武将实例
     * @param id 角色武将实例ID
     */
    void deleteCharacterGeneral(Long id);
}
