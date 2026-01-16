package io.github.gaomingyuan666.limiter.domain.service;

/**
 * 限流目标解析器，用于解析IP和UID
 * 外部可以通过实现此接口来自定义IP和UID的获取逻辑
 *
 * @author gaomingyuan
 */
public interface LimiterTargetResolver {
    
    /**
     * 获取当前请求的IP地址
     *
     * @return IP地址
     */
    String getIp();
    
    /**
     * 获取当前用户的ID
     *
     * @return 用户ID
     */
    String getUid();
}
