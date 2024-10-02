package com.xiuxian.xiuxianserver.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "player_feature_unlock")
public class PlayerFeatureUnlock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long playerId; // 玩家ID
    private Long featureId; // 功能ID
}
