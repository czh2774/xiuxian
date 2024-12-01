package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.CharacterBuildingCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.enums.FeaturePromptType;

import java.util.List;

/**
 * CharacterBuildingService 接口，定义角色建筑实例相关的业务逻辑接口。
 */
public interface CharacterBuildingService {

    /**
     * 获取所有的角色建筑实例。
     *
     * @return 角色建筑实例列表
     */
    List<CharacterBuildingDTO> getAllCharacterBuildings();

    /**
     * 根据角色ID获取该角色的所有建筑实例。
     *
     * 通过角色ID查询该角色下所有的建筑实例。
     *
     * @param characterId 角色的唯一标识符
     * @return 角色建筑实例列表
     */
    List<CharacterBuildingDTO> getCharacterBuildingsByCharacterId(Long characterId);

    /**
     * 根据建筑ID获取单个角色建筑实例。
     *
     * 通过建筑ID查询指定的角色建筑实例。
     *
     * @param buildingId 角色建筑实例的唯一标识符
     * @return 对应的角色建筑实例
     */
    CharacterBuildingDTO getCharacterBuildingById(Long buildingId);

    /**
     * 创建新的角色建筑实例。
     *
     * 为角色创建新的建筑实例，并根据请求体中的信息初始化建筑状态、等级等数据。
     *
     * @param request 创建建筑请求的DTO对象
     * @return 新建的角色建筑实例信息
     */
    CharacterBuildingDTO createCharacterBuildingInstance(CharacterBuildingCreateRequestDTO request);

    /**
     * 更新角色建筑的状态。
     *
     * 更新角色建筑实例的当前状态。例如：建筑升级中、空闲中、收集资源中等状态。
     *
     * @param buildingId 角色建筑实例的唯一标识符
     * @param newStatus  要更新的状态
     */
    void updateCharacterBuildingStatus(Long buildingId, BuildingStatusType newStatus);

    /**
     * 完成建筑升级操作。
     *
     * 当角色建筑的升级完成时，更新建筑的当前等级，并将建筑的状态设置为空闲（IDLE）。
     *
     * @param buildingId 角色建筑实例的唯一标识符
     */
    void completeBuildingUpgrade(Long buildingId);

    /**
     * 更新建筑实例的功能提示信息。
     *
     * 更新角色建筑的功能提示信息，用于显示给玩家。例如，提示可以升级、可以收集资源等信息。
     *
     * @param buildingId 角色建筑实例的唯一标识符
     * @param newPrompt  新的功能提示类型
     */
    void updateFeaturePrompt(Long buildingId, FeaturePromptType newPrompt);


}
