package com.xiuxian.xiuxianserver.controller;

import com.xiuxian.xiuxianserver.dto.CharacterGeneralDTO;
import com.xiuxian.xiuxianserver.dto.IdRequestDTO;
import com.xiuxian.xiuxianserver.service.CharacterGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CharacterGeneralController类，处理角色武将的API请求。
 * 所有操作均使用POST请求。
 */
@RestController
@RequestMapping("/character/general")
public class CharacterGeneralController {

    @Autowired
    private CharacterGeneralService characterGeneralService;

    /**
     * 根据ID获取角色武将实例
     * @param request 请求体，包含ID
     * @return 角色武将DTO对象
     */
    @PostMapping("/getById")
    public ResponseEntity<CharacterGeneralDTO> getCharacterGeneralById(@RequestBody IdRequestDTO request) {
        CharacterGeneralDTO general = characterGeneralService.getCharacterGeneralById(request.getId());
        return ResponseEntity.ok(general);
    }

    /**
     * 获取指定角色的所有武将实例
     * @param request 请求体，包含角色ID
     * @return 角色的所有武将实例DTO对象列表
     */
    @PostMapping("/getByCharacterId")
    public ResponseEntity<List<CharacterGeneralDTO>> getCharacterGeneralsByCharacterId(@RequestBody IdRequestDTO request) {
        List<CharacterGeneralDTO> generals = characterGeneralService.getCharacterGeneralsByCharacterId(request.getId());
        return ResponseEntity.ok(generals);
    }

    /**
     * 创建角色武将实例
     * @param request 创建武将实例的DTO请求体
     * @return 创建后的角色武将DTO对象
     */
    @PostMapping("/create")
    public ResponseEntity<CharacterGeneralDTO> createCharacterGeneral(@RequestBody CharacterGeneralDTO request) {
        CharacterGeneralDTO general = characterGeneralService.createCharacterGeneral(request);
        return ResponseEntity.ok(general);
    }

    /**
     * 更新指定ID的角色武将实例
     * @param request 更新武将实例的DTO请求体，包含ID和更新内容
     * @return 更新后的角色武将DTO对象
     */
    @PostMapping("/update")
    public ResponseEntity<CharacterGeneralDTO> updateCharacterGeneral(@RequestBody CharacterGeneralDTO request) {
        CharacterGeneralDTO general = characterGeneralService.updateCharacterGeneral(request.getId(), request);
        return ResponseEntity.ok(general);
    }

    /**
     * 删除指定ID的角色武将实例
     * @param request 请求体，包含ID
     * @return 空的响应体，状态为OK
     */
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteCharacterGeneral(@RequestBody IdRequestDTO request) {
        characterGeneralService.deleteCharacterGeneral(request.getId());
        return ResponseEntity.ok().build();
    }
}
