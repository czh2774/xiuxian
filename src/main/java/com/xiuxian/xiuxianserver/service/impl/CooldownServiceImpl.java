package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.entity.Cooldown;
import com.xiuxian.xiuxianserver.dto.CooldownDTO;
import com.xiuxian.xiuxianserver.repository.CooldownRepository;
import com.xiuxian.xiuxianserver.service.CooldownService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.xiuxian.xiuxianserver.enums.CooldownStatus;
import com.xiuxian.xiuxianserver.handler.CooldownHandlerManager;
import com.xiuxian.xiuxianserver.enums.CooldownType;

/**
 * 冷却时间管理服务实现类
 */
@Service
@RequiredArgsConstructor
public class CooldownServiceImpl implements CooldownService {

    private final CooldownRepository cooldownRepository;
    private static final Logger logger = LoggerFactory.getLogger(CooldownServiceImpl.class);
    private final Snowflake snowflake;
    private final CooldownHandlerManager handlerManager;

    @Override
    public CooldownDTO startCooldown(Long characterId, CooldownType type, Long targetId, int durationInSeconds, int queueId) {
        LocalDateTime now = LocalDateTime.now();

        // 最大队列限制检查
        int maxQueues = 2;
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
    public CooldownDTO getCooldownStatus(Long characterId, CooldownType type, Long targetId, int queueId) {
        Cooldown cooldown = cooldownRepository.findByCharacterIdAndTypeAndQueueIdAndTargetId(characterId, type, queueId, targetId)
                .orElseThrow(() -> new RuntimeException("未找到冷却时间记录"));
        return new CooldownDTO(cooldown);
    }

    @Override
    public boolean accelerateCooldown(Long characterId, CooldownType type, Long targetId, int accelerationTime, int queueId) {
        Cooldown cooldown = cooldownRepository.findByCharacterIdAndTypeAndQueueIdAndTargetId(characterId, type, queueId, targetId)
                .orElseThrow(() -> new RuntimeException("未找到冷却时间记录"));
        cooldown.setEndTime(cooldown.getEndTime().minusSeconds(accelerationTime));
        cooldownRepository.save(cooldown);
        return true;
    }

    @Override
    public boolean completeCooldown(Long characterId, CooldownType type, Long targetId, int queueId) {
        Cooldown cooldown = cooldownRepository.findByCharacterIdAndTypeAndQueueIdAndTargetId(characterId, type, queueId, targetId)
                .orElseThrow(() -> new RuntimeException("未找到冷却时间记录"));
        cooldown.setIsCompleted(true);
        cooldownRepository.save(cooldown);
        return true;
    }

    // 添加定时任务
    @Scheduled(fixedRate = 1000) // 每秒执行一次
    @Transactional
    public void checkCooldowns() {
        List<Cooldown> expiredCooldowns = cooldownRepository
            .findByEndTimeBeforeAndStatus(LocalDateTime.now(), CooldownStatus.ACTIVE);
            
        for (Cooldown cooldown : expiredCooldowns) {
            try {
                handlerManager.handleCooldownComplete(cooldown);
                cooldown.setStatus(CooldownStatus.COMPLETED);
                cooldownRepository.save(cooldown);
            } catch (Exception e) {
                logger.error("处理冷却完成失败: {}", e.getMessage(), e);
            }
        }
    }

    @Override
    public List<CooldownDTO> getCharacterCooldowns(Long characterId) {
        return cooldownRepository.findByCharacterId(characterId)
            .stream()
            .map(cooldown -> new CooldownDTO(cooldown))
            .collect(Collectors.toList());
    }
}
