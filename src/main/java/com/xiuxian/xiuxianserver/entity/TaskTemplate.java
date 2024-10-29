package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * TaskTemplate 实体类
 * 用于存储任务模板的基本信息、目标、奖励以及前置条件
 * 支持生成 Excel 文件。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "task_template")
@Schema(description = "任务模板实体类，存储任务的基础信息、目标、奖励和前置条件")
@ExcelField // 表示此类支持生成Excel
public class TaskTemplate {

    @Id
    @Column(nullable = false, updatable = false)
    @Schema(description = "任务的唯一标识符", example = "1001")
    @ExcelColumn(headerName = "任务ID", comment = "任务的唯一标识符")
    private Long id; // 任务唯一ID

    @Column(nullable = false, length = 255)
    @Schema(description = "任务名称", example = "Hero's Journey")
    @ExcelColumn(headerName = "任务名称", comment = "任务的名称")
    private String name; // 任务名称

    @Column(nullable = false, length = 500)
    @Schema(description = "任务描述", example = "Complete the journey to become a hero.")
    @ExcelColumn(headerName = "任务描述", comment = "任务的详细描述")
    private String description; // 任务描述

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @Schema(description = "任务类型", example = "COLLECT")
    @ExcelColumn(headerName = "任务类型", comment = "任务的类型")
    private TaskType type; // 任务类型

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "任务目标类型", example = "COLLECT_ITEMS")
    @ExcelColumn(headerName = "任务目标类型", comment = "任务目标的类型")
    private TaskGoalType goalType; // 任务目标类型

    @Column(nullable = false)
    @Schema(description = "任务目标值", example = "100")
    @ExcelColumn(headerName = "任务目标值", comment = "完成任务所需的目标值")
    private int goalValue; // 任务目标值

    @ElementCollection
    @Schema(description = "任务奖励列表，包括奖励类型和对应值")
    @ExcelColumn(headerName = "任务奖励", comment = "任务完成后获得的奖励")
    private List<TaskReward> rewards; // 任务奖励列表

    @Column(nullable = true)
    @Schema(description = "前置任务ID，如果存在需要先完成的任务", example = "1000")
    @ExcelColumn(headerName = "前置任务ID", comment = "需要先完成的前置任务ID")
    private Long preTaskId; // 前置任务ID

    @Column(nullable = true)
    @Schema(description = "前置玩家等级，玩家需达到该等级才能解锁任务", example = "10")
    @ExcelColumn(headerName = "前置玩家等级", comment = "玩家需达到的最低等级")
    private Integer requiredPlayerLevel; // 前置玩家等级

    @Enumerated(EnumType.STRING)
    @Schema(description = "任务状态", example = "NOT_STARTED")
    @ExcelColumn(headerName = "任务状态", comment = "当前任务的状态")
    private TaskStatus status; // 任务状态

    @Column(nullable = false)
    @Schema(description = "是否激活下一个任务", example = "true")
    @ExcelColumn(headerName = "是否激活下一个任务", comment = "完成后是否激活下一个任务")
    private boolean triggerNextTask; // 是否激活下一个任务

    // 枚举类型定义
    public enum TaskType {
        COLLECT, DEFEAT_ENEMY, UPGRADE, EXPLORE
    }

    public enum TaskGoalType {
        COLLECT_ITEMS, DEFEAT_ENEMIES, GATHER_RESOURCES, REACH_LEVEL
    }

    public enum RewardType {
        CURRENCY, RESOURCE, ITEM
    }

    public enum TaskStatus {
        NOT_STARTED, IN_PROGRESS, COMPLETED
    }

    // 奖励类定义
    @Embeddable
    @ExcelField // 表示此类支持生成Excel
    public static class Reward {
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        @Schema(description = "奖励类型", example = "CURRENCY")
        @ExcelColumn(headerName = "奖励类型", comment = "任务奖励的类型")
        private RewardType type; // 奖励类型

        @Column(nullable = false)
        @Schema(description = "奖励的数值", example = "1000")
        @ExcelColumn(headerName = "奖励值", comment = "任务奖励的数值")
        private int value; // 奖励值

        // getter for type
        public RewardType getType() {
            return type;
        }

        // getter for value
        public int getValue() {
            return value;
        }
    }
}
