package com.xiuxian.xiuxianserver.manager;

import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResourceManager {

    private final CharacterProfileService characterProfileService;

    /**
     * 校验角色是否拥有足够的资源
     *
     * @param characterId 角色ID
     * @param resourceType 资源类型
     * @param requiredAmount 所需资源数量
     * @return true 如果资源充足，否则返回 false
     */
    public boolean hasSufficientResources(Long characterId, String resourceType, int requiredAmount) {
        return characterProfileService.hasSufficientResource(characterId, resourceType, requiredAmount);
    }

    /**
     * 扣除角色资源
     *
     * @param characterId 角色ID
     * @param resourceType 资源类型
     * @param amount 扣除数量
     */
    public void deductResources(Long characterId, String resourceType, int amount) {
        characterProfileService.deductResource(characterId, resourceType, amount);
    }

    /**
     * 增加角色的资源
     *
     * @param characterId 角色ID
     * @param resourcesToAdd 增加资源的键值对
     */
    public void addResource(Long characterId, Map<String, Integer> resourcesToAdd) {
        resourcesToAdd.forEach((type, amount) -> {
            characterProfileService.addResource(characterId, type, amount);
        });
    }
}
