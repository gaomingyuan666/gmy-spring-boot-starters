# gmy-spring-boot-starters

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/java-17%2B-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot Version](https://img.shields.io/badge/spring--boot-3.2%2B-green.svg)](https://spring.io/projects/spring-boot)

一系列可重复使用的 Spring Boot 开发工具包，涵盖常见的基础设施组件，旨在提高开发效率和代码质量。

## 功能特性

- **基础模块**：提供通用工具类、统一响应模板、异常处理体系、请求验证等核心功能
- **缓存模块**：集成 Redis、Caffeine 和 JetCache，提供本地缓存和分布式缓存的统一抽象
- **限流模块**：支持基于注解和编程式的限流方式，实现多种限流算法
- **分布式锁模块**：提供基于 Redis 的分布式锁实现，支持注解和编程式调用
- **数据源模块**：支持多数据源配置和读写分离，提供统一的数据源管理抽象
- **自动配置**：基于 Spring Boot 的自动配置机制，开箱即用
- **可扩展设计**：提供丰富的扩展点，支持自定义实现

## 典型使用场景

1. **快速搭建 RESTful API**：使用基础模块的响应模板和异常处理，快速构建标准化的 API 接口
2. **缓存优化**：通过缓存模块的统一抽象，轻松实现多级缓存策略，提高系统性能
3. **保护系统稳定性**：使用限流模块防止恶意请求和突发流量，保障系统稳定运行
4. **微服务架构**：在微服务环境中，提供统一的基础设施组件，确保服务间的一致性

## 项目结构

```
gmy-spring-boot-starters/
├── gmy-boot-starter-base/         # 基础模块
├── gmy-boot-starter-cache/        # 缓存模块
├── gmy-boot-starter-datasource/   # 数据源模块
├── gmy-boot-starter-limiter/      # 限流模块
├── gmy-boot-starter-lock/         # 分布式锁模块
├── pom.xml                        # 父项目 POM 文件
├── README.md                      # 项目文档
└── LICENSE                        # 许可证文件
```

## 模块列表

| 模块名称                    | 描述                                                      | 状态   |
| --------------------------- | --------------------------------------------------------- | ------ |
| gmy-boot-starter-base       | 基础模块，提供通用工具类、响应模板、异常处理等            | 已完成 |
| gmy-boot-starter-cache      | 缓存模块，集成 Redis、Caffeine 和 JetCache                | 已完成 |
| gmy-boot-starter-lock       | 分布式锁模块，提供基于 Redis 的分布式锁实现               | 已完成 |
| gmy-boot-starter-datasource | 数据源模块 集成mybatis-helper，支持多数据源配置和读写分离 | 已完成 |
| gmy-boot-starter-limiter    | 限流模块，提供基于注解和编程式的限流功能                  | 已完成 |

## 快速开始

### 环境要求

- Java 17+
- Spring Boot 3.2+
- Maven 3.6+

### 安装

1. **克隆项目**

```bash
git clone https://github.com/gaomingyuan666/gmy-spring-boot-starters.git
cd gmy-spring-boot-starters
```

2. **构建项目**

```bash
mvn clean install
```

### 使用方法

#### 1. 基础模块 (gmy-boot-starter-base)

**添加依赖**

```xml
<dependency>
    <groupId>io.github.gaomingyuan666</groupId>
    <artifactId>gmy-boot-starter-base</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

**核心功能**

- **响应模板**：提供统一的响应格式

  ```java
  return new BaseResponse(ResponseCode.SUCCESS, data);
  return new BaseResponse(ResponseCode.FAIL, "错误信息");
  ```

- **异常处理**：提供统一的异常体系

  ```java
  throw new BizException("业务错误");
  throw new SystemException("系统错误");
  throw new RemoteCallException("远程调用错误");
  ```

- **工具类**：提供各种通用工具类

  ```java
  // 获取Spring Bean
  UserService userService = SpringContextHolder.getBean(UserService.class);

  // 断言工具
  AssertUtil.isTrue(condition, "条件不满足");

  // Bean验证工具
  BeanValidator.validate(request);

  // SpEL表达式工具
  String value = SpElUtils.parseExpression(expression, context);
  ```

- **请求模板**：提供统一的请求格式

  ```java
  public class UserRequest extends BaseRequest {
      private String username;
      private String password;
      // getter 和 setter
  }

  public class IdRequest extends BaseRequest {
      private Long id;
      // getter 和 setter
  }
  ```

- **状态机**：提供基础状态机实现

  ```java
  // 继承BaseStateMachine实现自定义状态机
  public class OrderStateMachine extends BaseStateMachine<OrderState, OrderEvent> {
      // 实现状态转换逻辑
  }
  ```

- **验证器**：提供自定义验证注解
  ```java
  public class UserRequest {
      @IsMobile
      private String mobile;
      // getter 和 setter
  }
  ```

#### 2. 缓存模块 (gmy-boot-starter-cache)

**添加依赖**

在你的 Spring Boot 项目的 `pom.xml` 文件中添加以下依赖：

```xml
<dependency>
    <groupId>io.github.gaomingyuan666</groupId>
    <artifactId>gmy-boot-starter-cache</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

**配置 Redis**

在 `application.yml` 或 `application.properties` 文件中配置 Redis 连接信息：

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password:
    database: 0
```

**使用 Redis 工具类**

```java
// 设置缓存
RedisUtils.set("key", "value");

// 获取缓存
String value = RedisUtils.getStr("key");

// 设置带过期时间的缓存
RedisUtils.set("key", "value", 60, TimeUnit.SECONDS);
```

**使用方法缓存**

在你的应用主类上添加 `@EnableMethodCache` 注解并指定基础包路径：

```java
@SpringBootApplication
@EnableMethodCache(basePackages = "com.your.project")
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }
}
```

然后在需要缓存的方法上添加 `@Cached` 注解：

```java
@Cached(name = "user:", key = "#id", expire = 3600)
public User getUserById(Long id) {
    // 从数据库获取用户信息
    return userRepository.findById(id).orElse(null);
}
```

#### 3. 限流模块 (gmy-boot-starter-limiter)

**添加依赖**

```xml
<dependency>
    <groupId>io.github.gaomingyuan666</groupId>
    <artifactId>gmy-boot-starter-limiter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

