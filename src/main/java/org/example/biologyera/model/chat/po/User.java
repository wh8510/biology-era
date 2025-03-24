package org.example.biologyera.model.chat.po;

import java.util.Date;
import lombok.Data;

/**
* @Author: 张文化
* @Description: $TODO$
* @DateTime: 2025/2/28$ 16:00$
* @Params: $params$
* @Return $return$
*/
@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String createPerson;

    private String updatePerson;

    private Date createTime;

    private Date updateTime;
}