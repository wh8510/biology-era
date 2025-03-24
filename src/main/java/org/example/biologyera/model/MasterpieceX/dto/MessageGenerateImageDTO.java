package org.example.biologyera.model.MasterpieceX.dto;

import lombok.Data;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/18$ 23:49$
 * @Params: $
 * @Return $
 */
@Data
public class MessageGenerateImageDTO {
    /**
     * 图形生成的提示
     */
    private String prompt;
    /**
     * 种子(默认为随机)
     */
    private Integer seed;

    /**
     * 生成图像的数量(默认1最大为4)
     */
    public Integer numImages;
    /**
     * 使用生成步骤的数量(默认4)
     */
    public Integer numSteps;
    /**
     * loraId(不可以和loraWeights一起用,默认"")
     */
    public String loraId;
    /**
     * loraWeights(不可以和loraId一起用,默认"")
     */
    public String loraWeights;
    /**
     * loraScale(不可以和loraId一起用,默认0.8)
     */
    public double loraScale;
    /**
     * 长宽比(默认1:1,允许的值有（1:1，16:9，4:3，3:4，9:16，1:2，2:1）)
     */
    public String aspectRatio;
    /**
     * 输出格式(默认png.允许的值有（png, jpg, webp）)
     */
    public String outputFormat;
    /**
     * 百万像素(默认1,允许的值是（1，2，4）)
     */
    public Integer megapixels;
}
