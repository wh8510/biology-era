package org.example.biologyera.model.chat.po;

import java.util.Date;
import lombok.Data;

/**
* @Author: 张文化
* @Description: $TODO$
* @DateTime: 2025/2/17$ 21:27$
* @Params: $params$
* @Return $return$
*/
@Data
public class Chat {
    private Integer id;

    private String question;

    private String answer;

    private Integer roomId;

    private Date createTime;
}