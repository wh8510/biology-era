package org.example.biologyera.mapper.chat;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.biologyera.model.chat.po.Chat;
import org.example.biologyera.model.chat.po.History;
import org.example.biologyera.model.chat.vo.ChatMessageVO;
import org.example.biologyera.model.chat.vo.ChatRoomMegVO;
import org.example.biologyera.model.chat.vo.ChatVO;

import java.util.List;

/**
* @Author: 张文化
* @Description: $TODO$
* @DateTime: 2025/2/17$ 21:27$
* @Params: $params$
* @Return $return$
*/
@Mapper
public interface ChatMapper extends BaseMapper<org.example.biologyera.model.chat.po.Chat> {

    List<ChatVO> selectChatList(Integer roomId);

    List<History> getHistory(Integer roomId);

    void createAiRoom(Long personId);

    List<Integer> getIdByPersonId(Long personId);

    void insertMessage(Chat chat);

    List<ChatRoomMegVO> getRoomId(Long personId);

    List<ChatMessageVO> getChatMegByRoomId(@Param("roomIds") List<Integer> roomIds);
}