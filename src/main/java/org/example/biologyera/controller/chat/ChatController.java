package org.example.biologyera.controller.chat;

import org.example.biologyera.common.model.BaseResponse;
import org.example.biologyera.model.chat.dto.ChatRequest;
import org.example.biologyera.model.chat.vo.ChatMessageVO;
import org.example.biologyera.model.chat.vo.ChatRoomMegVO;
import org.example.biologyera.model.chat.vo.ChatVO;
import org.example.biologyera.service.chat.ChatService;
import org.example.biologyera.util.ResultUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

/**
* ChatController控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/chat")
public class ChatController {
/**
* 服务对象
*/
    @Autowired
    private ChatService chatService;

    /**
    * 进行ai问答(非流式)
    *
    * @return 数据
    */
    @PostMapping("/ai")
    public BaseResponse<List<ChatVO>> askQuestion(@RequestBody ChatRequest chatRequest) throws IOException {

        return ResultUtil.success(chatService.askQuestion(chatRequest));
    }
    /**
     * 进行ai问答(流式)
     *
     * @return 数据
     */
    @PostMapping(value = "/ai/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamChat(@RequestBody ChatRequest chatRequest) throws IOException {
        SseEmitter emitter = new SseEmitter(600_000L); // 超时时间 600 秒
        // 调用 OpenAI 流式 API
        Flux<String> openAiStream = chatService.callOpenAIStreamAPI(chatRequest);
        // 将 Flux 流绑定到 SseEmitter
        openAiStream.subscribe(
                content -> {
                    try {
                        emitter.send(content);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, // 逐块发送
                emitter::completeWithError,       // 错误处理
                emitter::complete                 // 完成时关闭
        );
        return emitter;
    }
    /**
     * 创建房间
     *
     * @return 是否成功
     */
    @GetMapping("/ai/createRoomId")
    public BaseResponse<String> createAiRoom() throws IOException {
        chatService.createAiRoom();
        return ResultUtil.success("添加成功！");
    }
    /**
     * 查询房间
     *
     * @return 是否成功
     */
    @GetMapping("/ai/getRoomId")
    public BaseResponse<List<ChatRoomMegVO>> getRoomId() throws IOException {
        return ResultUtil.success(chatService.getRoomId());
    }
    /**
     * 查询聊天室以及信息
     *
     * @return List<ChatMessageVO>
     */
    @GetMapping("/ai/getChatMegByRoomId")
    public BaseResponse<List<ChatMessageVO>> getChatMegByRoomId(){
        return ResultUtil.success(chatService.getChatMegByRoomId());
    }
//    private Flux<String> callOpenAIStreamAPI(String userMessage) {
//        WebClient client = WebClient.create("https://api.openai.com");
//
//        return client.post()
//                .uri("https://api.deepseek.com/chat/completions")
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + "sk-674fa5a88e6d4783b98f972e77c7e10d")
//                .bodyValue(buildRequestJson(userMessage)) // 构建请求体
//                .retrieve()
//                .bodyToFlux(String.class) // 接收流式响应
//                .filter(chunk -> !chunk.equals("[DONE]")) // 过滤结束标记
//                .map(this::parseContent); // 解析 content 字段
//    }

    // 构建 OpenAI 请求体（含流式参数）
//    private String buildRequestJson(String userMessage) {
//        return String.format(
//                "{ " +
//                        "  \"model\": \"deepseek-chat\", " +
//                        "  \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], " +
//                        "  \"stream\": true " +
//                        "}",
//                userMessage.replace("\"", "\\\"") // 处理用户输入中的双引号 (防止 JSON 格式破坏)
//        );
//    }

    // 解析 OpenAI 返回的 chunk 数据
//    private String parseContent(String chunk) {
//        try {
//            // 示例 chunk 格式：data: {"choices":[{"delta":{"content":"Hello"}}]}
//            String jsonStr = chunk.replace("data: ", "");
//            JsonObject json = JsonParser.parseString(jsonStr).getAsJsonObject();
//            return json.getAsJsonArray("choices")
//                    .get(0).getAsJsonObject()
//                    .getAsJsonObject("delta")
//                    .get("content").getAsString();
//        } catch (Exception e) {
//            return "";
//        }
//    }

}
