package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.CharacterBuildingDTO;
import com.xiuxian.xiuxianserver.dto.CharacterBuildingCreateRequestDTO;
import com.xiuxian.xiuxianserver.dto.BuildingStatusUpdateDTO;
import com.xiuxian.xiuxianserver.service.CharacterBuildingService;
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

import java.util.List;

/**
 * CharacterBuildingController
 * 处理与角色建筑相关的API请求
 */
@RestController
@RequestMapping("/character/building")
public class CharacterBuildingController {

    private static final Logger logger = LoggerFactory.getLogger(CharacterBuildingController.class);

    private final CharacterBuildingService characterBuildingService;

    public CharacterBuildingController(CharacterBuildingService characterBuildingService) {
        this.characterBuildingService = characterBuildingService;
    }

    /**
     * 获取角色的所有建筑信息
     *
     * @param characterId 角色ID
     * @return 角色建筑列表的响应
     */
    @Operation(summary = "获取角色的所有建筑信息", description = "根据角色ID获取所有建筑信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功返回角色建筑信息"),
            @ApiResponse(responseCode = "404", description = "角色建筑信息未找到"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/list")
    public ResponseEntity<CustomApiResponse<List<CharacterBuildingDTO>>> getAllCharacterBuildings(@RequestBody Long characterId, HttpServletRequest request) {
        logger.info("获取角色建筑信息，角色ID: {}", characterId);

        List<CharacterBuildingDTO> buildings;
        try {
            buildings = characterBuildingService.getCharacterBuildingsByCharacterId(characterId);  // 调用正确的方法
            logger.info("成功获取角色建筑信息，角色ID: {}", characterId);
        } catch (Exception e) {
            logger.error("获取角色建筑信息时发生错误，角色ID: {}, 错误: {}", characterId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取角色建筑信息失败", request.getRequestURI()));
        }

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
    public ResponseEntity<CustomApiResponse<CharacterBuildingDTO>> createCharacterBuilding(@RequestBody CharacterBuildingCreateRequestDTO createRequestDTO, HttpServletRequest request) {
        logger.info("创建角色建筑实例，角色ID: {}, 建筑模板ID: {}", createRequestDTO.getCharacterId(), createRequestDTO.getBuildingTemplateId());

        CharacterBuildingDTO building;
        try {
            building = characterBuildingService.createCharacterBuildingInstance(createRequestDTO);  // 使用正确的方法
            logger.info("成功创建角色建筑实例，角色ID: {}", createRequestDTO.getCharacterId());
        } catch (Exception e) {
            logger.error("创建角色建筑实例时发生错误，错误: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "创建角色建筑失败", request.getRequestURI()));
        }

        return ResponseEntity.ok(CustomApiResponse.success("成功创建角色建筑", building, request.getRequestURI()));
    }

    /**
     * 更新角色建筑状态
     *
     * @param buildingStatusUpdateDTO 状态更新请求体
     * @return 更新结果的响应
     */
    @Operation(summary = "更新角色建筑状态", description = "根据请求体更新角色建筑的状态")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功更新角色建筑状态"),
            @ApiResponse(responseCode = "400", description = "请求体错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/update-status")
    public ResponseEntity<CustomApiResponse<Void>> updateCharacterBuildingStatus(@RequestBody BuildingStatusUpdateDTO buildingStatusUpdateDTO, HttpServletRequest request) {
        logger.info("更新角色建筑状态，建筑ID: {}", buildingStatusUpdateDTO.getBuildingId());

        try {
            // 解包 DTO 中的 buildingId 和 newStatus，调用服务层方法
            characterBuildingService.updateCharacterBuildingStatus(buildingStatusUpdateDTO.getBuildingId(), buildingStatusUpdateDTO.getNewStatus());
            logger.info("成功更新角色建筑状态，建筑ID: {}", buildingStatusUpdateDTO.getBuildingId());
        } catch (Exception e) {
            logger.error("更新角色建筑状态时发生错误，错误: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "更新角色建筑状态失败", request.getRequestURI()));
        }

        return ResponseEntity.ok(CustomApiResponse.success("成功更新角色建筑状态", null, request.getRequestURI()));
    }
}
