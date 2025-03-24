package org.example.biologyera.model.chat.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/24$ 20:30$
 * @Params: $
 * @Return $
 */
@Data
public class ChatMessageVO {
    /**
     * roomId
     */
    private Integer roomId;
    /**
     * 问题
     */
    private  String question;
    /**
     * 答案
     */
    private String answer;
    /**
     * 创建时间
     */
    private Date createTime;
}
