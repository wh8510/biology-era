package org.example.biologyera.service.Redis;

import org.example.biologyera.model.image.po.Image;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/17$ 16:46$
 * @Params: $
 * @Return $
 */
public interface RedisImageService {
    /**
     * 保存image信息到redis
     *
     * @param image image信息类
     */
    void saveImageByRedis(Image image);
    /**
     * 判断验证码是否正确
     *
     * @param image image信息类
     */
    boolean isValidByImage(Image image);
}
