package org.example.biologyera.model.image.po;

import cn.hutool.core.util.IdUtil;
import lombok.Data;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/17$ 16:49$
 * @Params: $
 * @Return $
 */
@Data
public class Image {
    /**
     * 图片uid
     */
    private String uid;
    /**
     * 图片code
     */
    private String code;
    public Image(String uid, String code) {
        this.uid = uid;
        this.code = code;
    }
    public Image() {

    }
    public void generateImage(String code) {
        this.uid = IdUtil.simpleUUID();
        this.code = code;
    }
}
