package com.xiuxian.xiuxianserver.entity;

import lombok.Data;
import jakarta.persistence.*;


/**
 * 势力表，存储每个势力的基础信息。
 */
@Data
@Entity
@Table(name = "faction")
public class Faction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long factionId; // 势力ID

    @Column(nullable = false, length = 255)
    private String name; // 势力名称

    @Column(nullable = true)
    private String description; // 势力描述

    @Column(nullable = false, length = 255)
    private String leaderImageUrl; // 势力主公的图片URL

    @Column(nullable = false, length = 255)
    private String selectionImageUrl; // 客户端选择时的图片URL
}
