package org.example.biologyera.ai.chat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.example.biologyera.model.chat.dto.ChatRequest;
import org.example.biologyera.model.chat.po.History;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/2/18$ 11:04$
 * @Params: $
 * @Return $
 */
@Component
public class DsChat {
    @Value("${DeepSeek.url}")
    private String url;
    @Value("${DeepSeek.model}")
    private String model;
    @Value("${DeepSeek.apiKey}")
    private String apiKey;
    /**
        发送消息(非流式)
    */
    public String sendMessage(List<History> history, String question) throws IOException {
//        String jsonBody = String.valueOf(createRequestParams(question));
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + apiKey)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new IOException("Unexpected code: " + response);
//            }
//            String responseData = response.body().string();
//            System.out.println("Response: " + responseData);
//            return responseData; // 返回响应数据
//        }
        // 设置 OkHttpClient 的超时
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(61, TimeUnit.SECONDS)  // 连接超时61秒
                .readTimeout(61, TimeUnit.SECONDS)     // 读取超时61秒
                .writeTimeout(61, TimeUnit.SECONDS)    // 写入超时61秒
                .build();
        String jsonBody = String.valueOf(createRequestParams(history,question,false));
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "无错误信息";
                throw new IOException("请求失败，状态码: " + response.code() + "，错误信息: " + errorBody);
            }
            String responseData = response.body().string();
            System.out.println("Response: " + responseData);
            return responseData;
        }
    }
    /**
     发送消息(流式)
     */
    public Flux<String> send(ChatRequest chatRequest, List<History> history) throws IOException {
        WebClient client = WebClient.create("https://api.deepseek.com");
        Flux<String> flux = client.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(createRequestParams(history,chatRequest.getText(),true)) // 构建请求体
                .retrieve()
                .bodyToFlux(String.class) // 接收流式响应
                .filter(chunk -> !chunk.equals("[DONE]")) // 过滤结束标记
                .map(this::parseContent); // 解析 content 字段
        return flux;
    }
    private String parseContent(String chunk) {
        try {
            // 示例 chunk 格式：data: {"choices":[{"delta":{"content":"Hello"}}]}
            String jsonStr = chunk.replace("data: ", "");
            JsonObject json = JsonParser.parseString(jsonStr).getAsJsonObject();
            return json.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("delta")
                    .get("content").getAsString();
        } catch (Exception e) {
            return "";
        }
    }
    /**
     拼装json(实现历史多轮问答)
     */
    public JSONObject createRequestParams(List<History> history,String questions,Boolean stream) {
        JSONObject requestJson = new JSONObject();
        JSONArray messagesArray = new JSONArray();
        if (!history.isEmpty()){
            for (History h : history) {
                JSONObject userJson1 = new JSONObject();
                userJson1.put("role","user");
                userJson1.put("content",h.question);
                messagesArray.add(userJson1);
                JSONObject assistantJson = new JSONObject();
                assistantJson.put("role","assistant");
                assistantJson.put("content",h.answer);
                messagesArray.add(assistantJson);
            }
            JSONObject userJson = new JSONObject();
            userJson.put("role","user");
            userJson.put("content",questions);
            messagesArray.add(userJson);
            System.out.println(messagesArray);
        }else {
            JSONObject userJson = new JSONObject();
            userJson.put("role", "user");
            userJson.put("content", questions);
            messagesArray.add(userJson);
        }
//        JSONObject systemJson = new JSONObject();
//        systemJson.put("role", "user");
//        systemJson.put("content", history.get(0).question);
//        JSONObject userJson = new JSONObject();
//        userJson.put("role", "user");
//        userJson.put("content", questions);
//        messagesArray.add(systemJson);
//        messagesArray.add(userJson);
        requestJson.put("model", model);
        requestJson.put("messages", messagesArray);
        requestJson.put("stream", stream);
        return requestJson;
    }
}
