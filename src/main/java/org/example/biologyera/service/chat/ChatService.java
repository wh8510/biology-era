package org.example.biologyera.service.chat;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.biologyera.model.chat.dto.ChatRequest;
import org.example.biologyera.model.chat.po.Chat;
import org.example.biologyera.model.chat.vo.ChatMessageVO;
import org.example.biologyera.model.chat.vo.ChatRoomMegVO;
import org.example.biologyera.model.chat.vo.ChatVO;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

/**
* @Author: 张文化
* @Description: $TODO$
* @DateTime: 2025/2/17$ 21:27$
* @Params: $params$
* @Return $return$
*/
public interface ChatService extends IService<Chat> {
    /**
     * 进行ai问答(非流式)
     *
     * @return 数据
     */
    List<ChatVO> askQuestion(ChatRequest chatRequest) throws IOException;
    /**
     * 进行ai问答(流式)
     *
     * @return 数据
     */
    Flux<String> callOpenAIStreamAPI(ChatRequest chatRequest) throws IOException;
    /**
     * 创建房间
     *
     */
    void createAiRoom();
    /**
     * 查询房间
     *
     * @return 是否成功
     */
    List<ChatRoomMegVO> getRoomId();
    /**
     * 查询聊天室
     *
     * @return List<ChatMessageVO>
     */
    List<ChatMessageVO> getChatMegByRoomId();
}
