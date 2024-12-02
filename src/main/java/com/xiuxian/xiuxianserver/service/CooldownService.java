package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.CooldownDTO;

/**
 * 冷却时间管理服务接口
 */
public interface CooldownService {

    /**
     * 启动新的冷却任务
     *
     * @param characterId  用户ID
     * @param type 冷却类型（如建筑升级、科技研究、装备突破）
     * @param targetId 目标ID
     * @param durationInSeconds 冷却持续时间（秒）
     * @return 冷却任务详情
     */
    CooldownDTO startCooldown(Long characterId, String type, Long targetId, int durationInSeconds);

    /**
     * 查询冷却任务状态
     *
     * @param characterId  用户ID
     * @param type 冷却类型
     * @param targetId 目标ID
     * @param queueId 队列ID
     * @return 冷却任务详情
     */
    CooldownDTO getCooldownStatus(Long characterId , String type, Long targetId, int queueId);

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
    boolean accelerateCooldown(Long characterId , String type, Long targetId, int accelerationTime, int queueId);

    /**
     * 手动完成冷却任务
     *
     * @param characterId  用户ID
     * @param type 冷却类型
     * @param targetId 目标ID
     * @param queueId 队列ID
     * @return 是否完成成功
     */
    boolean completeCooldown(Long characterId , String type, Long targetId, int queueId);
}
