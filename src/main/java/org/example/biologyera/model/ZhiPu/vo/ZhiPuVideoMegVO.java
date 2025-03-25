package org.example.biologyera.model.ZhiPu.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/26$ 00:51$
 * @Params: $
 * @Return $
 */
@Data
public class ZhiPuVideoMegVO {
    /**
     * roomId
     */
    private Integer roomId;
    /**
     * 视频的url
     */
    private String videoUrl;
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
