package io.github.gaomingyuan666.web.config;

import io.github.gaomingyuan666.web.filter.AuthFilter;
import io.github.gaomingyuan666.web.filter.DefaultAuthFilter;
import io.github.gaomingyuan666.web.handler.GlobalWebExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author gaomingyuan
 */
@AutoConfiguration
@ConditionalOnWebApplication
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    @ConditionalOnMissingBean
    GlobalWebExceptionHandler globalWebExceptionHandler() {
        return new GlobalWebExceptionHandler();
    }

    /**
     * 默认鉴权过滤器实现
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthFilter.class)
    public AuthFilter defaultAuthFilter() {
        return new DefaultAuthFilter();
    }

    /**
     * 注册鉴权过滤器
     *
     * @param authFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter(AuthFilter authFilter) {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(authFilter);
        // 默认拦截所有路径，外部项目可以通过自定义FilterRegistrationBean覆盖此配置
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(10);

        return registrationBean;
    }

}
