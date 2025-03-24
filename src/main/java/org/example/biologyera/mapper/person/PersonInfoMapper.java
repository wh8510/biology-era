package org.example.biologyera.mapper.person;

import org.apache.ibatis.annotations.Mapper;
import org.example.biologyera.model.person.dto.LoginRequestDTO;
import org.example.biologyera.model.person.po.PersonInfo;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/12$ 23:52$
 * @Params: $
 * @Return $
 */
@Mapper
public interface PersonInfoMapper {
    /**
     * 使用账号密码登录
     */
    PersonInfo getPersonInfo(LoginRequestDTO loginRequestDTO);
}
