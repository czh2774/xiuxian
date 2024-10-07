package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.CustomApiResponse;
import com.xiuxian.xiuxianserver.entity.Faction;
import com.xiuxian.xiuxianserver.exception.ResourceNotFoundException; // 引入自定义异常
import com.xiuxian.xiuxianserver.service.FactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * FactionController 用于处理与势力相关的 API 请求。
 */
@RestController
@RequestMapping("/api/factions")
public class FactionController {

    private final FactionService factionService;

    @Autowired
    public FactionController(FactionService factionService) {
        this.factionService = factionService;
    }

    /**
     * 获取所有势力的列表。
     *
     * @return 包含势力列表的统一响应
     */
    @Operation(summary = "获取所有势力", description = "返回所有势力的列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取势力列表"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/getAll")
    public CustomApiResponse<List<Faction>> getAllFactions() {
        List<Faction> factions = factionService.getAllFactions();
        return new CustomApiResponse<>(200, "成功", factions);
    }

    /**
     * 根据ID获取特定势力。
     *
     * @param factionRequest 请求体包含势力ID
     * @return 包含势力详情的统一响应
     * @throws ResourceNotFoundException 如果未找到势力
     */
    @Operation(summary = "根据ID获取特定势力", description = "根据势力ID返回特定势力的信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取势力"),
            @ApiResponse(responseCode = "404", description = "未找到势力"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/getById")
    public CustomApiResponse<Faction> getFactionById(@RequestBody FactionRequest factionRequest) {
        Long id = factionRequest.getFactionId();
        Optional<Faction> faction = factionService.getFactionById(id);
        return faction.map(value -> new CustomApiResponse<>(200, "成功", value))
                .orElseThrow(() -> new ResourceNotFoundException("势力未找到，ID: " + id));
    }

    // 请求体类，用于传递参数
    public static class FactionRequest {
        @Schema(description = "势力 ID", example = "1") // 使用 OpenAPI 注解描述参数
        private Long factionId;

        // Getter 和 Setter
        public Long getFactionId() {
            return factionId;
        }

        public void setFactionId(Long factionId) {
            this.factionId = factionId;
        }
    }
}
