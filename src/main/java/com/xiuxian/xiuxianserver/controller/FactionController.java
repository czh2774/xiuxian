package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.entity.Faction;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException;
import com.xiuxian.xiuxianserver.service.FactionService;
import com.xiuxian.xiuxianserver.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FactionController
 * 处理与势力相关的请求。
 */
@RestController
@RequestMapping("/factions")
public class FactionController {

    private final FactionService factionService;

    @Autowired
    public FactionController(FactionService factionService) {
        this.factionService = factionService;
    }

    /**
     * 获取所有势力的列表。
     *
     * @return 包含势力列表的响应
     */
    @PostMapping("/list")
    @Operation(summary = "获取所有势力", description = "返回所有势力的列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取势力列表"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public ResponseEntity<CustomApiResponse<List<Faction>>> getAllFactions() {
        List<Faction> factions = factionService.getAllFactions();
        return ResponseEntity.ok(new CustomApiResponse<>(HttpStatus.OK.value(), "获取势力列表成功", factions, "/factions/list"));
    }

    /**
     * 根据ID获取特定势力。
     *
     * @param factionRequest 请求体包含势力ID
     * @return 包含势力详情的响应
     */
    @PostMapping("/getById")
    @Operation(summary = "根据ID获取特定势力", description = "根据势力ID返回特定势力的信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取势力"),
            @ApiResponse(responseCode = "404", description = "未找到势力"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public ResponseEntity<CustomApiResponse<Faction>> getFactionById(@RequestBody FactionRequest factionRequest) {
        Long id = factionRequest.getFactionId();
        Faction faction = factionService.getFactionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("势力未找到，ID: " + id));

        return ResponseEntity.ok(new CustomApiResponse<>(HttpStatus.OK.value(), "获取势力详情成功", faction, "/factions/getById"));
    }

    // 请求体类，用于传递参数
    public static class FactionRequest {
        private Long factionId;

        public Long getFactionId() { return factionId; }
        public void setFactionId(Long factionId) { this.factionId = factionId; }
    }
}
