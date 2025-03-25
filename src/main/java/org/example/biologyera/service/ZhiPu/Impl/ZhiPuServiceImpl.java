package org.example.biologyera.service.ZhiPu.Impl;
import org.example.biologyera.ai.ZhiPu.ZhiPuImageAndMessageApi;
import org.example.biologyera.ai.ZhiPu.ZhiPuVideoApi;
import org.example.biologyera.common.model.ErrorCode;
import org.example.biologyera.config.GetIdByHttp;
import org.example.biologyera.exception.AssertionException;
import org.example.biologyera.mapper.ZhiPu.ZhiPuMapper;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateImageDTO;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateMessageDTO;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateVideoDTO;
import org.example.biologyera.model.ZhiPu.po.Chat;
import org.example.biologyera.model.ZhiPu.po.History;
import org.example.biologyera.model.ZhiPu.vo.*;
import org.example.biologyera.service.ZhiPu.ZhiPuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.*;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/3/5$ 21:02$
 * @Params: $
 * @Return $
 */
@Service
@Transactional
public class ZhiPuServiceImpl implements ZhiPuService {
    @Autowired
    public ZhiPuMapper zhiPuMapper;
    @Autowired
    public ZhiPuImageAndMessageApi zhiPuImageAndMessageApi;
    @Autowired
    public ZhiPuVideoApi zhiPuVideoApi;
    /**
     * 用户-创建图片理解的房间
     *
     */
    @Override
    public void createImageUnderstandRoom() {
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        zhiPuMapper.createImageUnderstandRoom(personId);
    }
    /**
     * 用户-创建视频理解的房间
     *
     */
    @Override
    public void createVideoUnderstandRoom() {
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        zhiPuMapper.createVideoUnderstandRoom(personId);
    }
    /**
     * 用户-文生图
     *
     * @param zhiPuGenerateImageDTO 传递的信息
     * @return url地址
     */
    @Override
    public ZhiPuGenerateImageVO getGenerateImageUrl(ZhiPuGenerateImageDTO zhiPuGenerateImageDTO) throws IOException {
        ZhiPuGenerateImageVO zhiPuGenerateImageVO = new ZhiPuGenerateImageVO();
        zhiPuGenerateImageVO.setUrl(zhiPuImageAndMessageApi.GenerateImage(zhiPuGenerateImageDTO));
        return zhiPuGenerateImageVO;
    }
    /**
     * 用户-图生文
     *
     * @param zhiPuGenerateMessageDTO 传递的信息
     * @return 结果
     */
    @Override
    public ZhiPuGenerateMessageVO getGenerateMessage(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO) throws IOException {
        ZhiPuGenerateMessageVO zhiPuGenerateMessageVO = new ZhiPuGenerateMessageVO();
        zhiPuGenerateMessageVO.setMsg(zhiPuImageAndMessageApi.GenerateMessage(zhiPuGenerateMessageDTO));
        return zhiPuGenerateMessageVO;
    }
    /**
     * 用户-文生视频
     *
     * @param zhiPuGenerateVideoDTO 传递的信息
     * @return 结果
     */
    @Override
    public ZhiPuGenerateVideoVO generateVideo(ZhiPuGenerateVideoDTO zhiPuGenerateVideoDTO) {
        ZhiPuGenerateVideoVO zhiPuGenerateVideoVO = new ZhiPuGenerateVideoVO();
        zhiPuGenerateVideoVO.setVideo(zhiPuVideoApi.GenerateVideo(zhiPuGenerateVideoDTO));
        return zhiPuGenerateVideoVO;
    }
    /**
     * 用户-多轮图片理解对话
     *
     * @param zhiPuGenerateMessageDTO 传递的信息
     * @return 结果
     */
    @Override
    public ZhiPuGenerateMessageVO getGenerateMessageRepeatedly(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO) throws IOException {
        Chat chat = new Chat();
        chat.setImageMeg(zhiPuGenerateMessageDTO.getImageMeg());
        chat.setVideoMeg(zhiPuGenerateMessageDTO.getVideoMeg());
        chat.setQuestion(zhiPuGenerateMessageDTO.getMessage());
        chat.setRoomId(zhiPuGenerateMessageDTO.getRoomId());
        chat.setCreateTime(new Date());
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        List<History> historyList;
        List<Integer> id;
        if (zhiPuGenerateMessageDTO.getVideoMeg().isEmpty()){
            historyList = zhiPuMapper.getImageHistory(zhiPuGenerateMessageDTO.getRoomId());
            id = zhiPuMapper.getImageRoomByPersonId(personId);
        }
        else {
            historyList = zhiPuMapper.getVideoHistory(zhiPuGenerateMessageDTO.getRoomId());
            id = zhiPuMapper.getVideoRoomByPersonId(personId);
        }
        roomStatus(id,zhiPuGenerateMessageDTO.getRoomId());
        ZhiPuGenerateMessageVO zhiPuGenerateMessageVO = new ZhiPuGenerateMessageVO();
        zhiPuGenerateMessageVO.setMsg(zhiPuImageAndMessageApi.GenerateMessageRepeatedly(zhiPuGenerateMessageDTO,historyList));
        chat.setAnswer(zhiPuGenerateMessageVO.getMsg());
        if (zhiPuGenerateMessageDTO.getVideoMeg().isEmpty()) zhiPuMapper.insertImageMessage(chat);
        else zhiPuMapper.insertVideoMessage(chat);
        return zhiPuGenerateMessageVO;
    }
    /**
     * 用户-获取到图片理解的聊天信息
     *
     * @return 各个房间的信息
     */
    @Override
    public List<ZhiPuImageMegVO> getImageRoomMegById() {
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        List<Integer> roomIds = zhiPuMapper.getImageRoomByPersonId(personId);
        return zhiPuMapper.getImageRoomMegById(roomIds);
    }
    /**
     * 用户-获取到视频理解的聊天信息
     *
     * @return 各个房间的信息
     */
    @Override
    public List<ZhiPuVideoMegVO> getVideoRoomMegById() {
        Long personId = GetIdByHttp.GetIdByHttpServletRequest();
        List<Integer> roomIds = zhiPuMapper.getVideoRoomByPersonId(personId);
        return zhiPuMapper.getVideoRoomMegById(roomIds);
    }
    public void roomStatus(List<Integer> id,Integer roomId){
        boolean isIdFound = false;
        for (Integer i : id) {
            if (Objects.equals(i, roomId)){
                isIdFound = true;
                break;
            }
        }
        if (!isIdFound){
            throw new AssertionException(ErrorCode.OPERATION_ERROR,"房间不存在");
        }
    }
//    /**
//     * 用户-多轮图片理解对话(流式)
//     *
//     * @param zhiPuGenerateMessageDTO 传递的信息
//     * @return 结果
//     */
//    @Override
//    public Flux<String> getGenerateMessageRepeatedlyStream(ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO) throws IOException {
//        Chat chat = new Chat();
//        chat.setVideoMeg(zhiPuGenerateMessageDTO.getVideoMeg());
//        chat.setImageMeg(zhiPuGenerateMessageDTO.getImageMeg());
//        chat.setQuestion(zhiPuGenerateMessageDTO.getMessage());
//        chat.setRoomId(zhiPuGenerateMessageDTO.getRoomId());
//        chat.setCreateTime(new Date());
//        List<History> historyList = zhiPuMapper.getHistory(zhiPuGenerateMessageDTO.getRoomId());
//        Flux<String> flux = zhiPuImageAndMessageApi.GetGenerateMessageRepeatedlyStream(zhiPuGenerateMessageDTO,historyList);
//// 通过 subscribe 异步处理数据流，拼接答案
//        StringBuilder fullAnswer = new StringBuilder();
//        // 拼接流中的内容
//        flux.doOnTerminate(() -> {
//                    // 执行一些清理工作
//                })
////                .map(this::process)
//                .subscribe(
//                        fullAnswer::append, // 正常的数据处理
//                        error -> {
//                            System.err.println("Error: " + error.getMessage());
//                            // 如果发生错误，可以通过抛出异常来阻止数据库保存
//                            throw new RuntimeException("Stream encountered an error");
//                        },
//                        () -> {
//                            // 流处理完后，插入数据库
//                            chat.setAnswer(fullAnswer.toString());
//                            System.out.println(fullAnswer);
//                            zhiPuMapper.insert(chat); // 将拼接后的答案保存到数据库
//                        }
//                );
//        return flux;
//    }
//    /**
//     * 用户-图生文(文件调用)
//     *
//     * @param imageFile 传递的文件
//     * @return 结果
//     */
//    @Override
//    public ZhiPuGenerateMessageVO getGenerateMessageByFile(MultipartFile imageFile,String message) throws IOException {
//        ZhiPuGenerateMessageVO zhiPuGenerateMessageVO = new ZhiPuGenerateMessageVO();
//        zhiPuGenerateMessageVO.setMsg(zhiPuApi.GenerateMessage(new ZhiPuGenerateMessageDTO(),imageFile));
//        return zhiPuGenerateMessageVO;
//    }
}
