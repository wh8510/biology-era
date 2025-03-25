package org.example.biologyera.service.MasterpieceX.Impl;

import org.example.biologyera.ai.ZhiPu.ZhiPuImageAndMessageApi;
import org.example.biologyera.ai.masterpieceX.MasterpieceX;
import org.example.biologyera.config.GetIdByHttp;
import org.example.biologyera.mapper.Masterpiece.MasterpieceMapper;
import org.example.biologyera.model.MasterpieceX.dto.ImageGenerate3dDTO;
import org.example.biologyera.model.MasterpieceX.vo.Generate3dModelUrlVO;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateImageDTO;
import org.example.biologyera.service.MasterpieceX.MasterpieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/17$ 21:30$
 * @Params: $
 * @Return $
 */
@Service
public class MasterpieceServiceImpl implements MasterpieceService {
    @Autowired
    private MasterpieceX masterpieceX;
    @Autowired
    private ZhiPuImageAndMessageApi zhiPuImageAndMessageApi;
    @Autowired
    private MasterpieceMapper masterpieceMapper;
    @Value("${Masterpiece-X.BEARER_TOKEN}")
    private String BEARER_TOKEN;

    /**
     * 图片生成3d模型
     * @param imageGenerate3dDTO 传入的图片信息
     * @return 模型的url
     */
    @Override
    public Generate3dModelUrlVO genera3DModelByMasterpieceX(ImageGenerate3dDTO imageGenerate3dDTO) {
        Generate3dModelUrlVO generate3dModelUrlVO = new Generate3dModelUrlVO();
        generate3dModelUrlVO.setOutputUrl(masterpieceX.genera3dModelByMasterpieceX(imageGenerate3dDTO));
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        masterpieceMapper.saveMasterpieceInfo(personId,generate3dModelUrlVO.getOutputUrl());
        return generate3dModelUrlVO;
    }
    /**
     * 文字生成3d模型
     * @param zhiPuGenerateImageDTO 传入的文字以及需要的图片信息
     * @return 模型的url
     */
    @Override
    public Generate3dModelUrlVO generate3DModelByMeg(ZhiPuGenerateImageDTO zhiPuGenerateImageDTO) throws IOException {
        Generate3dModelUrlVO generate3dModelUrlVO = new Generate3dModelUrlVO();
        String imageUrl = zhiPuImageAndMessageApi.GenerateImage(zhiPuGenerateImageDTO);
        System.out.println("图片为："+imageUrl);
        ImageGenerate3dDTO imageGenerate3dDTO = new ImageGenerate3dDTO();
        imageGenerate3dDTO.setImageUrl(imageUrl);
        generate3dModelUrlVO.setOutputUrl(masterpieceX.genera3dModelByMasterpieceX(imageGenerate3dDTO));
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        masterpieceMapper.saveMasterpieceInfo(personId,generate3dModelUrlVO.getOutputUrl());
        return generate3dModelUrlVO;
    }
    /**
     * 根据id查询生成3d模型
     *
     * @return 模型的url
     */
    @Override
    public Generate3dModelUrlVO get3DModelById() {
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        String outUrl = masterpieceMapper.get3DModelById(personId);
        Generate3dModelUrlVO generate3dModelUrlVO = new Generate3dModelUrlVO();
        generate3dModelUrlVO.setOutputUrl(outUrl);
        return generate3dModelUrlVO;
    }
}
