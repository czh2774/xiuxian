package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.GeneralsUpgradeDTO;
import com.xiuxian.xiuxianserver.dto.GeneralsUpgradeUpdateRequestDTO;
import com.xiuxian.xiuxianserver.dto.IdRequestDTO;
import com.xiuxian.xiuxianserver.service.GeneralsUpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GeneralsUpgradeController类，处理武将升级的API请求。
 * 所有操作均使用POST请求。
 */
@RestController
@RequestMapping("/generals/upgrades")
public class GeneralsUpgradeController {

    @Autowired
    private GeneralsUpgradeService generalsUpgradeService;

    /**
     * 根据ID获取武将升级记录
     * @param request 请求体，包含ID
     * @return 武将升级DTO对象
     */
    @PostMapping("/getById")
    public ResponseEntity<GeneralsUpgradeDTO> getGeneralsUpgradeById(@RequestBody IdRequestDTO request) {
        GeneralsUpgradeDTO upgrade = generalsUpgradeService.getGeneralsUpgradeById(request.getId());
        return ResponseEntity.ok(upgrade);
    }

    /**
     * 获取所有武将升级记录
     * @return 所有武将升级DTO对象列表
     */
    @PostMapping("/getAll")
    public ResponseEntity<List<GeneralsUpgradeDTO>> getAllGeneralsUpgrades() {
        List<GeneralsUpgradeDTO> upgrades = generalsUpgradeService.getAllGeneralsUpgrades();
        return ResponseEntity.ok(upgrades);
    }

    /**
     * 创建新的武将升级记录
     * @param request 创建武将升级记录的DTO请求体
     * @return 创建后的武将升级DTO对象
     */
    @PostMapping("/create")
    public ResponseEntity<GeneralsUpgradeDTO> createGeneralsUpgrade(@RequestBody GeneralsUpgradeDTO request) {
        GeneralsUpgradeDTO upgrade = generalsUpgradeService.createGeneralsUpgrade(request);
        return ResponseEntity.ok(upgrade);
    }

    /**
     * 更新指定ID的武将升级记录
     * @param request 更新武将升级记录的DTO请求体，包含ID和更新内容
     * @return 更新后的武将升级DTO对象
     */
    @PostMapping("/update")
    public ResponseEntity<GeneralsUpgradeDTO> updateGeneralsUpgrade(@RequestBody GeneralsUpgradeUpdateRequestDTO request) {
        GeneralsUpgradeDTO upgrade = generalsUpgradeService.updateGeneralsUpgrade(request.getId(), request);
        return ResponseEntity.ok(upgrade);
    }

    /**
     * 删除指定ID的武将升级记录
     * @param request 请求体，包含ID
     * @return 空的响应体，状态为OK
     */
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteGeneralsUpgrade(@RequestBody IdRequestDTO request) {
        generalsUpgradeService.deleteGeneralsUpgrade(request.getId());
        return ResponseEntity.ok().build();
    }
}
