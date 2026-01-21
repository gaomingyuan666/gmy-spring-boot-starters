package io.github.gaomingyuan666.web.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 鉴权过滤器接口
 * 外部项目可以实现此接口来自定义鉴权逻辑
 *
 * @author gaomingyuan
 */
public interface AuthFilter extends Filter {

    /**
     * 从请求中获取认证信息
     *
     * @param request HTTP请求
     * @return 认证信息（如token、用户名密码等）
     */
    String getAuthInfo(HttpServletRequest request);

    /**
     * 验证认证信息的有效性
     *
     * @param authInfo 认证信息
     * @return 是否有效
     */
    boolean validateAuthInfo(String authInfo);

    /**
     * 处理认证失败的情况
     *
     * @param request  HTTP请求
     * @param response HTTP响应
     * @param message  失败消息
     */
    void handleAuthFailure(HttpServletRequest request, HttpServletResponse response, String message);

    /**
     * 处理认证成功后的逻辑
     *
     * @param request  HTTP请求
     * @param response HTTP响应
     * @param authInfo 认证信息
     */
    void handleAuthSuccess(HttpServletRequest request, HttpServletResponse response, String authInfo);

    /**
     * 判断请求是否需要鉴权
     *
     * @param request HTTP请求
     * @return 是否需要鉴权
     */
    boolean shouldAuthenticate(HttpServletRequest request);
}
