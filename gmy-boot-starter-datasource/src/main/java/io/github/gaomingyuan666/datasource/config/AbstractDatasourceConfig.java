package io.github.gaomingyuan666.datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import io.github.gaomingyuan666.datasource.constants.DataSourceType;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置抽象基类
 * 使用者可以通过继承此类来自定义数据源配置
 *
 * @author gaomingyuan
 */
@Slf4j
@Configuration
public abstract class AbstractDatasourceConfig {
    @Resource
    protected MyBatisHelperConfig myBatisHelperConfig;

    /**
     * Mapper 接口包
     */
    protected abstract String getMapperPackage();

    /**
     * Mapper XML 路径
     */
    protected abstract String getMapperLocation();

    /**
     * 构建 SqlSessionFactory（通用逻辑）
     */
    protected SqlSessionFactory buildSqlSessionFactory(DataSource dataSource) throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(getMapperLocation()));

        SqlSessionFactory factory = factoryBean.getObject();
        if (factory == null) {
            throw new IllegalStateException("SqlSessionFactory is null");
        }

        // 注册 MyBatisHelper
        myBatisHelperConfig.regist(factory, getMapperPackage());

        return factory;
    }

    /**
     * 构建 SqlSessionTemplate（通用逻辑）
     */
    protected SqlSessionTemplate buildSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 构建事务管理器（通用逻辑）
     */
    protected DataSourceTransactionManager buildTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 创建 Hikari 数据源（通用方法）
     */
    protected DataSource createHikariDataSource(String prefix) {
        return new HikariDataSource();
    }

    /**
     * 构建动态路由数据源（用于读写分离）
     */
    protected DynamicRoutingDataSource buildDynamicRoutingDataSource(DataSource writeDataSource, DataSource readDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(16);
        targetDataSources.put(DataSourceType.WRITE, writeDataSource);
        targetDataSources.put(DataSourceType.READ, readDataSource);
        return new DynamicRoutingDataSource(writeDataSource, targetDataSources);
    }

    /**
     * 构建读写分离数据源的 SqlSessionFactory
     */
    protected SqlSessionFactory buildReadWriteSqlSessionFactory(DataSource writeDataSource, DataSource readDataSource) throws Exception {
        DynamicRoutingDataSource dynamicDataSource = buildDynamicRoutingDataSource(writeDataSource, readDataSource);
        return buildSqlSessionFactory(dynamicDataSource);
    }
}