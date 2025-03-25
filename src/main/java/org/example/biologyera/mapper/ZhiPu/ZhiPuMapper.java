package org.example.biologyera.mapper.ZhiPu;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.biologyera.model.ZhiPu.po.Chat;
import org.example.biologyera.model.ZhiPu.po.History;
import org.example.biologyera.model.ZhiPu.vo.ZhiPuImageMegVO;
import org.example.biologyera.model.ZhiPu.vo.ZhiPuVideoMegVO;

import java.util.List;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/25$ 18:03$
 * @Params: $
 * @Return $
 */
@Mapper
public interface ZhiPuMapper {
    void createImageUnderstandRoom(@Param("personId") Long personId);

    List<History> getImageHistory(@Param("roomId") Integer roomId);

    void insertImageMessage(Chat chat);

    List<Integer> getImageRoomByPersonId(@Param("personId")Long personId);

    List<ZhiPuImageMegVO> getImageRoomMegById(@Param("roomIds") List<Integer> roomIds);

    List<History> getVideoHistory(@Param("roomId") Integer roomId);

    void insertVideoMessage(Chat chat);

    void createVideoUnderstandRoom(@Param("personId") Long personId);

    List<Integer> getVideoRoomByPersonId(@Param("personId") Long personId);

    List<ZhiPuVideoMegVO> getVideoRoomMegById(@Param("roomIds") List<Integer> roomIds);
}
