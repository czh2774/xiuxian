package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.CooldownDTO;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import java.util.List;

/**
 * 冷却时间管理服务接口
 */
public interface CooldownService {

    /**
     * 启动冷却
     *
     * @param characterId 角色ID
     * @param type 冷却类型
     * @param targetId 目标ID
     * @param durationInSeconds 持续时间（秒）
     * @param queueId 队列ID
     * @return 冷却DTO
     */
    CooldownDTO startCooldown(Long characterId, CooldownType type, Long targetId, int durationInSeconds, int queueId);

    /**
     * 查询冷却任务状态
     *
     * @param characterId  用户ID
     * @param type 冷却类型
     * @param targetId 目标ID
     * @param queueId 队列ID
     * @return 冷却任务详情
     */
    CooldownDTO getCooldownStatus(Long characterId, CooldownType type, Long targetId, int queueId);

    /**
     * 加速冷却任务
     *
     * @param characterId  用户ID
     * @param type 冷却类型
     * @param targetId 目标ID
     * @param accelerationTime 加速时间（秒）
     * @param queueId 队列ID
     * @return 是否加速成功
     */
    boolean accelerateCooldown(Long characterId, CooldownType type, Long targetId, int accelerationTime, int queueId);

    /**
     * 手动完成冷却任务
     *
     * @param characterId  用户ID
     * @param type 冷却类型
     * @param targetId 目标ID
     * @param queueId 队列ID
     * @return 是否完成成功
     */
    boolean completeCooldown(Long characterId, CooldownType type, Long targetId, int queueId);

    List<CooldownDTO> getCharacterCooldowns(Long characterId);
}
