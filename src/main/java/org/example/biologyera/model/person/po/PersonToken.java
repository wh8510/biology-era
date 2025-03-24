package org.example.biologyera.model.person.po;
import cn.hutool.core.util.IdUtil;
import lombok.Data;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/12$ 23:29$
 * @Params: $
 * @Return $
 */
@Data
public class PersonToken {

    /**
     * token
     */
    private String token;

    /**
     * 用户 id
     */
    private Long personId;

    /**
     * 登录时间
     */
    private Long time;

    public PersonToken(Long personId) {
        this.token = IdUtil.simpleUUID();
        this.personId = personId;
        this.time = System.currentTimeMillis();
    }

}
