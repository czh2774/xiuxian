package com.xiuxian.xiuxianserver.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 测试类：DatabaseConnectionTest
 * 目的：测试 MySQL 数据库连接是否正常
 * 注释：通过 EntityManager 执行简单的数据库操作，确保数据库连接成功。
 */
@DirtiesContext
@SpringBootTest
public class DatabaseConnectionTest {

    // 注入 EntityManager，用于与数据库交互
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 测试方法：testMySQLConnection
     * 目的：测试数据库连接是否成功
     * 步骤：
     *  1. 尝试获取数据库连接
     *  2. 执行查询操作，检查是否有任何异常发生
     *  3. 断言 EntityManager 不为 null，表示连接正常
     */
    @Test
    @Transactional // 保证测试方法中的数据库操作在事务中执行
    public void testMySQLConnection() {
        // 执行简单查询，检查 EntityManager 是否注入成功
        assertNotNull(entityManager, "EntityManager 注入失败，数据库可能无法连接。");

        // 尝试获取数据库连接信息
        String query = "SELECT 1"; // 该查询不会对数据库造成影响，只是检查连接
        Object result = entityManager.createNativeQuery(query).getSingleResult();

        // 断言返回结果不为 null，表示查询成功，数据库连接正常
        assertNotNull(result, "数据库连接失败，查询结果为空。");
    }
}