**核心功能**

- **注解式限流**：通过注解方式实现方法级别的限流

  ```java
  @FixedWindowRateLimiter(
      target = FixedWindowRateLimiter.Target.EL,
      spEl = "#userId",
      time = 60,
      count = 10
  )
  public String getUserInfo(Long userId) {
      return "User info for " + userId;
  }
  ```

- **编程式限流**：通过工具类实现更灵活的限流

  ```java
  FrequencyControlDTO dto = new FrequencyControlDTO();
  dto.setKey("user:" + userId);
  dto.setTime(60);
  dto.setCount(10);
  dto.setUnit(TimeUnit.SECONDS);

  return FrequencyControlUtil.executeWithFrequencyControl(
      LimiterTypeEnum.FixedWindow.name(),
      dto,
      () -> {
          // 业务逻辑
          return "User info for " + userId;
      }
  );
  ```

**使用建议**

1. **自定义 IP 和 UID 获取逻辑**：实现 `LimiterTargetResolver` 接口来自定义 IP 和 UID 的获取逻辑

   ```java
   @Component
   public class CustomLimiterTargetResolver implements LimiterTargetResolver {
       @Override
       public String getIp() {
           // 从请求中获取IP地址
           return RequestContextHolder.getRequest().getRemoteAddr();
       }

       @Override
       public String getUid() {
           // 从用户会话中获取用户ID
           return UserContext.getCurrentUserId();
       }
   }
   ```

#### 4. 分布式锁模块 (gmy-boot-starter-lock)

**添加依赖**

