package org.example.biologyera.ai.masterpieceX;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/18$ 21:30$
 * @Params: $
 * @Return $
 */
@Component
public class MasterpieceXPostRequest {
    @Value("${Masterpiece-X.BEARER_TOKEN}")
    private String BEARER_TOKEN;
    /**
     * 图片生成3d模型
     * @param jsonBody 传入的json
     * @return 模型的url
     */
    public String genera3DModelByMasterpieceX(String url,String jsonBody) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(61, TimeUnit.SECONDS)  // 连接超时61秒
                .readTimeout(61, TimeUnit.SECONDS)     // 读取超时61秒
                .writeTimeout(61, TimeUnit.SECONDS)    // 写入超时61秒
                .build();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);
        System.out.println(body);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer " + BEARER_TOKEN)
                .build();
        System.out.println(request);
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "无错误信息";
                throw new IOException("请求失败，状态码: " + response.code() + "，错误信息: " + errorBody);
            }
            String responseData = response.body().string();
            System.out.println("Response: " + responseData);
            return responseData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
