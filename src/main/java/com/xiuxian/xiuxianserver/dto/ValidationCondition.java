package com.xiuxian.xiuxianserver.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ValidationCondition {
    private Map<String, Integer> resources; // 资源类型 -> 数量
    private Map<Long, Integer> items;      // 道具ID -> 数量
    private Map<String, Integer> attributes; // 属性名称 -> 值
}
