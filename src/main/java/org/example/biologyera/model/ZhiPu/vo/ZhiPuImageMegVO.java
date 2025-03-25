package org.example.biologyera.model.ZhiPu.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/25$ 22:23$
 * @Params: $
 * @Return $
 */
@Data
public class ZhiPuImageMegVO {
    /**
     * roomId
     */
    private Integer roomId;
    /**
     * 图片的url
     */
    private String imageUrl;
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
