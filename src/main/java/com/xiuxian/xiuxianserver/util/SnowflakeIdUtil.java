package com.xiuxian.xiuxianserver.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * SnowflakeIdUtil
 * 这是一个用于生成全局唯一 ID 的工具类，基于 Hutool 的 Snowflake 算法。
 *
 * 使用该工具类可以确保在整个应用中生成的 ID 保持一致和唯一。
 */
public class SnowflakeIdUtil {

    // 数据中心 ID (0~31)，可以根据不同的部署环境进行配置
    private static final long DATA_CENTER_ID = 1L;

    // 机器 ID (0~31)，根据每台服务器的节点 ID 进行配置
    private static final long MACHINE_ID = 1L;

    // Snowflake 实例，单例模式确保线程安全
    private static final Snowflake snowflake = IdUtil.getSnowflake(MACHINE_ID, DATA_CENTER_ID);

    /**
     * 生成一个新的唯一 ID。
     *
     * @return 全局唯一的 Long 类型 ID
     */
    public static long generateId() {
        return snowflake.nextId();
    }

    /**
     * 生成一个新的唯一 ID（字符串形式）。
     *
     * @return 全局唯一的 String 类型 ID
     */
    public static String generateIdStr() {
        return snowflake.nextIdStr();
    }

}
