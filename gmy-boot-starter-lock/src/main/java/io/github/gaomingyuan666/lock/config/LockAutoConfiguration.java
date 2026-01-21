package io.github.gaomingyuan666.lock.config;

import io.github.gaomingyuan666.lock.aspect.RedissonLockAspect;
import io.github.gaomingyuan666.lock.service.LockService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁自动配置类
 *
 * @author gaomingyuan
 */
@Configuration
public class LockAutoConfiguration {

    /**
     * 注册分布式锁服务
     *
     * @return LockService
     */
    @Bean
    public LockService lockService() {
        return new LockService();
    }

    /**
     * 注册分布式锁切面
     *
     * @param lockService LockService
     * @return RedissonLockAspect
     */
    @Bean
    public RedissonLockAspect redissonLockAspect(LockService lockService) {
        RedissonLockAspect aspect = new RedissonLockAspect();
        // 手动注入 LockService
        try {
            java.lang.reflect.Field field = RedissonLockAspect.class.getDeclaredField("lockService");
            field.setAccessible(true);
            field.set(aspect, lockService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject LockService into RedissonLockAspect", e);
        }
        return aspect;
    }

}
