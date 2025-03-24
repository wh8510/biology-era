package org.example.biologyera.model.person.po;

import lombok.Data;
import org.example.biologyera.model.ObjectPO;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/13$ 00:00$
 * @Params: $
 * @Return $
 */
@Data
public class PersonInfo extends ObjectPO {
    /**
     * id
     */
    public Long id;
    /**
     * 用户名
     */
    public String username;
    /**
     * 密码
     */
    public String password;
    /**
     * 手机号
     */
    public String phone;
    /**
     * 头像
     */
    public String avtar;
    /**
     * 类型
     */
    public String type;
    /**
     * 状态
     */
    public String state;
}
