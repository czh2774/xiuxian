package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.CharacterGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CharacterGeneralRepository接口，继承JpaRepository，
 * 提供角色武将实例的CRUD操作。
 */
@Repository
public interface CharacterGeneralRepository extends JpaRepository<CharacterGeneral, Long> {

    /**
     * 根据角色ID查找所有武将实例
     * @param characterId 角色ID
     * @return 角色武将实例列表
     */
    List<CharacterGeneral> findByCharacterId(Long characterId);
}
