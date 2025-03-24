package org.example.biologyera.service.MasterpieceX;


import org.example.biologyera.model.MasterpieceX.dto.ImageGenerate3dDTO;
import org.example.biologyera.model.MasterpieceX.dto.MessageGenerateImageDTO;
import org.example.biologyera.model.MasterpieceX.vo.Generate3dModelUrlVO;
import org.example.biologyera.model.MasterpieceX.vo.GenerateImageUrlVO;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateImageDTO;

import java.io.IOException;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/17$ 21:30$
 * @Params: $
 * @Return $
 */
public interface MasterpieceService {
    /**
     * 图片生成3d模型
     * @param imageGenerate3dDTO 传入的图片信息
     * @return 模型的url
     */
    Generate3dModelUrlVO genera3DModelByMasterpieceX(ImageGenerate3dDTO imageGenerate3dDTO);
    /**
     * 文字生成3d模型
     * @param zhiPuGenerateImageDTO 传入的文字以及需要的图片信息
     * @return 模型的url
     */
    Generate3dModelUrlVO generate3DModelByMeg(ZhiPuGenerateImageDTO zhiPuGenerateImageDTO) throws IOException;
}
