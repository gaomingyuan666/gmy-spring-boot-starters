//package io.github.gaomingyuan666.datasource.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * 多数据源配置示例
// * 展示如何使用AbstractDatasourceConfig配置多个数据源
// *
// * @author gaomingyuan
// */
//public class MultiDatasourceExampleConfig {
//
//    // =======================
//    // db1 读写分离数据源
//    // =======================
//
//    @Configuration
//    @MapperScan(
//            basePackages = "io.github.gaomingyuan666.mapper.db1",
//            sqlSessionTemplateRef = "db1SqlSessionTemplate"
//    )
//    public static class Db1Config extends AbstractDatasourceConfig {
//
//        @Override
//        protected String getMapperPackage() {
//            return "io.github.gaomingyuan666.mapper.db1";
//        }
//
//        @Override
//        protected String getMapperLocation() {
//            return "classpath*:mybatis/mapper/db1/*.xml";
//        }
//
//        @Bean
//        @ConfigurationProperties("spring.datasource.hikari.db1.write")
//        public DataSource initWriteDataSource() {
//            return new HikariDataSource();
//        }
//
//        @Bean
//        @ConfigurationProperties("spring.datasource.hikari.db1.read")
//        public DataSource initReadDataSource() {
//            return new HikariDataSource();
//        }
//
//        @Bean
//        public DynamicRoutingDataSource db1DynamicRoutingDataSource() {
//            return buildDynamicRoutingDataSource(initWriteDataSource(), initReadDataSource());
//        }
//
//        @Bean
//        public SqlSessionFactory db1SqlSessionFactory() throws Exception {
//            return buildReadWriteSqlSessionFactory(initWriteDataSource(), initReadDataSource());
//        }
//
//        @Bean
//        public SqlSessionTemplate db1SqlSessionTemplate() throws Exception {
//            return buildSqlSessionTemplate(db1SqlSessionFactory());
//        }
//
//        @Bean
//        public DataSourceTransactionManager db1TransactionManager() {
//            return buildTransactionManager(db1DynamicRoutingDataSource());
//        }
//    }
//
//    // =======================
//    // db2 数据源
//    // =======================
//
//    @Configuration
//    @MapperScan(
//            basePackages = "io.github.gaomingyuan666.mapper.db2",
//            sqlSessionTemplateRef = "db2SqlSessionTemplate"
//    )
//    public static class Db2Config extends AbstractDatasourceConfig {
//
//        @Override
//        protected String getMapperPackage() {
//            return "io.github.gaomingyuan666.mapper.db2";
//        }
//
//        @Override
//        protected String getMapperLocation() {
//            return "classpath*:mybatis/mapper/db2/*.xml";
//        }
//
//        @Bean
//        @ConfigurationProperties("spring.datasource.hikari.db2")
//        public DataSource db2DataSource() {
//            return new HikariDataSource();
//        }
//
//        @Bean
//        public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
//            return buildSqlSessionFactory(dataSource);
//        }
//
//        @Bean
//        public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory factory) {
//            return buildSqlSessionTemplate(factory);
//        }
//
//        @Bean
//        public DataSourceTransactionManager db2TransactionManager(@Qualifier("db2DataSource") DataSource dataSource) {
//            return buildTransactionManager(dataSource);
//        }
//    }
//}
