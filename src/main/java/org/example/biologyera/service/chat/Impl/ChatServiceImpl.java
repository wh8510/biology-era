package org.example.biologyera.service.chat.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.biologyera.ai.chat.DsChat;
import org.example.biologyera.common.model.ErrorCode;
import org.example.biologyera.config.GetIdByHttp;
import org.example.biologyera.exception.AssertionException;
import org.example.biologyera.mapper.chat.ChatMapper;
import org.example.biologyera.model.chat.dto.ChatRequest;
import org.example.biologyera.model.chat.po.Chat;
import org.example.biologyera.model.chat.po.History;
import org.example.biologyera.model.chat.vo.ChatMessageVO;
import org.example.biologyera.model.chat.vo.ChatRoomMegVO;
import org.example.biologyera.model.chat.vo.ChatVO;
import org.example.biologyera.service.Redis.Impl.RedisTokenServiceImpl;
import org.example.biologyera.service.chat.ChatService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
* @Author: 张文化
* @Description: $TODO$
* @DateTime: 2025/2/17$ 21:27$
* @Params: $params$
* @Return $return$
*/
@Service
@Transactional
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private DsChat dsChat;
    /**
     * 进行ai问答(非流式)
     */
    @Override
    public List<ChatVO> askQuestion(ChatRequest chatRequest) throws IOException {
        Integer roomId = chatRequest.getRoomId();
        String question = chatRequest.getText();
        Chat chat = new Chat();
        chat.setQuestion(question);
        chat.setRoomId(roomId);
        chat.setCreateTime(new Date());
        List<History> history = history(chatRequest.getRoomId());
        //获取ai回答的答案
        String answer = aiAnswer(history,question);
        chat.setAnswer(answer);
        chatMapper.insertMessage(chat);
        List<ChatVO> chatVO = chatMapper.selectChatList(roomId);
        return chatVO;
    }
    /**
     * 进行ai问答(流式)
     */
    @Override
    public Flux<String> callOpenAIStreamAPI(ChatRequest chatRequest) throws IOException {
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        Integer roomId = chatRequest.getRoomId();
        String question = chatRequest.getText();
        List<Integer> Id = chatMapper.getIdByPersonId(personId);
        boolean isIdFound = false;
        for (Integer i : Id) {
            if (Objects.equals(i, roomId)){
                isIdFound = true;
                break;
            }
        }
        if (!isIdFound){
            throw new AssertionException(ErrorCode.OPERATION_ERROR,"房间不存在");
        }
        Chat chat = new Chat();
        chat.setQuestion(question);
        chat.setRoomId(roomId);
        chat.setCreateTime(new Date());
        Flux<String> flux = dsChat.send(chatRequest,history(chatRequest.getRoomId()));
// 通过 subscribe 异步处理数据流，拼接答案
        StringBuilder fullAnswer = new StringBuilder();
        // 拼接流中的内容
        flux.doOnTerminate(() -> {
                    // 执行一些清理工作
                })
//                .map(this::process)
                .subscribe(
                        fullAnswer::append, // 正常的数据处理
                        error -> {
                            System.err.println("Error: " + error.getMessage());
                            // 如果发生错误，可以通过抛出异常来阻止数据库保存
                            throw new AssertionException(ErrorCode.OPERATION_ERROR,"Stream encountered an error");
                        },
                        () -> {
                            // 流处理完后，插入数据库
                            chat.setAnswer(fullAnswer.toString());
                            System.out.println(fullAnswer);
                                chatMapper.insertMessage(chat); // 将拼接后的答案保存到数据库
                        }
                );
        return flux;
    }
    /**
     * 创建房间
     */
    @Override
    public void createAiRoom() {
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        chatMapper.createAiRoom(personId);
    }
    /**
     * 查询房间
     *
     * @return 是否成功
     */
    @Override
    public List<ChatRoomMegVO> getRoomId() {
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        return chatMapper.getRoomId(personId);
    }
    /**
     * 查询聊天室
     *
     * @return List<ChatMessageVO>
     */
    @Override
    public List<ChatMessageVO> getChatMegByRoomId() {
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        List<ChatRoomMegVO> chatRoomMegVOS = chatMapper.getRoomId(personId);
        List<Integer> roomIds = chatRoomMegVOS.stream()
                .map(ChatRoomMegVO::getRoomId)  // 提取 roomId 字段
                .collect(Collectors.toList());  // 转换成 List<Integer>
        System.out.println(roomIds.get(0));
        System.out.println(roomIds.get(1));
        System.out.println(roomIds.get(2));
        List<ChatMessageVO> chatMessageVOS = chatMapper.getChatMegByRoomId(roomIds);
        System.out.println(chatMessageVOS.toString());
        return chatMessageVOS;
    }

    /**
     * 进行ai请求（非流式）
     */
    public String aiAnswer(List<History> history,String question) throws IOException {
        String s = dsChat.sendMessage(history,question);
        if (s.isEmpty()){
            throw  new RuntimeException("你的请求超时了！！！");
        }
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONArray choicesArray = jsonObject.getJSONArray("choices");
        JSONObject message = choicesArray.getJSONObject(0).getJSONObject("message");
        String answer = message.getString("content");
        System.out.println(answer+"张文化666");
        return answer;
    }
    /**
     * 获取历史对话
     */
    public List<History> history(Integer roomId) throws IOException {
        List<History> history = chatMapper.getHistory(roomId);
        System.out.println(history);
        return history;
    }
//    /**
//     * 对数据处理
//     */
//    public String process(String item) {
//        return item.toUpperCase();  // 将字符串转换为大写
//    }

}
