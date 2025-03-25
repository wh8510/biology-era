package org.example.biologyera.ai.ZhiPu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.example.biologyera.common.model.ErrorCode;
import org.example.biologyera.exception.AssertionException;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateImageDTO;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateMessageDTO;
import org.example.biologyera.model.ZhiPu.po.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/3/6$ 15:57$
 * @Params: $
 * @Return $
 */
@Component
public class ZhiPuImageAndMessageApi {
    @Autowired
    public ZhiPuRequest zhiPuRequest;
    @Value("${ZhiPu.imageModel}")
    private String imageModel;
    @Value("${ZhiPu.messageModel}")
    private String messageModel;
    @Value("${ZhiPu.imageUrl}")
    private String imageUrl;
    @Value("${ZhiPu.messageUrl}")
    private String messageUrl;

    /**
     * 用户-文生图
     *
     * @param zhiPuGenerateImageDTO 传递的信息
     * @return url地址
     */
    public String GenerateImage(ZhiPuGenerateImageDTO zhiPuGenerateImageDTO) throws IOException {
        // 发送请求并获取响应
        String response = generateImage(zhiPuGenerateImageDTO);
        System.out.println(response);
//         解析响应，假设返回的是 JSON 格式
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        System.out.println(json);
        String imageUrl = json.getAsJsonArray("data").get(0).getAsJsonObject().get("url").getAsString();
        System.out.println("Generated Image URL: " + imageUrl);
        return imageUrl;
    }

