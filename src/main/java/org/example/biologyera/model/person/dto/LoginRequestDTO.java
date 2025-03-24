package org.example.biologyera.model.person.dto;

import lombok.Data;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/12$ 23:39$
 * @Params: $
 * @Return $
 */
@Data
public class LoginRequestDTO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 图片 uid
     */
    private String uid;

    /**
     * 图片 code
     */
    private String code;
}
