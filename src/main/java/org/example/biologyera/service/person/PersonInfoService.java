package org.example.biologyera.service.person;

import org.example.biologyera.model.image.vo.ImageVO;
import org.example.biologyera.model.person.po.PersonToken;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/12$ 23:47$
 * @Params: $
 * @Return $
 */
public interface PersonInfoService {
    /**
     * 生成图片验证码
     *
     * @return 图片验证码
     */
    ImageVO generateImage();
    /**
     * 生成用户 token
     *
     * @param personId 用户 id
     * @return 用户 token
     */
    PersonToken generatePersonToken(Long personId);

}
