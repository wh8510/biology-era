package org.example.biologyera.model.MasterpieceX.dto;

import lombok.Data;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/3/18$ 21:16$
 * @Params: $
 * @Return $
 */
@Data
public class ImageGenerate3dDTO {
    /**
     * 图片请求id
     */
    public String imageRequestId;
    /**
     * 种子，默认1
     */
    public Integer seed;
    /**
     * 纹理大小；默认1024（只可以为256、512、1024、2048）
     */
    public Integer textureSize;
    /**
     * 图片url（有这个的时候就不需要imageRequestId）
     */
    public String imageUrl;
}
