package org.example.biologyera.model.chat.dto;

import lombok.Data;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/2/17$ 21:31$
 * @Params: $
 * @Return $
 */
@Data
public class ChatRequest {
    /**
     * 房间号
     */
    private Integer roomId;

    /**
     * 问题
     */
    private String text;
}
