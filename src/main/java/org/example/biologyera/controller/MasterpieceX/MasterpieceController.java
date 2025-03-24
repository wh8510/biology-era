package org.example.biologyera.controller.MasterpieceX;

import org.example.biologyera.common.model.BaseResponse;
import org.example.biologyera.model.MasterpieceX.dto.ImageGenerate3dDTO;
import org.example.biologyera.model.MasterpieceX.dto.MessageGenerateImageDTO;
import org.example.biologyera.model.MasterpieceX.vo.Generate3dModelUrlVO;
import org.example.biologyera.model.MasterpieceX.vo.GenerateImageUrlVO;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateImageDTO;
import org.example.biologyera.service.MasterpieceX.MasterpieceService;
import org.example.biologyera.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/17$ 21:29$
 * @Params: $
 * @Return $
 */
@RestController
@RequestMapping("/test")
public class MasterpieceController {
    @Autowired
    private MasterpieceService masterpieceService;
    /**
     * 图片生成3d模型
     * @param imageGenerate3dDTO 传入的图片信息
     * @return 模型的url
     */
    @PostMapping("/model")
    public BaseResponse<Generate3dModelUrlVO> genera3DModelByMasterpieceX(@RequestBody ImageGenerate3dDTO imageGenerate3dDTO){
        return ResultUtil.success(masterpieceService.genera3DModelByMasterpieceX(imageGenerate3dDTO));
    }
    /**
     * 文字生成3d模型
     * @param zhiPuGenerateImageDTO 传入的文字以及需要的图片信息
     * @return 模型的url
     */
    @PostMapping("/meg/model")
    public BaseResponse<Generate3dModelUrlVO> generate3DModelByMeg(@RequestBody ZhiPuGenerateImageDTO zhiPuGenerateImageDTO) throws IOException {
        return ResultUtil.success(masterpieceService.generate3DModelByMeg(zhiPuGenerateImageDTO));
    }
}
