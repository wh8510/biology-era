package org.example.biologyera.common.enums;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * 过期时间
 *
 * @author zwh
 * @date 2025/3/10
 */
@Getter
public enum ExpireTime {
    /**
     * 验证码
     */
    VERIFICATION_CODE(300L, TimeUnit.SECONDS);

    /**
     * 毫秒
     */
    private final Long millisecond;

    /**
     * 秒
     */
    private final Long second;

    /**
     * 分钟
     */
    private final Long minute;

    ExpireTime(Long time, TimeUnit unit) {
        switch (unit) {
            case MILLISECONDS: {
                second = time / 1000;
            }
            break;
            case SECONDS: {
                second = time;
            }
            break;
            case MINUTES: {
                second = time * 60;
            }
            break;
            default:
                second = 0L;
        }

        millisecond = second * 1000;
        minute = second / 60;
    }
}