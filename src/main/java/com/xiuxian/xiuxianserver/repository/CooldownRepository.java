package com.xiuxian.xiuxianserver.repository;

import com.xiuxian.xiuxianserver.entity.Cooldown;
import com.xiuxian.xiuxianserver.enums.CooldownStatus;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 冷却时间管理的 Repository 接口，用于与数据库交互
 */
@Repository
public interface CooldownRepository extends JpaRepository<Cooldown, Long> {

    /**
     * 根据角色ID、类型和目标ID查询特定冷却时间记录
     * @param characterId 角色ID
     * @param type 冷却类型（如建筑升级、科技研究、装备突破）
     * @param targetId 目标ID
     * @return 冷却记录的 Optional 包装对象
     */
    Optional<Cooldown> findByCharacterIdAndTypeAndTargetId(Long characterId, String type, Long targetId);

    /**
     * 查询所有未完成的冷却记录
     * @return 未完成冷却记录的列表
     */
    List<Cooldown> findByIsCompletedFalse();

    /**
     * 查询某角色的所有冷却记录
     * @param characterId 角色ID
     * @return 角色的冷却记录列表
     */
    List<Cooldown> findByCharacterId(Long characterId);

    /**
     * 根据角色ID、类型和队列ID查询未完成的冷却记录
     * @param characterId 角色ID
     * @param type 冷却类型（如建筑升级、科技研究、装备突破）
     * @param queueId 队列ID
     * @return 冷却记录的 Optional 包装对象
     */
    Optional<Cooldown> findByCharacterIdAndTypeAndQueueIdAndIsCompletedFalse(Long characterId, String type, int queueId);

    /**
     * 根据角色ID、类型、队列ID和目标ID查询特定冷却时间记录
     * @param characterId 角色ID
     * @param type 冷却类型
     * @param queueId 队列ID
     * @param targetId 目标ID
     * @return 冷却记录的 Optional 包装对象
     */
    Optional<Cooldown> findByCharacterIdAndTypeAndQueueIdAndTargetId(
        Long characterId, 
        CooldownType type, 
        int queueId, 
        Long targetId
    );

    /**
     * 查询某角色某队列的所有冷却记录
     * @param characterId 角色ID
     * @param type 冷却类型
     * @param queueId 队列ID
     * @return 角色队列的冷却记录列表
     */
    List<Cooldown> findByCharacterIdAndTypeAndQueueId(Long characterId, String type, int queueId);

    /**
     * 根据角色ID和冷却类型查询所有未完成的冷却记录
     * @param characterId 角色ID
     * @param type 冷却类型（如建筑升级、科技研究、装备突破）
     * @return 未完成的冷却记录列表
     */
    List<Cooldown> findByCharacterIdAndTypeAndIsCompletedFalse(Long characterId, String type);

    List<Cooldown> findByEndTimeBeforeAndStatus(LocalDateTime endTime, CooldownStatus status);

    List<Cooldown> findByCharacterIdAndTypeAndStatus(
        Long characterId, 
        CooldownType type, 
        CooldownStatus status
    );

}