```xml
<dependency>
    <groupId>io.github.gaomingyuan666</groupId>
    <artifactId>gmy-boot-starter-lock</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

**核心功能**

- **注解式分布式锁**：通过注解方式实现方法级别的分布式锁

  ```java
  @RedissonLock(key = "user:" + "#userId")
  public String getUserInfo(Long userId) {
      return "User info for " + userId;
  }
  ```

- **编程式分布式锁**：通过工具类实现更灵活的分布式锁

  ```java
  return lockService.executeWithLock("user:" + userId, () -> {
      // 业务逻辑
      return "User info for " + userId;
  });
  ```

#### 5. 数据源模块 (gmy-boot-starter-datasource)

**添加依赖**

```xml
<dependency>
    <groupId>io.github.gaomingyuan666</groupId>
    <artifactId>gmy-boot-starter-datasource</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

**配置示例**

在 `application.yml` 或 `application.properties` 文件中配置数据源信息：

```yaml
spring:
  datasource:
    hikari:
      connection-test-query: SELECT 1
      db1:
        write:
          jdbcUrl: "jdbc:mysql://localhost:3306/test?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
          username: "root"
          password: "root"
        read:
          jdbcUrl: "jdbc:mysql://localhost:3306/test?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
          username: "read"
          password: "read"
      db2:
        jdbcUrl: "jdbc:mysql://localhost:3306/test1?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
        username: "root"
        password: "root"
```

**使用方法**

1. **配置多数据源**

   创建数据源配置类，继承 `AbstractDatasourceConfig` 类：

   ```java
   public class MultiDatasourceConfig {

       // 读写分离数据源配置
       @Configuration
       @MapperScan(
               basePackages = "com.example.mapper.db1",
               sqlSessionTemplateRef = "db1SqlSessionTemplate"
       )
       public static class Db1Config extends AbstractDatasourceConfig {

           @Override
           protected String getMapperPackage() {
               return "com.example.mapper.db1";
           }

           @Override
           protected String getMapperLocation() {
               return "classpath*:mybatis/mapper/db1/*.xml";
           }

           @Bean
           @ConfigurationProperties("spring.datasource.hikari.db1.write")
           public DataSource initWriteDataSource() {
               return new HikariDataSource();
           }

           @Bean
           @ConfigurationProperties("spring.datasource.hikari.db1.read")
           public DataSource initReadDataSource() {
               return new HikariDataSource();
           }

           @Bean
           public DynamicRoutingDataSource db1DynamicRoutingDataSource() {
               return buildDynamicRoutingDataSource(initWriteDataSource(), initReadDataSource());
           }

           @Bean
           public SqlSessionFactory db1SqlSessionFactory() throws Exception {
               return buildReadWriteSqlSessionFactory(initWriteDataSource(), initReadDataSource());
           }

           @Bean
           public SqlSessionTemplate db1SqlSessionTemplate() throws Exception {
               return buildSqlSessionTemplate(db1SqlSessionFactory());
           }

           @Bean
           public DataSourceTransactionManager db1TransactionManager() {
               return buildTransactionManager(db1DynamicRoutingDataSource());
           }
       }

       // 单数据源配置
       @Configuration
       @MapperScan(
               basePackages = "com.example.mapper.db2",
               sqlSessionTemplateRef = "db2SqlSessionTemplate"
       )
       public static class Db2Config extends AbstractDatasourceConfig {

           @Override
           protected String getMapperPackage() {
               return "com.example.mapper.db2";
           }

           @Override
           protected String getMapperLocation() {
               return "classpath*:mybatis/mapper/db2/*.xml";
           }

           @Bean
           @ConfigurationProperties("spring.datasource.hikari.db2")
           public DataSource db2DataSource() {
               return new HikariDataSource();
           }

           @Bean
           public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
               return buildSqlSessionFactory(dataSource);
           }

           @Bean
           public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory factory) {
               return buildSqlSessionTemplate(factory);
           }

           @Bean
           public DataSourceTransactionManager db2TransactionManager(@Qualifier("db2DataSource") DataSource dataSource) {
               return buildTransactionManager(dataSource);
           }
       }
   }
   ```

