package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.CooldownDTO;
import com.xiuxian.xiuxianserver.service.CooldownService;
import com.xiuxian.xiuxianserver.manager.CooldownManager;
import com.xiuxian.xiuxianserver.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import com.xiuxian.xiuxianserver.dto.CooldownCheckRequest;
import com.xiuxian.xiuxianserver.enums.CooldownType;
import com.xiuxian.xiuxianserver.dto.cooldown.CooldownAccelerateRequest;

/**
 * 冷却时间管理控制器
 * 提供与冷却时间相关的API接口
 */
@RestController
@RequestMapping("/cooldown")
public class CooldownController {

    private static final Logger logger = LoggerFactory.getLogger(CooldownController.class);

    private final CooldownService cooldownService;
    private final CooldownManager cooldownManager;

    public CooldownController(CooldownService cooldownService, CooldownManager cooldownManager) {
        this.cooldownService = cooldownService;
        this.cooldownManager = cooldownManager;
    }

    /**
     * 启动冷却
     *
     * @param requestBody 请求体，包含角色ID、类型、目标ID、持续时间和队列ID
     * @return 启动冷却的详细信息
     */
    @Operation(summary = "启动冷却", description = "为指定角色和目标启动冷却任务")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功启动冷却"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/start")
    public ResponseEntity<CustomApiResponse<CooldownDTO>> startCooldown(
            @RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        logger.info("收到启动冷却的请求：{}", requestBody);

        try {
            Long characterId = Long.valueOf(requestBody.get("characterId").toString());
            String typeStr = requestBody.get("type").toString();
            CooldownType type = CooldownType.valueOf(typeStr);
            Long targetId = Long.valueOf(requestBody.get("targetId").toString());
            int durationInSeconds = Integer.parseInt(requestBody.get("durationInSeconds").toString());
            int queueId = Integer.parseInt(requestBody.get("queueId").toString());

            CooldownDTO cooldown = cooldownService.startCooldown(characterId, type, targetId, durationInSeconds, queueId);
            logger.info("成功启动冷却：角色ID={}, 类型={}, 目标ID={}", characterId, type, targetId);
            return ResponseEntity.ok(CustomApiResponse.success("冷却启动成功", cooldown, request.getRequestURI()));
        } catch (Exception e) {
            logger.error("启动冷却失败：{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "启动冷却失败", request.getRequestURI()));
        }
    }

    /**
     * 查询冷却状态
     *
     * @param requestBody 请求体，包含角色ID、类型、目标ID和队列ID
     * @return 冷却状态详情
     */
    @Operation(summary = "查询冷却状态", description = "获取指定冷却的状态")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功返回冷却状态"),
            @ApiResponse(responseCode = "404", description = "未找到冷却记录"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/status")
    public ResponseEntity<CustomApiResponse<CooldownDTO>> getCooldownStatus(
            @RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        logger.info("收到查询冷却状态的请求：{}", requestBody);

        try {
            Long characterId = Long.valueOf(requestBody.get("characterId").toString());
            String typeStr = requestBody.get("type").toString();
            CooldownType type = CooldownType.valueOf(typeStr);
            Long targetId = Long.valueOf(requestBody.get("targetId").toString());
            int queueId = Integer.parseInt(requestBody.get("queueId").toString());

            CooldownDTO cooldown = cooldownService.getCooldownStatus(characterId, type, targetId, queueId);
            logger.info("成功查询冷却状态：角色ID={}, 类型={}, 目标ID={}, 队列ID={}", characterId, type, targetId, queueId);
            return ResponseEntity.ok(CustomApiResponse.success("冷却状态获取成功", cooldown, request.getRequestURI()));
        } catch (Exception e) {
            logger.error("查询冷却状态失败：{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "查询冷却状态失败", request.getRequestURI()));
        }
    }

    /**
     * 加速冷却
     *
     * @param request 请求体，包含角色ID、类型、目标ID、加速时间和队列ID
     * @return 加速结果
     */
    @Operation(summary = "加速冷却", description = "通过道具或货币加速冷却时间")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功加速冷却"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/accelerate")
    public ResponseEntity<CustomApiResponse<Void>> accelerateCooldown(
            @Valid @RequestBody CooldownAccelerateRequest request, HttpServletRequest httpRequest) {
        try {
            boolean success = cooldownManager.accelerateCooldown(
                request.getCharacterId(),
                request.getType(),
                request.getTargetId(),
                request.getItemType(),
                request.getItemCount(),
                request.getQueueId()
            );
            
            if (success) {
                logger.info("成功加速冷却：角色ID={}, 类型={}, 目标ID={}, 队列ID={}", 
                    request.getCharacterId(), request.getType(), request.getTargetId(), request.getQueueId());
                return ResponseEntity.ok(CustomApiResponse.success("冷却加速成功", null, httpRequest.getRequestURI()));
            } else {
                return ResponseEntity.badRequest().body(
                    CustomApiResponse.error(400, "加速冷却失败", httpRequest.getRequestURI()));
            }
        } catch (Exception e) {
            logger.error("加速冷却失败：{}", e.getMessage());
            return ResponseEntity.internalServerError().body(
                CustomApiResponse.error(500, "加速冷却失败: " + e.getMessage(), httpRequest.getRequestURI()));
        }
    }

    /**
     * 手动完成冷却
     *
     * @param requestBody 请求体，包含角色ID、类型、目标ID和队列ID
     * @return 完成结果
     */
    @Operation(summary = "手动完成冷却", description = "立即完成指定冷却时间")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功完成冷却"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/complete")
    public ResponseEntity<CustomApiResponse<Void>> completeCooldown(
            @RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        logger.info("收到手动完成冷却的请求：{}", requestBody);

        try {
            Long characterId = Long.valueOf(requestBody.get("characterId").toString());
            String typeStr = requestBody.get("type").toString();
            CooldownType type = CooldownType.valueOf(typeStr);
            Long targetId = Long.valueOf(requestBody.get("targetId").toString());
            int queueId = Integer.parseInt(requestBody.get("queueId").toString());

            cooldownService.completeCooldown(characterId, type, targetId, queueId);
            logger.info("成功完成冷却：角色ID={}, 类型={}, 目标ID={}, 队列ID={}", characterId, type, targetId, queueId);
            return ResponseEntity.ok(CustomApiResponse.success("冷却完成成功", null, request.getRequestURI()));
        } catch (Exception e) {
            logger.error("完成冷却失败：{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "完成冷却失败", request.getRequestURI()));
        }
    }

    // 查询特定类型的冷却状态
    @PostMapping("/check")
    public ResponseEntity<CustomApiResponse<CooldownDTO>> checkCooldown(
            @Valid @RequestBody CooldownCheckRequest request, HttpServletRequest httpRequest) {
        CooldownDTO cooldown = cooldownService.getCooldownStatus(
            request.getCharacterId(),
            request.getCooldownType(),
            request.getTargetId(),
            request.getQueueId()
        );
        return ResponseEntity.ok(CustomApiResponse.success("查询冷却状态成功", cooldown, httpRequest.getRequestURI()));
    }

    // 建议添加一个查询所有冷却的接口
    @PostMapping("/list")
    public ResponseEntity<CustomApiResponse<List<CooldownDTO>>> listCooldowns(
            @RequestParam Long characterId, HttpServletRequest httpRequest) {
        List<CooldownDTO> cooldowns = cooldownService.getCharacterCooldowns(characterId);
        return ResponseEntity.ok(CustomApiResponse.success("查询冷却列表成功", cooldowns, httpRequest.getRequestURI()));
    }
}
