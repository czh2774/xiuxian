package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.entity.Faction;

import java.util.List;
import java.util.Optional;

/**
 * FactionService 接口定义与势力相关的业务逻辑。
 */
public interface FactionService {

    /**
     * 获取所有势力的列表。
     *
     * @return 势力列表
     */
    List<Faction> getAllFactions();

    /**
     * 根据ID获取特定势力。
     *
     * @param id 势力ID
     * @return 势力详情（Optional 包装）
     */
    Optional<Faction> getFactionById(Long id);
}
