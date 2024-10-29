package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.CharacterItemDTO;
import com.xiuxian.xiuxianserver.dto.IdRequestDTO;
import com.xiuxian.xiuxianserver.service.CharacterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CharacterItemController类，处理角色道具的API请求。
 * 所有操作均使用POST请求。
 */
@RestController
@RequestMapping("/character/item")
public class CharacterItemController {

    @Autowired
    private CharacterItemService characterItemService;

    /**
     * 根据ID获取角色道具实例
     * @param request 请求体，包含道具实例ID
     * @return 角色道具DTO对象
     */
    @PostMapping("/getById")
    public ResponseEntity<CharacterItemDTO> getCharacterItemById(@RequestBody IdRequestDTO request) {
        CharacterItemDTO item = characterItemService.getCharacterItemById(request.getId());
        return ResponseEntity.ok(item);
    }

    /**
     * 获取指定角色的所有道具实例
     * @param request 请求体，包含角色ID
     * @return 角色的所有道具实例DTO对象列表
     */
    @PostMapping("/getByCharacterId")
    public ResponseEntity<List<CharacterItemDTO>> getCharacterItemsByCharacterId(@RequestBody IdRequestDTO request) {
        List<CharacterItemDTO> items = characterItemService.getCharacterItemsByCharacterId(request.getId());
        return ResponseEntity.ok(items);
    }

    /**
     * 创建角色道具实例
     * @param request 创建道具实例的DTO请求体
     * @return 创建后的角色道具DTO对象
     */
    @PostMapping("/create")
    public ResponseEntity<CharacterItemDTO> createCharacterItem(@RequestBody CharacterItemDTO request) {
        CharacterItemDTO item = characterItemService.createCharacterItem(request);
        return ResponseEntity.ok(item);
    }

    /**
     * 更新指定道具实例的记录
     * @param request 更新道具实例的DTO请求体，包含道具实例ID和更新内容
     * @return 更新后的角色道具DTO对象
     */
    @PostMapping("/update")
    public ResponseEntity<CharacterItemDTO> updateCharacterItem(@RequestBody CharacterItemDTO request) {
        CharacterItemDTO item = characterItemService.updateCharacterItem(request.getItemInstanceId(), request);
        return ResponseEntity.ok(item);
    }

    /**
     * 删除指定ID的角色道具实例
     * @param request 请求体，包含道具实例ID
     * @return 空的响应体，状态为OK
     */
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteCharacterItem(@RequestBody IdRequestDTO request) {
        characterItemService.deleteCharacterItem(request.getId());
        return ResponseEntity.ok().build();
    }
}
