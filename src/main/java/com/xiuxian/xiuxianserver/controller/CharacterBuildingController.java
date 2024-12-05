package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.BuildingStatusUpdateDTO;
import com.xiuxian.xiuxianserver.service.CharacterBuildingService;
import com.xiuxian.xiuxianserver.manager.CharacterBuildingManager;
import com.xiuxian.xiuxianserver.mapper.CharacterBuildingMapper;
import com.xiuxian.xiuxianserver.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

import com.xiuxian.xiuxianserver.entity.CharacterBuilding;
import com.xiuxian.xiuxianserver.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import com.xiuxian.xiuxianserver.dto.BuildingUpgradeResponse;
import com.xiuxian.xiuxianserver.dto.response.MainCityUpgradeResponse;

/**
 * CharacterBuildingController
 * 处理与角色建筑相关的API请求
 */
@Slf4j
@RestController
@RequestMapping("/character/building")
public class CharacterBuildingController {

    private static final Logger logger = LoggerFactory.getLogger(CharacterBuildingController.class);

    private final CharacterBuildingService characterBuildingService;
    private final CharacterBuildingManager buildingManager;
    private final CharacterBuildingMapper buildingMapper;

    public CharacterBuildingController(
            CharacterBuildingService characterBuildingService,
            CharacterBuildingManager buildingManager,
            CharacterBuildingMapper buildingMapper) {
        this.characterBuildingService = characterBuildingService;
        this.buildingManager = buildingManager;
        this.buildingMapper = buildingMapper;
    }

    /**
     * 获取角色的所有建筑信息
     *
     * @param requestBody 请求体，包含角色ID
     * @return 角色建筑列表的响应
     */
    @Operation(summary = "获取角色的所有建筑信息", description = "根据角��ID获取所有建筑信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功返回角色建筑信息"),
            @ApiResponse(responseCode = "404", description = "色建筑信息未找到"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/list")
    public ResponseEntity<CustomApiResponse<List<CharacterBuildingDTO>>> getAllCharacterBuildings(
            @RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        logger.info("收到获取角色建筑信息的请求：{}", requestBody);

        Long characterId;
        try {
            // 提取 characterId 并进行类型转换
            if (requestBody.containsKey("characterId")) {
                characterId = Long.valueOf(requestBody.get("characterId").toString());
            } else {
                throw new IllegalArgumentException("请求体缺少 'characterId' 字段");
            }
        } catch (Exception e) {
            logger.error("解析请求体失败：{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    CustomApiResponse.error(HttpStatus.BAD_REQUEST.value(), "无效的请求参数", request.getRequestURI()));
        }

        List<CharacterBuildingDTO> buildings;
        try {
            // 调用服务层获取建筑信息
            buildings = characterBuildingService.getCharacterBuildingsByCharacterId(characterId);
            logger.info("成功获取角色建筑息，角色ID: {}", characterId);
        } catch (Exception e) {
            logger.error("获取角色建筑信息时发生错误，角色ID: {}, 错误: {}", characterId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取角色建筑信息失败", request.getRequestURI()));
        }

        // 返回成功响应
        return ResponseEntity.ok(CustomApiResponse.success("成功获取角色建筑信息", buildings, request.getRequestURI()));
    }


    /**
     * 创建角色的建筑实例
     *
     * @param createRequestDTO 角色建筑创建请求体
     * @return 创建的角色建筑的响应
     */
    @Operation(summary = "创建角色建筑实例", description = "根据请求体创建角色的建筑实例")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功创建角色建筑"),
            @ApiResponse(responseCode = "400", description = "请求体错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse<CharacterBuildingDTO>> createCharacterBuilding(
            @RequestBody CharacterBuildingCreateRequestDTO createRequestDTO, 
            HttpServletRequest request) {
        logger.info("创建角色建筑实例，角色ID: {}, 建筑模板ID: {}", 
                   createRequestDTO.getCharacterId(), createRequestDTO.getBuildingTemplateId());

        try {
            CharacterBuilding building = buildingManager.createBuilding(
                createRequestDTO.getCharacterId(),
                createRequestDTO.getBuildingTemplateId(),
                createRequestDTO.getLocationId()
            );
            return ResponseEntity.ok(CustomApiResponse.success(
                "成功创建角色建筑", 
                buildingMapper.toDTO(building), 
                request.getRequestURI()
            ));
        } catch (Exception e) {
            logger.error("创建角色建筑实例时发生错误，错误: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                            "创建角色建筑失败", request.getRequestURI()));
        }
    }

    /**
     * 更新角色建筑状态
     *
     * @param updateDTO 状态更新请求体
     * @return 更新结果的响应
     */
    @Operation(summary = "更新角色建筑状态", description = "开始建筑升级")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "成功开始建筑升级"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "建筑不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/update-status")
    public ResponseEntity<CustomApiResponse<CharacterBuildingDTO>> updateCharacterBuildingStatus(
            @RequestBody BuildingStatusUpdateDTO updateDTO, 
            HttpServletRequest request) {
        try {
            // 1. 调用管理器处理升级
            CharacterBuilding building = buildingManager.startUpgradeBuilding(
                updateDTO.getBuildingId()
            );
            
            // 2. 转换并返回结果
            CharacterBuildingDTO dto = buildingMapper.toDTO(building);
            return ResponseEntity.ok(CustomApiResponse.success(
                "成功开始建筑升级", 
                dto, 
                request.getRequestURI()
            ));
            
        } catch (BusinessException e) {
            log.warn("建筑升级业务异常: {}", e.getMessage());
            return ResponseEntity.badRequest()
                .body(CustomApiResponse.error(400, e.getMessage(), request.getRequestURI()));
        } catch (Exception e) {
            log.error("建筑升级系统异常: ", e);
            return ResponseEntity.status(500)
                .body(CustomApiResponse.error(500, "系统错误", request.getRequestURI()));
        }
    }

    @PostMapping("/upgrade")
    @Operation(
        summary = "升级建筑", 
        description = "开始建筑升级，服务端会自动分配升级队列"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "成功开始建筑升级",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BuildingUpgradeResponse.class))
        ),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "建筑不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public ResponseEntity<CustomApiResponse<CharacterBuildingDTO>> upgradeBuilding(
            @Valid @RequestBody 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "建筑升级请求",
                required = true,
                content = @Content(schema = @Schema(implementation = BuildingStatusUpdateDTO.class))
            )
            BuildingStatusUpdateDTO updateDTO, 
            HttpServletRequest request) {
        try {
            CharacterBuilding building = buildingManager.startUpgradeBuilding(
                updateDTO.getBuildingId()
            );
            
            CharacterBuildingDTO dto = buildingMapper.toDTO(building);
            return ResponseEntity.ok(CustomApiResponse.success(
                "成功开始建筑升级", 
                dto, 
                request.getRequestURI()
            ));
            
        } catch (BusinessException e) {
            log.warn("建筑升级业务异常: {}", e.getMessage());
            return ResponseEntity.badRequest()
                .body(CustomApiResponse.error(400, e.getMessage(), request.getRequestURI()));
        } catch (Exception e) {
            log.error("建筑升级系统异常: ", e);
            return ResponseEntity.status(500)
                .body(CustomApiResponse.error(500, "系统错误", request.getRequestURI()));
        }
    }

    @PutMapping("/{buildingId}/complete-upgrade")
    public ResponseEntity<MainCityUpgradeResponse> completeUpgrade(
            @PathVariable Long buildingId) {
        MainCityUpgradeResponse response = buildingManager.completeUpgrade(buildingId);
        return ResponseEntity.ok(response);
    }
}
