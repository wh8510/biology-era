package org.example.biologyera.service.chat.Impl;

import org.example.biologyera.config.GetIdByHttp;
import org.example.biologyera.mapper.chat.ChatMapper;
import org.example.biologyera.model.chat.vo.ChatRoomMegVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/3/24$ 20:52$
 * @Params: $
 * @Return $
 */
@SpringBootTest
class ChatServiceImplTest {
    @Autowired
    private ChatMapper chatMapper;
    @Test
    void getChatMegByRoomId() {
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        List<ChatRoomMegVO> chatRoomMegVOS = chatMapper.getRoomId(personId);
        List<Integer> roomIds = chatRoomMegVOS.stream()
                .map(ChatRoomMegVO::getRoomId)  // 提取 roomId 字段
                .collect(Collectors.toList());  // 转换成 List<Integer>
        System.out.println(roomIds);
    }
}