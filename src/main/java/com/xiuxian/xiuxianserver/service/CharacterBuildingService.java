package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import java.util.List;

/**
 * CharacterBuildingService 接口，定义角色建筑实例相关的业务逻辑接口。
 * 提供角色建筑的查询、创建、升级、状态管理等功能。
 */
public interface CharacterBuildingService {



    /**
     * 根据角色ID获取该角色的所有建筑实例。
     *
     * @param characterId 角色的唯一标识符
     * @return 该角色的建筑实例列表
     */
    List<CharacterBuildingDTO> getCharacterBuildingsByCharacterId(Long characterId);

    /**
     * 根据建筑ID获取单个角色建筑实例。
     *
     * @param buildingId 建筑实例的唯一标识符
     * @return 对应的角色建筑实例
     */
    CharacterBuildingDTO getCharacterBuildingById(Long buildingId);

    /**
     * 更新角色建筑的状态。
     *
     * @param buildingId 建筑实例的唯一标识符
     * @param status     更新后的建筑状态
     */
    void updateBuildingStatus(Long buildingId, BuildingStatusType status);

}
