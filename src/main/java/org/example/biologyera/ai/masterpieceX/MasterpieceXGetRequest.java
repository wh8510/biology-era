package org.example.biologyera.ai.masterpieceX;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/18$ 21:35$
 * @Params: $
 * @Return $
 */
@Component
public class MasterpieceXGetRequest {

    @Value("${Masterpiece-X.BEARER_TOKEN}")
    private String BEARER_TOKEN;
    public String GetRequest(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(61, TimeUnit.SECONDS)  // 连接超时61秒
                .readTimeout(61, TimeUnit.SECONDS)     // 读取超时61秒
                .writeTimeout(61, TimeUnit.SECONDS)    // 写入超时61秒
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "无错误信息";
                throw new IOException("请求失败，状态码: " + response.code() + "，错误信息: " + errorBody);
            }
            String responseData = response.body().string();
            return responseData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getMessageByRequestId(String url) {
        String responseData = "";
        boolean isSuccess = false;
        System.out.println("url:"+url);
        while (!isSuccess) {
            // 发送GET请求
            responseData = GetRequest(url);
            // 解析返回的JSON数据
            JsonObject json = JsonParser.parseString(responseData).getAsJsonObject();
            // 获取task_status字段的值
            String status = json.get("status").getAsString();

            // 如果task_status为SUCCESS，则停止循环
            if ("complete".equals(status)) {
                System.out.println("任务完成，状态: complete ");
                System.out.println("json: "+json);
                isSuccess = true;
            }else if ("failed".equals(status)) {
                System.out.println("json: "+json);
                throw new RuntimeException("运行出错");
            } else {
                // 如果status不是complete，等待10秒钟后再请求
                System.out.println("任务正在处理中，等待10秒钟后重试...");
                System.out.println("json: "+json);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("线程中断，停止请求.");
                    break;
                }
            }
        }
        return responseData;
    }
}
