package org.example.biologyera.ai.masterpieceX;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.biologyera.model.MasterpieceX.dto.ImageGenerate3dDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/3/18$ 21:45$
 * @Params: $
 * @Return $
 */
@Component
public class MasterpieceX {
    @Autowired
    private MasterpieceXPostRequest masterpieceXPostRequest;
    @Autowired
    private MasterpieceXGetRequest masterpieceXGetRequest;
    @Value("${Masterpiece-X.GenerateImageTo3D}")
    private String GENERATE_IMAGE_TO_3D;
    @Value("${Masterpiece-X.GeneratesAnImageFromATextPrompt}")
    private String GENERATE_IMAGE_FROM_A_TEXT_PROMPT;
    @Value("${Masterpiece-X.GetStatus}")
    private String GET_STATUS;
    /**
     * 图片生成3d模型
     * @param imageGenerate3dDTO 传入的图片信息
     * @return 模型的url
     */
    public String genera3dModelByMasterpieceX(ImageGenerate3dDTO imageGenerate3dDTO) {
        // 发送请求并获取响应
        String response = generate3dModel(imageGenerate3dDTO);
        System.out.println(response);
//         解析响应，假设返回的是 JSON 格式
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        System.out.println(json);
        String modelUrl = json.getAsJsonObject().getAsJsonObject("outputs").get("glb").getAsString();
        System.out.println("Generated Image URL: " + modelUrl);
        return modelUrl;
    }
    public String generate3dModel(ImageGenerate3dDTO imageGenerate3dDTO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imageRequestId", imageGenerate3dDTO.getImageRequestId());
        jsonObject.put("seed", imageGenerate3dDTO.getSeed());
        jsonObject.put("textureSize", imageGenerate3dDTO.getTextureSize());
        jsonObject.put("imageUrl", imageGenerate3dDTO.getImageUrl());
        String jsonBody = JSONObject.toJSONString(jsonObject);
        System.out.println("jsonBody为："+jsonBody);
        String requestId = JsonParser.parseString(masterpieceXPostRequest.genera3DModelByMasterpieceX(GENERATE_IMAGE_TO_3D,jsonBody)).getAsJsonObject().get("requestId").getAsString();
        System.out.println("id为："+requestId);
        String requestData = masterpieceXGetRequest.getMessageByRequestId(GET_STATUS+requestId);
        System.out.println("数据为："+requestData);
        return requestData;
    }
}
