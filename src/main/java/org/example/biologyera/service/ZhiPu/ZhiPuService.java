package org.example.biologyera.service.ZhiPu;


import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateImageDTO;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateMessageDTO;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateVideoDTO;
import org.example.biologyera.model.ZhiPu.vo.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/3/5$ 21:01$
 * @Params: $
 * @Return $
 */
public interface ZhiPuService {
    /**
     * 用户-创建图片理解的房间
     *
     */
    void createImageUnderstandRoom();
    /**
     * 用户-文生图
     *
     * @param zhiPuGenerateImageDTO 传递的信息
     * @return url地址
     */
    ZhiPuGenerateImageVO getGenerateImageUrl(ZhiPuGenerateImageDTO zhiPuGenerateImageDTO) throws IOException;
    /**
     * 用户-图生文
     *
     * @param zhiPuGenerateMessageDTO 传递的信息
     * @return 结果
     */
    ZhiPuGenerateMessageVO getGenerateMessage(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO) throws IOException;
    /**
     * 用户-文生视频
     *
     * @param zhiPuGenerateVideoDTO 传递的信息
     * @return 结果
     */
    ZhiPuGenerateVideoVO generateVideo(ZhiPuGenerateVideoDTO zhiPuGenerateVideoDTO);
    /**
     * 用户-多轮图片理解对话
     *
     * @param zhiPuGenerateMessageDTO 传递的信息
     * @return 结果
     */
    ZhiPuGenerateMessageVO getGenerateMessageRepeatedly(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO) throws IOException;
    /**
     * 用户-获取到图片理解的聊天信息
     *
     * @return 各个房间的信息
     */
    List<ZhiPuImageMegVO> getImageRoomMegById();
    /**
     * 用户-创建视频理解的房间
     *
     */
    void createVideoUnderstandRoom();
    /**
     * 用户-获取到视频理解的聊天信息
     *
     * @return 各个房间的信息
     */
    List<ZhiPuVideoMegVO> getVideoRoomMegById();
//    /**
//     * 用户-多轮图片理解对话(流式)
//     *
//     * @param zhiPuGenerateMessageDTO 传递的信息
//     * @return 结果
//     */
//    Flux<String> getGenerateMessageRepeatedlyStream(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO) throws IOException;
//    /**
//     * 用户-图生文(文件调用)
//     *
//     * @param imageFile 传递的文件
//     * @return 结果
//     */
//    ZhiPuGenerateMessageVO getGenerateMessageByFile(MultipartFile imageFile,String message) throws IOException;
}
