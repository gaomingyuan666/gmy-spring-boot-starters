package io.github.gaomingyuan666.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 默认鉴权过滤器实现
 * 当外部项目没有提供自定义AuthFilter实现时使用
 * 此实现不做任何实际的鉴权操作，只是通过请求
 *
 * @author gaomingyuan
 */
public class DefaultAuthFilter implements AuthFilter {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("DefaultAuthFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 检查是否需要鉴权
        if (shouldAuthenticate(httpRequest)) {
            // 获取认证信息
            String authInfo = getAuthInfo(httpRequest);

            // 验证认证信息
            if (!validateAuthInfo(authInfo)) {
                // 处理认证失败
                handleAuthFailure(httpRequest, httpResponse, "Authentication failed");
                return;
            }

            // 处理认证成功
            handleAuthSuccess(httpRequest, httpResponse, authInfo);
        }

        // 继续执行过滤器链
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("DefaultAuthFilter destroyed");
    }

    @Override
    public String getAuthInfo(HttpServletRequest request) {
        // 默认从Authorization头获取
        return request.getHeader("Authorization");
    }

    @Override
    public boolean validateAuthInfo(String authInfo) {
        // 默认实现：所有请求都通过
        return true;
    }

    @Override
    public void handleAuthFailure(HttpServletRequest request, HttpServletResponse response, String message) {
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(message);
        } catch (IOException e) {
            logger.error("Error handling auth failure", e);
        }
    }

    @Override
    public void handleAuthSuccess(HttpServletRequest request, HttpServletResponse response, String authInfo) {
        // 默认实现：不做任何处理
    }

    @Override
    public boolean shouldAuthenticate(HttpServletRequest request) {
        // 默认实现：所有请求都需要鉴权
        return true;
    }
}
