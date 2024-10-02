package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.Column;
import lombok.Data;
import jakarta.persistence.*;


/**
 * 季节表，存储四季的基础信息和描述。
 */
@Data
@Entity
@Table(name = "season")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long seasonId; // 季节ID

    @Column(nullable = false, length = 50)
    private String name; // 季节名称（春、夏、秋、冬）

    @Column(nullable = false)
    private String description; // 季节的多项说明，描述季节的特性和影响

    @Column(nullable = false, length = 255)
    private String imageUrl; // 季节对应的介绍图片URL
}
