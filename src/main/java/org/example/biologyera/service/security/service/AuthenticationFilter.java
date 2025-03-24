package org.example.biologyera.service.security.service;

import cn.hutool.core.util.ObjectUtil;
import io.lettuce.core.dynamic.annotation.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.biologyera.common.model.ErrorCode;
import org.example.biologyera.exception.AssertionException;
import org.example.biologyera.service.Redis.Impl.RedisTokenServiceImpl;
import org.example.biologyera.service.security.model.CustomRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
* @Author: 张文化
* @Description: zwh$
* @DateTime: 2025/3/23$ 21:48$
* @Params: $
* @Return $
*/
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {
    @Autowired
    private RedisTokenServiceImpl redisTokenService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws AssertionException, ServletException, IOException {
        // 判断是否是匿名接口
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        CustomRequestWrapper requestWrapper = new CustomRequestWrapper(request);

        // 获取 token
        String token = ((HttpServletRequest) servletRequest).getHeader("token");
        if (ObjectUtil.isEmpty(token)) {
            requestWrapper.addHeader("id", "-1");
            filterChain.doFilter(requestWrapper, servletResponse);
        } else {
            Long personId = redisTokenService.getIdByPersonToken(token);
            if (Objects.isNull(personId)) {
                requestWrapper.addHeader("id", "-1");
            } else {
                requestWrapper.addHeader("id", String.valueOf(personId));
            }
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
