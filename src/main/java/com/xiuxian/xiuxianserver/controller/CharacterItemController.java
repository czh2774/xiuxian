package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.CharacterItemDTO;
import com.xiuxian.xiuxianserver.dto.IdRequestDTO;
import com.xiuxian.xiuxianserver.service.CharacterItemService;
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
 * CharacterItemController类，处理角色道具相关的API请求。
 * 提供获取角色道具、单个道具详情接口。
 */
@RestController
@RequestMapping("/character/item")
public class CharacterItemController {

    private static final Logger logger = LoggerFactory.getLogger(CharacterItemController.class);

    private final CharacterItemService characterItemService;

    public CharacterItemController(CharacterItemService characterItemService) {
        this.characterItemService = characterItemService;
    }

    /**
     * 获取角色的所有道具信息
     *
     * @param characterIdRequest 角色ID请求体
     * @return 角色道具列表的响应
     */
    @Operation(summary = "获取角色的所有道具信息", description = "根据角色ID获取所有道具信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功返回角色道具信息"),
            @ApiResponse(responseCode = "404", description = "角色道具信息未找到"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/list")
    public ResponseEntity<CustomApiResponse<List<CharacterItemDTO>>> getCharacterItemsByCharacterId(@RequestBody IdRequestDTO characterIdRequest, HttpServletRequest request) {
        Long characterId = characterIdRequest.getId();
        logger.info("获取角色道具信息，角色ID: {}", characterId);

        List<CharacterItemDTO> items;
        try {
            items = characterItemService.getCharacterItemsByCharacterId(characterId);
            logger.info("成功获取角色道具信息，角色ID: {}, 道具数量: {}", characterId, items.size());
        } catch (Exception e) {
            logger.error("获取角色道具信息时发生错误，角色ID: {}, 错误: {}", characterId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取角色道具信息失败", request.getRequestURI()));
        }

        return ResponseEntity.ok(CustomApiResponse.success("成功获取角色道具信息", items, request.getRequestURI()));
    }

    /**
     * 获取指定道具的详细信息
     *
     * @param idRequestDTO 道具实例ID请求体
     * @return 指定道具的详细信息响应
     */
    @Operation(summary = "获取指定道具详细信息", description = "根据道具实例ID获取指定道具的详细信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功返回道具详细信息"),
            @ApiResponse(responseCode = "404", description = "道具信息未找到"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/getById")
    public ResponseEntity<CustomApiResponse<CharacterItemDTO>> getCharacterItemById(@RequestBody IdRequestDTO idRequestDTO, HttpServletRequest request) {
        Long itemInstanceId = idRequestDTO.getId();
        logger.info("获取道具详细信息，道具ID: {}", itemInstanceId);

        CharacterItemDTO item;
        try {
            item = characterItemService.getCharacterItemById(itemInstanceId);
            logger.info("成功获取道具详细信息，道具ID: {}", itemInstanceId);
        } catch (Exception e) {
            logger.error("获取道具信息时发生错误，道具ID: {}, 错误: {}", itemInstanceId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CustomApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取道具详细信息失败", request.getRequestURI()));
        }

        return ResponseEntity.ok(CustomApiResponse.success("成功获取道具详细信息", item, request.getRequestURI()));
    }
}
