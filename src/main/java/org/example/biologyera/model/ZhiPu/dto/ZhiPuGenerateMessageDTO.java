package org.example.biologyera.model.ZhiPu.dto;

import lombok.Data;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/3/6$ 20:05$
 * @Params: $
 * @Return $
 */
@Data
public class ZhiPuGenerateMessageDTO {
    /**
     * 编号
     */
    public Integer roomId;
    /**
     * 视频信息
     */
    public String videoMeg;
    /**
     * 图片信息
     */
    public String imageMeg;
    /**
     * 描述信息
     */
    public String message;
}
