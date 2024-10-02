package com.xiuxian.xiuxianserver.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "player_building")
public class PlayerBuilding {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 建筑记录ID

    private Long playerId; // 玩家ID
    private Long buildingId; // 建筑ID，指向建筑模板表
    private int level; // 建筑当前等级
}
