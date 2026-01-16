# gmy-spring-boot-starters

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

一系列可重复使用的 Spring Boot 开发工具包，涵盖常见的基础设施组件，旨在提高开发效率和代码质量。

## 项目结构

```
gmy-spring-boot-starters/
├── gmy-boot-starter-base/         # 基础模块
├── gmy-boot-starter-cache/        # 缓存模块
├── pom.xml                        # 父项目 POM 文件
├── README.md                      # 项目文档
└── LICENSE                        # 许可证文件
```

## 模块列表

| 模块名称               | 描述                                           | 状态   |
| ---------------------- | ---------------------------------------------- | ------ |
| gmy-boot-starter-base  | 基础模块，提供通用工具类、响应模板、异常处理等 | 已完成 |
| gmy-boot-starter-cache | 缓存模块，集成 Redis、Caffeine 和 JetCache     | 已完成 |

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

## 配置选项

### 基础模块配置

| 配置项                         | 描述                         | 默认值 |
| ------------------------------ | ---------------------------- | ------ |
| gmy.base.enabled               | 是否启用基础模块             | true   |
| gmy.base.spring-context-holder | 是否启用 Spring 上下文持有器 | true   |

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
