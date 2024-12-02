package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.entity.Cooldown;
import com.xiuxian.xiuxianserver.dto.CooldownDTO;
import com.xiuxian.xiuxianserver.repository.CooldownRepository;
import com.xiuxian.xiuxianserver.service.CooldownService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 冷却时间管理服务实现类
 */
@Service
@RequiredArgsConstructor
public class CooldownServiceImpl implements CooldownService {

    private final CooldownRepository cooldownRepository;
    private static final Logger logger = LoggerFactory.getLogger(CooldownServiceImpl.class);
    private final Snowflake snowflake;

    @Override
    public CooldownDTO startCooldown(Long characterId, String type, Long targetId, int durationInSeconds) {
        LocalDateTime now = LocalDateTime.now();

        // 查询角色当前未完成的冷却任务
        List<Cooldown> activeCooldowns = cooldownRepository.findByCharacterIdAndTypeAndIsCompletedFalse(characterId, type);

        // 分配队列ID：从1开始，选择最小的空闲队列
        int queueId = 1; // 队列ID从1开始
        for (Cooldown cooldown : activeCooldowns) {
            if (cooldown.getQueueId() == queueId) {
                queueId++; // 如果队列ID被占用，尝试下一个
            } else {
                break; // 找到空闲队列
            }
        }

        // 最大队列限制检查
        int maxQueues = 2; // 假设最大支持2个队列，可以从配置中加载
        if (queueId > maxQueues) {
            throw new RuntimeException("建筑升级队列已满，无法启动新的冷却任务");
        }

        // 创建新的冷却记录
        Cooldown cooldown = new Cooldown();
        cooldown.setId(snowflake.nextId()); // 生成雪花ID
        cooldown.setCharacterId(characterId);
        cooldown.setType(type);
        cooldown.setTargetId(targetId);
        cooldown.setQueueId(queueId); // 自动分配的队列ID
        cooldown.setStartTime(now);
        cooldown.setEndTime(now.plusSeconds(durationInSeconds));
        cooldown.setIsCompleted(false);

        // 保存冷却记录
        cooldownRepository.save(cooldown);

        logger.info("冷却启动：角色ID={}, 类型={}, 目标ID={}, 队列ID={}, 结束时间={}", characterId, type, targetId, queueId, cooldown.getEndTime());
        return new CooldownDTO(cooldown);
    }


    @Override
    public CooldownDTO getCooldownStatus(Long characterId, String type, Long targetId, int queueId) {
        Cooldown cooldown = cooldownRepository.findByCharacterIdAndTypeAndQueueIdAndTargetId(characterId, type, queueId, targetId)
                .orElseThrow(() -> new RuntimeException("未找到冷却时间记录"));
        logger.info("查询冷却状态: 用户ID={}, 类型={}, 队列ID={}, 目标ID={}, 当前状态={}", characterId, type, queueId, targetId, cooldown.getIsCompleted());
        return new CooldownDTO(cooldown);
    }

    @Override
    public boolean accelerateCooldown(Long userId, String type, Long targetId, int accelerationTime, int queueId) {
        Cooldown cooldown = cooldownRepository.findByCharacterIdAndTypeAndQueueIdAndTargetId(userId, type, queueId, targetId)
                .orElseThrow(() -> new RuntimeException("未找到冷却时间记录"));
        cooldown.setEndTime(cooldown.getEndTime().minusSeconds(accelerationTime));
        cooldownRepository.save(cooldown);
        logger.info("加速冷却: 用户ID={}, 类型={}, 队列ID={}, 目标ID={}, 加速时间={}秒", userId, type, queueId, targetId, accelerationTime);
        return true;
    }

    @Override
    public boolean completeCooldown(Long characterId, String type, Long targetId, int queueId) {
        Cooldown cooldown = cooldownRepository.findByCharacterIdAndTypeAndQueueIdAndTargetId(characterId, type, queueId, targetId)
                .orElseThrow(() -> new RuntimeException("未找到冷却时间记录"));
        cooldown.setIsCompleted(true);
        cooldownRepository.save(cooldown);
        logger.info("手动完成冷却: 用户ID={}, 类型={}, 队列ID={}, 目标ID={}", characterId, type, queueId, targetId);
        return true;
    }
}
