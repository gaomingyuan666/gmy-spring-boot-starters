package io.github.gaomingyuan666.limiter.config;

import io.github.gaomingyuan666.limiter.aspect.FixedWindowRateLimiterAspect;
import io.github.gaomingyuan666.limiter.domain.service.DefaultLimiterTargetResolver;
import io.github.gaomingyuan666.limiter.domain.service.FixedWindowRateLimiterController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 限流自动配置类
 *
 * @author gaomingyuan
 */
@Configuration
public class LimiterAutoConfiguration {

    /**
     * 注册默认限流目标解析器
     *
     * @return DefaultLimiterTargetResolver
     */
    @Bean
    public DefaultLimiterTargetResolver defaultLimiterTargetResolver() {
        return new DefaultLimiterTargetResolver();
    }

    /**
     * 注册固定窗口速率限流控制器
     *
     * @return FixedWindowRateLimiterController
     */
    @Bean
    public FixedWindowRateLimiterController fixedWindowRateLimiterController() {
        return new FixedWindowRateLimiterController();
    }

    /**
     * 注册固定窗口速率限流切面
     *
     * @param defaultLimiterTargetResolver DefaultLimiterTargetResolver
     * @return FixedWindowRateLimiterAspect
     */
    @Bean
    public FixedWindowRateLimiterAspect fixedWindowRateLimiterAspect(DefaultLimiterTargetResolver defaultLimiterTargetResolver) {
        FixedWindowRateLimiterAspect aspect = new FixedWindowRateLimiterAspect();
        // 手动注入 DefaultLimiterTargetResolver
        try {
            java.lang.reflect.Field field = FixedWindowRateLimiterAspect.class.getDeclaredField("limiterTargetResolver");
            field.setAccessible(true);
            field.set(aspect, defaultLimiterTargetResolver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject DefaultLimiterTargetResolver into FixedWindowRateLimiterAspect", e);
        }
        return aspect;
    }

}
