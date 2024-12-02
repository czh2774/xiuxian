package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.enums.BuildingStatusType;
import com.xiuxian.xiuxianserver.enums.FeaturePromptType;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "character_building")
@Schema(description = "角色建筑实例表，存储角色建造的建筑信息")
public class CharacterBuilding {

    @Id
    @Schema(description = "角色建筑实例的唯一标识符")
    private Long id;  // 建筑实例ID

    @Schema(description = "角色ID，表示建筑属于哪个角色")
    private Long characterId;  // 角色ID

    @Schema(description = "建筑模板ID，表示建筑的类型")
    private Long buildingTemplateId;  // 建筑模板ID

    @Schema(description = "建筑位置ID，表示建筑所在的位置")
    private Long locationId;  // 位置ID

    @Schema(description = "建筑当前的等级")
    private int currentLevel = 1;  // 建筑等级，默认为1

    @Schema(description = "建筑的状态，使用枚举类表示：空闲中、研究中、升级中等")
    @Enumerated(EnumType.STRING)
    private BuildingStatusType buildingStatus = BuildingStatusType.IDLE;  // 建筑状态

    @Schema(description = "操作开始时间，记录当前操作的开始时间")
    private LocalDateTime actionStartTime;  // 操作开始时间

    @Schema(description = "操作的总持续时间（秒），用于计算操作的完成时间")
    private int actionTotalDuration;  // 操作总计时间

    @Schema(description = "事务ID，用于标识当前的事务")
    private Long transactionId;  // 事务ID

    @Schema(description = "功能提示类型，使用枚举来表示当前功能提示")
    @Enumerated(EnumType.STRING)
    private FeaturePromptType featurePrompt;  // 功能提示

    @Schema(description = "是否有待领取的奖励")
    private boolean rewardPending = false;  // 是否有待领取奖励

    @Schema(description = "增长的类型：兵力、科技、突破、道具等")
    private String growthType;  // 增长类型

    @Schema(description = "增长的实例ID，如科技ID、道具ID等")
    private Long growthId;  // 增长实例ID

    @Schema(description = "增长的数值或等级，如兵力数量、科技等级等")
    private int growthValue;  // 增长数值或等级

    @Schema(description = "最后修改时间")
    private LocalDateTime lastModifiedTime;  // 最后修改时间
}
