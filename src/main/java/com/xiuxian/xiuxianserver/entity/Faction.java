package com.xiuxian.xiuxianserver.entity;

import com.xiuxian.xiuxianserver.util.ExcelColumn;
import com.xiuxian.xiuxianserver.util.ExcelField;
import lombok.Data;
import jakarta.persistence.*;


/**
 * 势力表，存储每个势力的基础信息。
 */
@ExcelField
@Data
@Entity
@Table(name = "faction")
public class Faction {

    @Id
    @Column(nullable = false, updatable = false)
    @ExcelColumn(headerName = "势力ID", comment = "势力ID")
    private Long id; // 势力ID

    @Column(nullable = false, length = 255)
    @ExcelColumn(headerName = "势力名称", comment = "势力名称")
    private String name; // 势力名称

    @Column(nullable = true)
    @ExcelColumn(headerName = "势力描述", comment = "势力描述")
    private String description; // 势力描述

    @Column(nullable = false, length = 255)
    @ExcelColumn(headerName = "势力主公的图片URL", comment = "势力主公的图片URL")
    private String leaderImageUrl; // 势力主公的图片URL

    @Column(nullable = false, length = 255)
    @ExcelColumn(headerName = "客户端选择时的图片URL", comment = "客户端选择时的图片URL")
    private String selectionImageUrl; // 客户端选择时的图片URL
}
