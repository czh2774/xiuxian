package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.CharacterItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CharacterItemRepository接口，继承JpaRepository，
 * 提供角色道具实例的CRUD操作。
 */
@Repository
public interface CharacterItemRepository extends JpaRepository<CharacterItem, Long> {

    /**
     * 根据角色ID查找所有道具实例
     * @param characterId 角色ID
     * @return 角色道具实例列表
     */
    List<CharacterItem> findByCharacterId(Long characterId);
}
