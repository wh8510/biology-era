package org.example.biologyera.model.person.vo;

import lombok.Data;
import org.example.biologyera.model.person.po.PersonInfo;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/3/13$ 00:13$
 * @Params: $
 * @Return $
 */
@Data
public class PersonInfoVO {
    /**
     * token
     */
    private String token;
    /**
     * person信息
     */
    private PersonInfo info;

    public PersonInfoVO(String token, PersonInfo info) {
        this.token = token;
        this.info = info;
    }
}
