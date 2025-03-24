package org.example.biologyera.model.image.vo;

import cn.hutool.core.util.IdUtil;
import lombok.Data;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/17$ 15:14$
 * @Params: $
 * @Return $
 */
@Data
public class ImageVO {
    /**
     * 图片uid
     */
    private String uid;
    /**
     * 图片
     */
    private String image;
    public ImageVO(String uid,String image) {
        this.uid = uid;
        this.image = image;
    }
}
