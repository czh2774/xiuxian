package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelField;
import com.xiuxian.xiuxianserver.util.ExcelColumn;
import lombok.Data;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 爵位模板表，定义每个爵位的属性、效果和条件。
 * 支持生成 Excel 文件，带有注解。
 */
@ExcelField
@Data
@Entity
@Table(name = "title_template")
@Schema(description = "爵位模板实体")
public class TitleTemplate {

    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BIGINT COMMENT '爵位唯一标识符'")
    @ExcelColumn(headerName = "爵位模板ID", comment = "爵位唯一标识符")
    @Schema(description = "爵位唯一标识符")
    private Long id; // 爵位模板ID

    @Column(name = "name", nullable = false, length = 100, columnDefinition = "VARCHAR(100) COMMENT '爵位的名称'")
    @Schema(description = "爵位名称", example = "大将军")
    @ExcelColumn(headerName = "爵位名称", comment = "爵位的名称")
    private String name; // 爵位名称

    @Column(name = "description", length = 500, columnDefinition = "VARCHAR(500) COMMENT '爵位的详细描述'")
    @Schema(description = "爵位描述", example = "拥有极高的战斗指挥能力，掌握帝国军权")
    @ExcelColumn(headerName = "爵位描述", comment = "爵位的详细描述")
    private String description; // 爵位描述

    @Column(name = "icon_url", length = 255, columnDefinition = "VARCHAR(255) COMMENT '用于客户端展示的爵位图标URL'")
    @Schema(description = "爵位图标URL", example = "http://example.com/icon_general.png")
    @ExcelColumn(headerName = "爵位图标URL", comment = "用于客户端展示的爵位图标URL")
    private String iconUrl; // 爵位图标URL

    @Column(name = "effect", columnDefinition = "JSON COMMENT 'JSON格式存储的爵位特性效果'")
    @Schema(description = "特性效果", example = "{\"resource_bonus\": {\"wood\": 10, \"iron\": 5}, \"military_bonus\": {\"attack\": 5}}")
    @ExcelColumn(headerName = "特性效果", comment = "JSON格式存储的爵位特性效果，如资源加成或军队加成")
    private String effect; // 特性效果（JSON格式）

    @Column(name = "level_requirement", nullable = false, columnDefinition = "INT COMMENT '晋升到该爵位所需的玩家最低等级'")
    @Schema(description = "玩家等级要求", example = "50")
    @ExcelColumn(headerName = "玩家等级要求", comment = "晋升到该爵位所需的玩家最低等级")
    private int levelRequirement; // 玩家等级要求

    @Column(name = "previous_title_id", nullable = true, columnDefinition = "BIGINT COMMENT '需要的前置爵位ID'")
    @Schema(description = "前置爵位ID", example = "2")
    @ExcelColumn(headerName = "前置爵位ID", comment = "需要的前置爵位ID")
    private Long previousTitleId; // 前置爵位ID

    @Column(name = "max_rank", nullable = false, columnDefinition = "INT COMMENT '该爵位的最大等级'")
    @Schema(description = "最大等级", example = "5")
    @ExcelColumn(headerName = "最大等级", comment = "该爵位的最大等级")
    private int maxRank; // 最大等级

    @Column(name = "privilege_status", columnDefinition = "JSON COMMENT '解锁的特权状态'")
    @Schema(description = "解锁特权状态", example = "{\"build_extra_barracks\": true}")
    @ExcelColumn(headerName = "特权状态", comment = "解锁的特权状态，JSON格式")
    private String privilegeStatus; // 特权状态（JSON格式）
}
