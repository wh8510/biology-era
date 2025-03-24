package org.example.biologyera.service.person.Impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.example.biologyera.model.image.po.Image;
import org.example.biologyera.model.image.vo.ImageVO;
import org.example.biologyera.model.person.po.PersonToken;
import org.example.biologyera.service.Redis.RedisImageService;
import org.example.biologyera.service.Redis.RedisTokenService;
import org.example.biologyera.service.person.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/12$ 23:48$
 * @Params: $
 * @Return $
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    @Autowired
    private RedisTokenService redisTokenService;
    @Autowired
    private RedisImageService redisImageService;
    /**
     * 生成图片验证码
     *
     * @return 图片验证码
     */
    @Override
    public ImageVO generateImage() {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        Image image = new Image();
        image.generateImage(lineCaptcha.getCode());
        redisImageService.saveImageByRedis(image);
        return new ImageVO(image.getUid(),lineCaptcha.getImageBase64Data());
    }
    /**
     * 生成用户 token
     *
     * @param personId 用户 id
     * @return 用户 token
     */
    @Override
    public PersonToken generatePersonToken(Long personId) {
        PersonToken personToken = new PersonToken(personId);
        redisTokenService.saveTokenByRedis(personToken);
        return personToken;
    }
}
