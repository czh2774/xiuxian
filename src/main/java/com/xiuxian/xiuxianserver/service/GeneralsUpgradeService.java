package com.xiuxian.xiuxianserver.service;

import com.xiuxian.xiuxianserver.dto.GeneralsUpgradeDTO;
import java.util.List;

/**
 * GeneralsUpgradeService接口，定义武将升级管理的服务合同。
 */
public interface GeneralsUpgradeService {

    /**
     * 根据ID获取武将升级记录
     * @param id 武将升级记录ID
     * @return 武将升级的DTO对象
     */
    GeneralsUpgradeDTO getGeneralsUpgradeById(Long id);

    /**
     * 获取所有武将升级记录
     * @return 武将升级DTO对象列表
     */
    List<GeneralsUpgradeDTO> getAllGeneralsUpgrades();

    /**
     * 创建新的武将升级记录
     * @param request 创建升级记录的请求DTO
     * @return 创建后的武将升级DTO对象
     */
    GeneralsUpgradeDTO createGeneralsUpgrade(GeneralsUpgradeDTO request);

    /**
     * 更新指定ID的武将升级记录
     * @param id 武将升级记录ID
     * @param request 更新请求的DTO对象
     * @return 更新后的武将升级DTO对象
     */
    GeneralsUpgradeDTO updateGeneralsUpgrade(Long id, GeneralsUpgradeDTO request);

    /**
     * 删除指定ID的武将升级记录
     * @param id 武将升级记录ID
     */
    void deleteGeneralsUpgrade(Long id);
}
