package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.CharacterGeneral;
import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import java.util.List;

/**
 * CharacterInitializationService接口，定义角色初始化服务的方法。
 * 该接口负责管理角色的初始状态，包括道具、武将和建筑的创建与配置。
 */
public interface CharacterInitializationService {

    /**
     * 初始化角色的默认道具列表
     * @param characterId 角色ID
     */
    void initializeDefaultItems(long characterId);

    /**
     * 初始化角色的默认武将列表
     * @param characterId 角色ID
     * @return 初始化后的武将列表
     */
    List<CharacterGeneral> initializeDefaultGenerals(Long characterId);

    /**
     * 初始化角色的建筑
     * @param characterId 新创建角色的ID
     */
    void initializeBuildingsForCharacter(Long characterId);

    /**
     * 为角色创建默认建筑
     * @param characterId 角色ID
     */
    void createDefaultBuildingsForCharacter(Long characterId);
}
