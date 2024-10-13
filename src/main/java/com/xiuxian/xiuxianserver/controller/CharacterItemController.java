package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.enums.ItemCategory;
import com.xiuxian.xiuxianserver.service.CharacterItemService;
import com.xiuxian.xiuxianserver.service.impl.CharacterItemServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CharacterItemController
 * 处理与玩家道具相关的请求，并将其转发到服务层进行处理。
 */
@RestController
@RequestMapping("/items")
public class CharacterItemController {

    private final CharacterItemService characterItemService;
    private static final Logger logger = LoggerFactory.getLogger(CharacterItemController.class);

    public CharacterItemController(CharacterItemServiceImpl characterItemService) {
        this.characterItemService = characterItemService;
    }

    /**
     * 根据玩家ID查询该玩家拥有的所有道具及其数量。
     *
     * @param characterId 玩家ID
     * @return 玩家道具及其数量的列表
     */
    @PostMapping("/all")
    @Operation(summary = "根据玩家ID查询所有道具", description = "通过玩家ID查询玩家拥有的所有道具及其数量。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功"),
            @ApiResponse(responseCode = "404", description = "未找到玩家或道具")
    })
    public List<Object[]> getAllItemsByCharacterId(@RequestParam String characterId) {
        try {
            logger.info("查询玩家 {} 拥有的所有道具", characterId);
            return characterItemService.getAllItemsByCharacterId(characterId);
        } catch (Exception e) {
            logger.error("获取玩家 {} 的道具时出现错误: {}", characterId, e.getMessage());
            throw new RuntimeException("未能获取玩家道具信息");
        }
    }

    /**
     * 根据玩家ID和道具类型查询该类型对应的道具及其数量。
     *
     * @param characterId 玩家ID
     * @param itemCategory 道具类型
     * @return 符合条件的道具及其数量的列表
     */
    @PostMapping("/by-type")
    @Operation(summary = "根据玩家ID和道具类型查询道具", description = "通过玩家ID和道具类型查询该类型的所有道具及其数量。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功"),
            @ApiResponse(responseCode = "404", description = "未找到符合条件的道具")
    })
    public List<Object[]> getItemsByTypeAndCharacterId(@RequestParam String characterId, @RequestParam ItemCategory itemCategory) {
        try {
            logger.info("查询玩家 {} 的 {} 类型的道具", characterId, itemCategory);
            return characterItemService.findItemsByItemCategoryAndCharacterId(characterId, itemCategory);
        } catch (Exception e) {
            logger.error("查询玩家 {} 的 {} 类型的道具时出现错误: {}", characterId, itemCategory, e.getMessage());
            throw new RuntimeException("未能获取指定类型的玩家道具信息");
        }
    }


}
