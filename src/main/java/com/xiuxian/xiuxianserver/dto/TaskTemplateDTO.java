package com.xiuxian.xiuxianserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TaskTemplateDTO 数据传输对象
 * 用于在服务层与控制层之间传输任务模板相关的数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "任务模板DTO，用于传输任务模板相关数据")
public class TaskTemplateDTO {

    @Schema(description = "任务ID", example = "1001")
    private Long id; // 任务唯一ID

    @Schema(description = "任务名称", example = "Hero's Journey")
    private String name; // 任务名称

    @Schema(description = "任务描述", example = "Complete the journey to become a hero.")
    private String description; // 任务描述

    @Schema(description = "任务类型", example = "COLLECT")
    private String type; // 任务类型

    @Schema(description = "任务目标类型", example = "COLLECT_ITEMS")
    private String goalType; // 任务目标类型

    @Schema(description = "任务目标值", example = "100")
    private int goalValue; // 任务目标值

    @Schema(description = "任务奖励列表")
    private List<TaskRewardDTO> rewards; // 任务奖励列表

    @Schema(description = "前置任务ID", example = "1000")
    private Long preTaskId; // 前置任务ID

    @Schema(description = "前置玩家等级", example = "10")
    private Integer requiredPlayerLevel; // 前置玩家等级

    @Schema(description = "任务状态", example = "NOT_STARTED")
    private String status; // 任务状态

    @Schema(description = "是否激活下一个任务", example = "true")
    private boolean triggerNextTask; // 是否激活下一个任务

    /**
     * TaskRewardDTO 用于传输任务奖励的类型和对应的值
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "任务奖励DTO")
    public static class TaskRewardDTO {
        @Schema(description = "奖励类型", example = "CURRENCY")
        private String type; // 奖励类型

        @Schema(description = "奖励值", example = "1000")
        private int value; // 奖励值
    }
}