2. **使用数据源**
   在业务代码中使用`DynamicRoutingDataSource.setDataSource()` 方法动态切换数据源：

   ```java
   // 动态切换数据源
   public void updateUser(User user) {
       try {
           // 切换到写数据源
           DynamicRoutingDataSource.setDataSource(DataSourceType.WRITE);
           userMapper.updateById(user);
       } finally {
           // 清空数据源上下文
           DynamicRoutingDataSource.clearDataSource();
       }
   }
   ```

**核心功能**

- **多数据源支持**：轻松配置多个数据源，适用于复杂业务场景
- **读写分离**：支持主从架构，实现读写分离，提高系统性能
- **统一抽象**：提供统一的数据源配置抽象，减少重复代码
- **灵活扩展**：支持自定义数据源配置和切换策略

## 配置选项

### 缓存模块配置

| 配置项                | 描述             | 默认值    |
| --------------------- | ---------------- | --------- |
| spring.redis.host     | Redis 服务器地址 | localhost |
| spring.redis.port     | Redis 服务器端口 | 6379      |
| spring.redis.password | Redis 服务器密码 | 空        |
| spring.redis.database | Redis 数据库索引 | 0         |

## 项目架构

### 基础模块架构

- **config**: 配置类，包括基础配置和配置属性
- **constant**: 常量类，定义通用常量，如环境配置常量
- **exception**: 异常类，提供统一的异常体系，包括业务异常、系统异常等
- **model**: 模型类，包括模板类
- **request**: 请求类，提供统一的请求格式，如基础请求、ID 请求
- **response**: 响应类，提供统一的响应格式和响应码
- **service**: 服务类，提供基础服务接口
- **statemachine**: 状态机，提供基础状态机实现
- **utils**: 工具类，提供各种通用工具，如 Spring 上下文持有器、断言工具等
- **validator**: 验证器，提供自定义验证注解，如手机号验证

### 缓存模块架构

- **config**: 配置类，包括 Redis、Redisson 和缓存配置
- **constant**: 常量类，定义缓存相关的常量
- **service**: 缓存服务类，提供本地缓存和 Redis 缓存的抽象实现
- **utils**: 工具类，包括 Redis 操作工具和 JSON 转换工具

### 限流模块架构

- **aspect**: 切面类，实现注解式限流
- **domain**: 领域模型
  - **annotation**: 注解类，定义限流注解
  - **dto**: 数据传输对象，定义限流配置
  - **enums**: 枚举类，定义限流类型
  - **service**: 服务类，实现限流逻辑
- **service**: 服务类，提供限流策略工厂和工具类

### 分布式锁模块架构

- **annotation**: 注解类，定义分布式锁注解
- **aspect**: 切面类，实现注解式分布式锁
- **config**: 配置类，提供自动配置功能
- **service**: 服务类，实现分布式锁核心逻辑

### 数据源模块架构

- **config**: 配置类，包括抽象数据源配置、动态路由数据源和示例配置
  - **AbstractDatasourceConfig**: 数据源配置抽象基类，提供通用的数据源配置方法
  - **DynamicRoutingDataSource**: 动态路由数据源，实现读写分离
  - **MultiDatasourceExampleConfig**: 多数据源配置示例
  - **MyBatisHelperConfig**: mybatis-helper 辅助配置
- **constants**: 常量类，定义数据源类型枚举
  - **DataSourceType**: 数据源类型枚举，包括 READ 和 WRITE

## 贡献指南

1. Fork 本项目
2. 创建你的特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交你的更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 打开一个 Pull Request

## 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

## 联系方式

- 作者: gaomingyuan
- GitHub: [https://github.com/gaomingyuan666](https://github.com/gaomingyuan666)

---

**享受编码的乐趣！** 🚀
