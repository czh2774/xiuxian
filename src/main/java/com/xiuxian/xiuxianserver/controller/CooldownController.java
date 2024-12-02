package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.CooldownDTO;
import com.xiuxian.xiuxianserver.service.CooldownService;
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

import java.util.Map;

/**
 * 冷却时间管理控制器
 * 提供与冷却时间相关的API接口
 */
@RestController
@RequestMapping("/cooldown")
public class CooldownController {

    private static final Logger logger = LoggerFactory.getLogger(CooldownController.class);

    private final CooldownService cooldownService;

    public CooldownController(CooldownService cooldownService) {
        this.cooldownService = cooldownService;
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
            String type = requestBody.get("type").toString();
            Long targetId = Long.valueOf(requestBody.get("targetId").toString());
            int durationInSeconds = Integer.parseInt(requestBody.get("durationInSeconds").toString());
            int queueId = Integer.parseInt(requestBody.get("queueId").toString());

            CooldownDTO cooldown = cooldownService.startCooldown(characterId, type, targetId, durationInSeconds);
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
            String type = requestBody.get("type").toString();
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
     * @param requestBody 请求体，包含角色ID、类型、目标ID、加速时间和队列ID
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
            @RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        logger.info("收到加速冷却的请求：{}", requestBody);

        try {
            Long characterId = Long.valueOf(requestBody.get("characterId").toString());
            String type = requestBody.get("type").toString();
            Long targetId = Long.valueOf(requestBody.get("targetId").toString());
            int accelerationTime = Integer.parseInt(requestBody.get("accelerationTime").toString());
            int queueId = Integer.parseInt(requestBody.get("queueId").toString());

            cooldownService.accelerateCooldown(characterId, type, targetId, accelerationTime, queueId);
            logger.info("成功加速冷却：角色ID={}, 类型={}, 目标ID={}, 队列ID={}", characterId, type, targetId, queueId);
            return ResponseEntity.ok(CustomApiResponse.success("冷却加速成功", null, request.getRequestURI()));
        } catch (Exception e) {
            logger.error("加速冷却失败：{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "加速冷却失败", request.getRequestURI()));
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
            String type = requestBody.get("type").toString();
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
}
