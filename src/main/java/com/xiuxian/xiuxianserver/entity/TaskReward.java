package com.xiuxian.xiuxianserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class TaskReward {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskTemplate.RewardType type; // 奖励类型

    @Column(nullable = false)
    private int value; // 奖励值

    // getter for type
    public TaskTemplate.RewardType getType() {
        return type;
    }

    // setter for type
    public void setType(TaskTemplate.RewardType type) {
        this.type = type;
    }

    // getter for value
    public int getValue() {
        return value;
    }

    // setter for value
    public void setValue(int value) {
        this.value = value;
    }
}