    public String generateImage(ZhiPuGenerateImageDTO zhiPuGenerateImageDTO) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", imageModel);
        jsonObject.put("prompt", zhiPuGenerateImageDTO.getMessage());
        jsonObject.put("size", zhiPuGenerateImageDTO.getSize());
        jsonObject.put("user_id", zhiPuGenerateImageDTO.getId());
        String jsonBody = String.valueOf(jsonObject);
        String requestData = zhiPuRequest.PostRequest(imageUrl, jsonBody);
        return requestData;
    }

    /**
     * 用户-图生文
     *
     * @param zhiPuGenerateMessageDTO 传递的信息
     * @return 图片信息
     */
    public String GenerateMessage(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO) throws IOException {
        // 发送请求并获取响应
        String response = generateMessage(zhiPuGenerateMessageDTO);
        System.out.println(response);
//         解析响应，假设返回的是 JSON 格式
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        System.out.println(json);
        String Meg = json.getAsJsonArray("choices").get(0).getAsJsonObject().getAsJsonObject("message").get("content").getAsString();
        System.out.println("Generated Image URL: " + imageUrl);
        return Meg;
    }

    /**
     * 发送请求给模型
     */
    private String generateMessage(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO) throws IOException {
        JSONObject contentObject = contentObject(zhiPuGenerateMessageDTO);
        //组装message部分
        JSONArray messageJsonArray = new JSONArray();
        messageJsonArray.add(contentObject);
        //组装最后的JSON
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", messageModel);
        jsonObject.put("messages", messageJsonArray);
        String jsonBody = String.valueOf(jsonObject);
        System.out.println(jsonObject);
        return zhiPuRequest.PostRequest(messageUrl, jsonBody);
    }

    public String GenerateMessageRepeatedly(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO, List<History> historyList) throws IOException {
        // 发送请求并获取响应
        String response = generateMessageRepeatedly(zhiPuGenerateMessageDTO, historyList);
        if (historyList.isEmpty()) {
            return response;
        }
//         解析响应，假设返回的是 JSON 格式
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        System.out.println(json);
        String Meg = json.getAsJsonArray("choices").get(0).getAsJsonObject().getAsJsonObject("message").get("content").getAsString();
        System.out.println("Generated Image URL: " + imageUrl);
        return Meg;
    }

    public String generateMessageRepeatedly(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO, List<History> historyList) throws IOException {
        if (historyList.isEmpty()) {
            return GenerateMessage(zhiPuGenerateMessageDTO);
        } else {
            String jsonBody = messageRepeatedly(zhiPuGenerateMessageDTO, historyList);
            return zhiPuRequest.PostRequest(messageUrl, jsonBody);
        }
    }

    /**
     * 用户-多轮图片理解对话(流式)
     *
     * @param zhiPuGenerateMessageDTO 传递的信息
     * @return 结果
     */
    public Flux<String> GetGenerateMessageRepeatedlyStream(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO, List<History> historyList) throws IOException {
        if (historyList.isEmpty()) {
            return StreamPostRequest(zhiPuGenerateMessageDTO);
        } else {
            String jsonBody = messageRepeatedly(zhiPuGenerateMessageDTO, historyList);
            Flux<String> flux = zhiPuRequest.StreamPostRequestRepeatedly(messageUrl, jsonBody);
            return flux;
        }
    }

    private Flux<String> StreamPostRequest(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO) {
        JSONObject contentObject = contentObject(zhiPuGenerateMessageDTO);
        //组装message部分
        JSONArray messageJsonArray = new JSONArray();
        messageJsonArray.add(contentObject);
        //组装最后的JSON
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", messageModel);
        jsonObject.put("messages", messageJsonArray);
        jsonObject.put("stream",true);
        String jsonBody = String.valueOf(jsonObject);
        Flux<String> flux = zhiPuRequest.StreamPostRequestRepeatedly(messageUrl, jsonBody);
        return flux;
    }

    public JSONObject contentObject(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO){
        //组装第一个type
        JSONObject content1JsonArray = new JSONObject();
        content1JsonArray.put("type", "text");
        content1JsonArray.put("text", zhiPuGenerateMessageDTO.getMessage());
        //组装第二个type
        JSONObject content2JsonArray = new JSONObject();
        JSONObject url = new JSONObject();
        //判断是视频理解还是图片理解
        if (!zhiPuGenerateMessageDTO.getImageMeg().isEmpty()) {
            content2JsonArray.put("type", "image_url");
            //组装url部分
            url.put("url", zhiPuGenerateMessageDTO.getImageMeg());
            content2JsonArray.put("image_url", url);
        } else if (!zhiPuGenerateMessageDTO.getVideoMeg().isEmpty()) {
            content2JsonArray.put("type", "video_url");
            //组装url部分
            url.put("url", zhiPuGenerateMessageDTO.getVideoMeg());
            content2JsonArray.put("video_url", url);
        } else throw new AssertionException(ErrorCode.PARAMS_ERROR,"图片/视频的输入不能同时为空");
        //组装content部分
        JSONArray contentJsonArray = new JSONArray();
        contentJsonArray.add(content1JsonArray);
        contentJsonArray.add(content2JsonArray);
        //组装message的user和content部分
        JSONObject contentObject = new JSONObject();
        contentObject.put("role", "user");
        contentObject.put("content", contentJsonArray);
        return contentObject;
    }
    public String messageRepeatedly(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO, List<History> historyList){
        JSONArray messageJsonArray = new JSONArray();
        for (History h : historyList) {
            //组装第一个type
            JSONObject content1JsonArray = new JSONObject();
            content1JsonArray.put("type", "text");
            content1JsonArray.put("text", h.getQuestion());
            //组装第二个type
            JSONObject content2JsonArray = new JSONObject();
            JSONObject url = new JSONObject();
            //判断是视频理解还是图片理解
            if (!zhiPuGenerateMessageDTO.getImageMeg().isEmpty()) {
                content2JsonArray.put("type", "image_url");
                //组装url部分
                url.put("url", h.getImageUrl());
                content2JsonArray.put("image_url", url);
            } else if (!zhiPuGenerateMessageDTO.getVideoMeg().isEmpty()) {
                content2JsonArray.put("type", "video_url");
                //组装url部分
                url.put("url", h.getVideoUrl());
                content2JsonArray.put("video_url", url);
            } else throw new RuntimeException("图片/视频的输入不能为空");
            //组装content部分
            JSONArray contentJsonArray = new JSONArray();
            contentJsonArray.add(content1JsonArray);
            contentJsonArray.add(content2JsonArray);
            //组装message的user和content部分
            JSONObject contentObject = new JSONObject();
            contentObject.put("role", "user");
            contentObject.put("content", contentJsonArray);
            //组装ai唯一的type
            JSONObject assistantContent1Object = new JSONObject();
            assistantContent1Object.put("text", h.getAnswer());
            assistantContent1Object.put("type", "text");
            //组装ai的content
            JSONArray assistantContentArray = new JSONArray();
            assistantContentArray.add(assistantContent1Object);
            //组装ai的回答
            JSONObject assistantContentObject = new JSONObject();
            assistantContentObject.put("role", "assistant");
            assistantContentObject.put("content", assistantContentArray);
            //组装message部分
            messageJsonArray.add(contentObject);
            messageJsonArray.add(assistantContentObject);
        }
        JSONObject userContentObject = contentObject(zhiPuGenerateMessageDTO);
        //组装最后的JSON
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", messageModel);
        messageJsonArray.add(userContentObject);
        jsonObject.put("messages", messageJsonArray);
        return String.valueOf(jsonObject);
    }
}
//    /**
//     *MultipartFile转换为base64
//     */
//    public String convertToBase64(MultipartFile file) throws IOException {
//        try (InputStream inputStream = new BufferedInputStream(file.getInputStream());
//             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//
//            // 关键修正：使用Encoder包装OutputStream
//            try (OutputStream base64Stream = Base64.getEncoder().wrap(outputStream)) {
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    // 将原始数据写入Base64编码流（自动编码）
//                    base64Stream.write(buffer, 0, bytesRead);
//                }
//            } // 此处自动关闭base64Stream，确保数据刷新到outputStream
//            return outputStream.toString("UTF-8");
//        }
//    }
