package com.xiuxian.xiuxianserver.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.xiuxian.xiuxianserver.entity.Cooldown;
import com.xiuxian.xiuxianserver.dto.CooldownDTO;
import com.xiuxian.xiuxianserver.repository.CooldownRepository;
import com.xiuxian.xiuxianserver.service.CooldownService;
import com.xiuxian.xiuxianserver.enums.CooldownStatus;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import com.xiuxian.xiuxianserver.service.CooldownCompletionCallback;
import com.xiuxian.xiuxianserver.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CooldownServiceImpl implements CooldownService {

    private final CooldownRepository cooldownRepository;
    private static final Logger logger = LoggerFactory.getLogger(CooldownServiceImpl.class);
    private final Snowflake snowflake;
    private final Map<CooldownType, CooldownCompletionCallback> callbacks = new ConcurrentHashMap<>();

    @Value("${scheduler.cooldown.check-interval:5000}")
    private long checkInterval;

    @Value("${scheduler.cooldown.log-enabled:false}")
    private boolean logEnabled;

    @Override
    public CooldownDTO startCooldown(Long characterId, CooldownType type, Long targetId, int durationInSeconds, int queueId) {
        LocalDateTime now = LocalDateTime.now();

        // 最大队列限制检查
        int maxQueues = 2;
        if (queueId > maxQueues) {
            throw new BusinessException("建筑升级队列已满，无法启动新的冷却任务");
        }

        // 创建新的冷却记录
        Cooldown cooldown = new Cooldown();
        cooldown.setId(snowflake.nextId());
        cooldown.setCharacterId(characterId);
        cooldown.setType(type);
        cooldown.setTargetId(targetId);
        cooldown.setQueueId(queueId);
        cooldown.setStartTime(now);
        cooldown.setEndTime(now.plusSeconds(durationInSeconds));
        cooldown.setStatus(CooldownStatus.ACTIVE);

        cooldownRepository.save(cooldown);

        if (logEnabled) {
            logger.info("Started cooldown: characterId={}, type={}, targetId={}, queueId={}, endTime={}", 
                characterId, type, targetId, queueId, cooldown.getEndTime());
        }
        
        return new CooldownDTO(cooldown);
    }

    @Override
    public CooldownDTO getCooldownStatus(Long characterId, CooldownType type, Long targetId, int queueId) {
        Cooldown cooldown = cooldownRepository.findByCharacterIdAndTypeAndQueueIdAndTargetId(characterId, type, queueId, targetId)
                .orElseThrow(() -> new BusinessException("未找到冷却时间记录"));
        return new CooldownDTO(cooldown);
    }

    @Override
    @Transactional
    public boolean accelerateCooldown(Long characterId, CooldownType type, Long targetId, int accelerationTime, int queueId) {
        Cooldown cooldown = cooldownRepository.findByCharacterIdAndTypeAndQueueIdAndTargetId(
            characterId, type, queueId, targetId
        ).orElseThrow(() -> new BusinessException("未找到冷却时间记录"));

        LocalDateTime newEndTime = cooldown.getEndTime().minusSeconds(accelerationTime);
        cooldown.setEndTime(newEndTime);
        cooldown.setStatus(CooldownStatus.ACCELERATED);
        cooldownRepository.save(cooldown);

        if (newEndTime.isBefore(LocalDateTime.now())) {
            completeCooldown(characterId, type, targetId, queueId);
        }

        return true;
    }

    @Override
    public boolean completeCooldown(Long characterId, CooldownType type, Long targetId, int queueId) {
        Cooldown cooldown = cooldownRepository.findByCharacterIdAndTypeAndQueueIdAndTargetId(characterId, type, queueId, targetId)
                .orElseThrow(() -> new BusinessException("未找到冷却时间记录"));
        cooldown.setIsCompleted(true);
        cooldownRepository.save(cooldown);
        return true;
    }

    @Override
    public List<CooldownDTO> getCharacterCooldowns(Long characterId) {
        return cooldownRepository.findByCharacterId(characterId)
            .stream()
            .map(cooldown -> new CooldownDTO(cooldown))
            .collect(Collectors.toList());
    }

    @Override
    public void registerCallback(CooldownType type, CooldownCompletionCallback callback) {
        callbacks.put(type, callback);
    }

    @Scheduled(fixedRateString = "${scheduler.cooldown.check-interval:5000}")
    @Transactional
    public void checkCooldowns() {
        List<Cooldown> expiredCooldowns = cooldownRepository
            .findByEndTimeBeforeAndStatus(LocalDateTime.now(), CooldownStatus.ACTIVE);
            
        if (!expiredCooldowns.isEmpty()) {
            processExpiredCooldowns(expiredCooldowns);
        }
    }
    
    private void processExpiredCooldowns(List<Cooldown> expiredCooldowns) {
        if (logEnabled) {
            logger.debug("Processing {} expired cooldowns", expiredCooldowns.size());
        }
        
        for (Cooldown cooldown : expiredCooldowns) {
            processSingleCooldown(cooldown);
        }
    }
    
    private void processSingleCooldown(Cooldown cooldown) {
        try {
            CooldownCompletionCallback callback = callbacks.get(cooldown.getType());
            if (callback != null) {
                callback.onCooldownComplete(cooldown);
                cooldown.setStatus(CooldownStatus.COMPLETED);
                cooldownRepository.save(cooldown);
                
                if (logEnabled) {
                    logger.debug("Completed cooldown: type={}, targetId={}", 
                        cooldown.getType(), cooldown.getTargetId());
                }
            }
        } catch (Exception e) {
            logger.error("Failed to process cooldown: {}", e.getMessage(), e);
        }
    }
}
