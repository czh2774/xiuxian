package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.xiuxian.xiuxianserver.entity.CharacterBuilding;

import java.util.List;

/**
 * CharacterBuildingRepository
 * 角色建筑存储库接口，继承自JpaRepository
 */
@Repository
public interface CharacterBuildingRepository extends JpaRepository<CharacterBuilding, Long> {

    /**
     * 根据角色ID获取建筑实例
     * @param characterId 角色ID
     * @return 角色建筑实例列表
     */
    List<CharacterBuilding> findByCharacterId(Long characterId);

    /**
     * 根据角色ID和建筑模板ID获取建筑实例
     * @param characterId 角色ID
     * @param buildingTemplateId 建筑模板ID
     * @return 角色特定模板的建筑实例列表
     */
    List<CharacterBuilding> findByCharacterIdAndBuildingTemplateId(Long characterId, Long buildingTemplateId);

    /**
     * 根据角色ID获取特定建筑状态的建筑实例
     * @param characterId 角色ID
     * @param buildingStatus 建筑状态
     * @return 指定状态的建筑实例列表
     */
    List<CharacterBuilding> findByCharacterIdAndBuildingStatus(Long characterId, BuildingStatusType buildingStatus);
}
