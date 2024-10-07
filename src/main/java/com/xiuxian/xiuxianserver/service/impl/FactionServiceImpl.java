package com.xiuxian.xiuxianserver.service.impl;

import com.xiuxian.xiuxianserver.entity.Faction;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException; // 引入自定义异常
import com.xiuxian.xiuxianserver.repository.FactionRepository;
import com.xiuxian.xiuxianserver.service.FactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * FactionServiceImpl 是 FactionService 的实现类，
 * 负责具体处理与势力相关的业务逻辑。
 */
@Service
public class FactionServiceImpl implements FactionService {

    @Autowired
    private FactionRepository factionRepository;

    /**
     * 获取所有势力的列表。
     *
     * @return 势力列表
     */
    @Override
    public List<Faction> getAllFactions() {
        return factionRepository.findAll();
    }

    /**
     * 根据ID获取特定势力。
     *
     * @param id 势力ID
     * @return 势力详情（Optional 包装）
     */
    @Override
    public Optional<Faction> getFactionById(Long id) {
        return factionRepository.findById(id)
                .or(() -> {
                    throw new ResourceNotFoundException("势力未找到，ID: " + id);
                });
    }
}
