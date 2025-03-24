package org.example.biologyera.service.Redis.Impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.example.biologyera.common.model.ErrorCode;
import org.example.biologyera.exception.AssertionException;
import org.example.biologyera.model.image.po.Image;
import org.example.biologyera.service.Redis.RedisImageService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.example.biologyera.common.enums.ExpireTime.VERIFICATION_CODE;

/**
* @Author: 张文化
* @Description: zwh$
* @DateTime: 2025/3/17$ 16:46$
* @Params: $
* @Return $
*/
@Repository
@RequiredArgsConstructor
public class RedisImageServiceImpl implements RedisImageService {
    private final RedisTemplate<Object, Object> redisTemplate;
    /**
     * 保存image信息到redis
     *
     * @param image image信息类
     */
    @Override
    public void saveImageByRedis(Image image) {
        String key = Image.class.getSimpleName() + ":" + image.getUid();
        redisTemplate.delete("Image");
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(image), VERIFICATION_CODE.getSecond(), TimeUnit.SECONDS);
    }
    public Image getByUid(String uid) {
        String key = Image.class.getSimpleName() + ":" + uid;
        Object value = redisTemplate.opsForValue().get(key);
        Image image = JSONUtil.toBean((String) value, Image.class);
        if (ObjectUtil.isNull(image)) {
            return null;
        }
        return image;
    }
    public void deleteByUid(String uid) {
        String key = Image.class.getSimpleName() + ":" + uid;
        redisTemplate.delete(key);
    }
    /**
     * 判断验证码是否正确
     *
     * @param image image信息类
     */
    @Override
    public boolean isValidByImage(Image image) {
        Image image1 = this.getByUid(image.getUid());
        if (ObjectUtil.isNull(image1)) {
            throw new AssertionException(ErrorCode.OPERATION_ERROR, "验证码超时，请重新获取");
        }

        //校验的时候删除验证码码
        this.deleteByUid(image1.getUid());
        // 判断是否相同
        return Objects.equals(image.getCode(), image1.getCode());
    }
}
