package com.xiuxian.xiuxianserver.enums;

public enum WebSocketMessageType {
    CONNECTED("已连接"),
    PING("心跳检测"),
    PONG("心跳响应"),
    ERROR("错误"),
    
    COOLDOWN_START("冷却开始"),
    COOLDOWN_PROGRESS("冷却进度"),
    COOLDOWN_COMPLETE("冷却完成"),
    COOLDOWN_ACCELERATE("冷却加速"),
    COOLDOWN_CANCEL("冷却取消"),
    COOLDOWN_SYNC("冷却同步"),
    
    RESOURCE_CHANGE("资源变更"),
    
    BUILDING_UPDATE("建筑更新"),
    
    TASK_COMPLETE("任务完成"),
    TASK_PROGRESS("任务进度"),
    TASK_SYNC("任务同步");

    private final String description;

    WebSocketMessageType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 