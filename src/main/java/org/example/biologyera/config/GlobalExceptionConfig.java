package org.example.biologyera.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.biologyera.common.model.BaseResponse;
import org.example.biologyera.common.model.ErrorCode;
import org.example.biologyera.exception.AssertionException;
import org.example.biologyera.util.ResultUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/24$ 17:02$
 * @Params: $
 * @Return $
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionConfig {

    @ExceptionHandler(value = AssertionException.class)
    public BaseResponse<?> assertionExceptionHandler(Exception e) {
        log.error("AssertionException", e);
        return ResultUtil.error(((AssertionException) e).getCode(), e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(Exception e) {
        log.error("RuntimeException", e);
        return ResultUtil.error(ErrorCode.SYSTEM_ERROR);
    }

}
