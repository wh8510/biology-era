package org.example.biologyera.common.model;

import lombok.Getter;

/**
 * 异常处理枚举类
 *
 * @author zwh
 * @date 2025/3/10
 */
@Getter
public enum ErrorCode {

    /**
     * ok
     */
    SUCCESS(200, "ok"),

    /**
     * 请求参数错误
     */
    PARAMS_ERROR(40000, "请求参数错误"),

    /**
     * 未登录
     */
    NOT_LOGIN_ERROR(40100, "未登录"),

    /**
     * 无权限
     */
    NO_AUTH_ERROR(40101, "无权限"),

    /**
     * 请求数据不存在
     */
    NOT_FOUND_ERROR(40400, "请求数据不存在"),

    /**
     * 禁止访问
     */
    FORBIDDEN_ERROR(40300, "禁止访问"),

    /**
     * 系统内部异常
     */
    SYSTEM_ERROR(50000, "系统内部异常"),

    /**
     * 操作失败
     */
    OPERATION_ERROR(50001, "操作失败"),

    /**
     * 接口调用失败
     */
    API_REQUEST_ERROR(50010, "接口调用失败");


    /**
     * 信息码
     */
    private final Integer code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
