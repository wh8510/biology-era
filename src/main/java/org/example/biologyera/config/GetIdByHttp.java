package org.example.biologyera.config;

import org.example.biologyera.common.model.ErrorCode;
import org.example.biologyera.exception.AssertionException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 张文化
 * @Description: 获取token信息
 * @DateTime: 2025/3/24$ 20:54$
 * @Params: $
 * @Return $
 */
public  class GetIdByHttp {
    public static Long GetIdByHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpservletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        Long personId = Long.valueOf(httpservletRequest.getHeader("id"));
        if (personId == -1L) {
            throw new AssertionException(ErrorCode.OPERATION_ERROR,"token错误");
        }
        return personId;
    }
}
