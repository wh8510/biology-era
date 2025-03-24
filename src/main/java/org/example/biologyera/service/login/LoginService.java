package org.example.biologyera.service.login;

import org.example.biologyera.model.image.vo.ImageVO;
import org.example.biologyera.model.person.dto.LoginRequestDTO;
import org.example.biologyera.model.person.po.PersonInfo;
import org.example.biologyera.model.person.vo.PersonInfoVO;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/12$ 23:43$
 * @Params: $
 * @Return $
 */
public interface LoginService {
    /**
     * 生成图片验证码
     *
     * @return 图片详情 Vo
     */
    ImageVO getImageMessage();
    /**
     * 使用账号密码登录
     *
     * @param loginRequestDTO 登录 dto
     * @return 用户详细 Vo
     */
    PersonInfoVO loginByPassword(LoginRequestDTO loginRequestDTO);

}
