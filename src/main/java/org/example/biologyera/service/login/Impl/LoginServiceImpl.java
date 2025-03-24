package org.example.biologyera.service.login.Impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.biologyera.common.model.ErrorCode;
import org.example.biologyera.exception.AssertionException;
import org.example.biologyera.mapper.person.PersonInfoMapper;
import org.example.biologyera.model.image.po.Image;
import org.example.biologyera.model.image.vo.ImageVO;
import org.example.biologyera.model.person.dto.LoginRequestDTO;
import org.example.biologyera.model.person.po.PersonInfo;
import org.example.biologyera.model.person.po.PersonToken;
import org.example.biologyera.model.person.vo.PersonInfoVO;
import org.example.biologyera.service.Redis.RedisImageService;
import org.example.biologyera.service.login.LoginService;
import org.example.biologyera.service.person.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/12$ 23:43$
 * @Params: $
 * @Return $
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    public PersonInfoMapper personInfoMapper;
    @Autowired
    public PersonInfoService personInfoService;
    @Autowired
    public RedisImageService redisImageService;
    /**
     * 生成图片验证码
     *
     * @return 图片详情 Vo
     */
    @Override
    public ImageVO getImageMessage() {
        return personInfoService.generateImage();
    }
    /**
     * 使用账号密码登录
     *
     * @param loginRequestDTO 登录 dto
     * @return 用户详细 Vo
     */
    @Override
    public PersonInfoVO loginByPassword(LoginRequestDTO loginRequestDTO){
        loginRequestDTO.setPassword(DigestUtil.md5Hex(loginRequestDTO.getPassword()));
        //验证图片验证码是否通过
        Image image = new Image(loginRequestDTO.getUid(),loginRequestDTO.getCode());
        if (!redisImageService.isValidByImage(image)) {
            throw new AssertionException(ErrorCode.OPERATION_ERROR, "验证码不匹配，请重新获取");
        }
        log.info(loginRequestDTO.toString());
        PersonInfo personInfo = personInfoMapper.getPersonInfo(loginRequestDTO);
        if (!ObjectUtil.isNotNull(personInfo)) {
            throw new AssertionException(ErrorCode.PARAMS_ERROR, "密码不匹配，请确认");
        }
        PersonToken personToken = personInfoService.generatePersonToken(personInfo.getId());
        return new PersonInfoVO(personToken.getToken(),personInfo);
    }

}
