package org.example.biologyera.config;

import lombok.RequiredArgsConstructor;
import org.example.biologyera.service.security.service.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/23$ 21:46$
 * @Params: $
 * @Return $
 */
@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final AuthenticationFilter authenticationFilter;
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterFilterRegistrationBean() {
        FilterRegistrationBean<AuthenticationFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(authenticationFilter);
        bean.setOrder(1);
        return bean;
    }
}
