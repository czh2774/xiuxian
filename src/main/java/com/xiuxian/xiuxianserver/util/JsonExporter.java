package com.xiuxian.xiuxianserver.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.xiuxian.xiuxianserver.entity.TaskTemplate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.sqm.UnknownEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class JsonExporter {

    private static final Logger logger = LoggerFactory.getLogger(JsonExporter.class);

    private static final EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    private final String outputDirectory = "output/json"; // 默认输出目录
    private final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.ALWAYS)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE); // 设置为下划线命名策略

    // ANSI颜色代码
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    // 用于存储扫描到的模板类
    private static Set<Class<?>> templateClasses;

    /**
     * 使用 Hibernate Configuration 手动创建 EntityManagerFactory
     */
    private static EntityManagerFactory createEntityManagerFactory() {
        try {
            Configuration configuration = new Configuration();

            // 数据库连接配置
            Properties settings = new Properties();
            settings.put("hibernate.connection.driver_class", "org.mariadb.jdbc.Driver");
            settings.put("hibernate.connection.url", "jdbc:mariadb://localhost:3306/xiuxian_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8");
            settings.put("hibernate.connection.username", "root");
            settings.put("hibernate.connection.password", "Leng9s9bxs");

            // Hibernate 配置
            settings.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
            settings.put("hibernate.hbm2ddl.auto", "validate");
            settings.put("hibernate.show_sql", "true");
            settings.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");

            configuration.setProperties(settings);

            // 初始化模板类集合
            templateClasses = findTemplateClasses();

            // 添加实体类到配置中
            templateClasses.forEach(configuration::addAnnotatedClass);

            // 手动添加 Reward 类
            configuration.addAnnotatedClass(TaskTemplate.Reward.class);

            // 使用 SessionFactory 创建 EntityManagerFactory
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            return sessionFactory.unwrap(EntityManagerFactory.class);

        } catch (Exception e) {
            logger.error(RED + "无法创建 EntityManagerFactory，请检查数据库连接设置。" + RESET, e);
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 自动扫描指定包路径下带有 @ExcelField 注解的模板类
     */
    private static Set<Class<?>> findTemplateClasses() {
        Set<Class<?>> classes = new HashSet<>();
        // 创建一个扫描器，用于扫描带有指定注解的类
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(ExcelField.class));

        String basePackage = "com.xiuxian.xiuxianserver.entity"; // 扫描的基础包路径

        for (var beanDef : scanner.findCandidateComponents(basePackage)) {
            try {
                Class<?> clazz = Class.forName(beanDef.getBeanClassName());
                if (clazz.isAnnotationPresent(ExcelField.class)) {
                    classes.add(clazz);
                    logger.info(GREEN + "发现模板类: {}" + RESET, clazz.getSimpleName());
                }
            } catch (ClassNotFoundException e) {
                logger.error(RED + "扫描过程中找不到类: {}" + RESET, beanDef.getBeanClassName(), e);
            }
        }

        return classes;
    }

    /**
     * 导出所有带有 @ExcelField 注解的模板类数据为 JSON 文件
     */
    public void exportJson() {
        if (templateClasses == null || templateClasses.isEmpty()) {
            logger.warn(YELLOW + "未发现带有 @ExcelField 注解的模板类，导出终止。" + RESET);
            return;
        }
        templateClasses.forEach(this::exportClassDataToJson);
    }

    /**
     * 导出指定类的数据为 JSON 文件
     *
     * @param clazz 要导出的模板类
     */
    private <T> void exportClassDataToJson(Class<T> clazz) {
        // 检查类是否包含 @ExcelField 注解
        if (!clazz.isAnnotationPresent(ExcelField.class)) {
            logger.warn(YELLOW + "类 {} 未包含 @ExcelField 注解，跳过导出。" + RESET, clazz.getSimpleName());
            return;
        }

        List<T> dataList = null;
        try {
            // 从数据库中获取该类的所有实例
            dataList = entityManager.createQuery("FROM " + clazz.getSimpleName(), clazz).getResultList();
        } catch (UnknownEntityException e) {
            // 实体类未注册或找不到的情况
            logger.error(RED + "类 {} 未在 Hibernate 配置中注册，无法导出数据。" + RESET, clazz.getSimpleName(), e);
            return;
        } catch (PersistenceException e) {
            // 数据库连接问题或查询失败
            logger.error(RED + "导出 {} 数据时发生数据库访问错误，请检查连接。" + RESET, clazz.getSimpleName(), e);
            return;
        } catch (Exception e) {
            // 其他异常
            logger.error(RED + "导出 {} 数据时发生未知错误。" + RESET, clazz.getSimpleName(), e);
            return;
        }

        if (dataList == null || dataList.isEmpty()) {
            // 无数据情况
            logger.info(YELLOW + "类 {} 没有数据可导出。" + RESET, clazz.getSimpleName());
            return;
        }

        String fileName = clazz.getSimpleName() + ".json";
        File directory = new File(outputDirectory);
        // 如果输出目录不存在，则创建
        if (!directory.exists() && !directory.mkdirs()) {
            logger.error(RED + "无法创建输出目录: {}" + RESET, outputDirectory);
            return;
        }

        File outputFile = new File(directory, fileName);

        // 将数据写入 JSON 文件
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, dataList);
            logger.info(GREEN + "类 {} 的数据成功导出到: {}" + RESET, clazz.getSimpleName(), outputFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error(RED + "写入 {} 的 JSON 文件失败: {}" + RESET, clazz.getSimpleName(), outputFile.getAbsolutePath(), e);
        }
    }

    /**
     * 主方法，直接运行 JSON 导出工具
     */
    public static void main(String[] args) {
        JsonExporter jsonExporter = new JsonExporter();
        jsonExporter.exportJson();
        entityManagerFactory.close(); // 关闭 EntityManagerFactory
    }
}
