package org.example.biologyera.util;


import org.example.biologyera.common.model.BaseResponse;
import org.example.biologyera.common.model.ErrorCode;

/**
 * 返回工具类
 *
 * @author zwh
 * @date 2025/3/10
 */
public class ResultUtil {

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  类型
     * @return BaseResponse
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return BaseResponse
     */
    public static BaseResponse<Object> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code    错误码
     * @param message 信息
     * @return BaseResponse<Object>
     */
    public static BaseResponse<Object> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @param message   信息
     * @return BaseResponse<Object>
     */
    public static BaseResponse<Object> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(), null, message);
    }
}
