package com.xiuxian.xiuxianserver.util;

import java.time.Instant;

public class SnowflakeIdGenerator {

    private final long epoch = 1672537600000L; // 2023-01-01 00:00:00
    private final long dataCenterIdBits = 5L; // 数据中心ID所占位数
    private final long machineIdBits = 5L; // 机器ID所占位数
    private final long sequenceBits = 12L; // 序列号所占位数

    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits); // 最大数据中心ID
    private final long maxMachineId = -1L ^ (-1L << machineIdBits); // 最大机器ID
    private final long sequenceMask = -1L ^ (-1L << sequenceBits); // 序列号掩码

    private long dataCenterId; // 数据中心ID
    private long machineId; // 机器ID
    private long sequence = 0L; // 当前序列号
    private long lastTimestamp = -1L; // 上一时间戳

    // 构造函数
    public SnowflakeIdGenerator(long dataCenterId, long machineId) {
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException("Data center ID can't be greater than " + maxDataCenterId + " or less than 0.");
        }
        if (machineId > maxMachineId || machineId < 0) {
            throw new IllegalArgumentException("Machine ID can't be greater than " + maxMachineId + " or less than 0.");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    // 生成下一个ID
    public synchronized long nextId() {
        long timestamp = currentTimeMillis();

        // 如果当前时间小于上一次生成ID的时间，抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock is moving backwards. Rejecting requests until " + lastTimestamp);
        }

        // 如果当前时间与上次生成ID的时间相同
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask; // 序列号自增
            if (sequence == 0) { // 如果序列号溢出，等待下一毫秒
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L; // 重置序列号
        }

        lastTimestamp = timestamp; // 更新上次时间戳

        // 生成ID
        return ((timestamp - epoch) << (dataCenterIdBits + machineIdBits + sequenceBits)) |
                (dataCenterId << (machineIdBits + sequenceBits)) |
                (machineId << sequenceBits) |
                sequence;
    }

    // 等待下一毫秒
    private long waitNextMillis(long lastTimestamp) {
        long timestamp = currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimeMillis();
        }
        return timestamp;
    }

    // 获取当前时间戳
    private long currentTimeMillis() {
        return Instant.now().toEpochMilli();
    }
}
