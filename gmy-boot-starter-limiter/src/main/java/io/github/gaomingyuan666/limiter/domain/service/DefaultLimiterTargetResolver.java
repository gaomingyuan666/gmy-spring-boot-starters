package io.github.gaomingyuan666.limiter.domain.service;

import org.springframework.stereotype.Component;

/**
 * 默认的限流目标解析器
 * 当用户没有实现LimiterTargetResolver接口时使用
 *
 * @author gaomingyuan
 */
@Component
public class DefaultLimiterTargetResolver implements LimiterTargetResolver {
    
    @Override
    public String getIp() {
        // 默认实现，返回空字符串
        // 实际使用时，用户应该实现自己的LimiterTargetResolver
        return "";
    }
    
    @Override
    public String getUid() {
        // 默认实现，返回空字符串
        // 实际使用时，用户应该实现自己的LimiterTargetResolver
        return "";
    }
}
