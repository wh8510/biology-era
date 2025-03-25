package org.example.biologyera.controller.ZhiPu;

import org.example.biologyera.common.model.BaseResponse;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateImageDTO;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateMessageDTO;
import org.example.biologyera.model.ZhiPu.dto.ZhiPuGenerateVideoDTO;
import org.example.biologyera.model.ZhiPu.vo.*;
import org.example.biologyera.service.ZhiPu.ZhiPuService;
import org.example.biologyera.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
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
@RestController
@RequestMapping("/ZhiPu")
public class ZhiPuController {
    @Autowired
    public ZhiPuService zhiPuService;
    /**
     * 用户-创建图片理解的房间
     *
     * @return 结果
     */
    @GetMapping("/createRoom/ImageUnder")
    public BaseResponse<String> createImageUnderstandRoom() throws IOException {
        zhiPuService.createImageUnderstandRoom();
        return ResultUtil.success("添加成功");
    }
    /**
     * 用户-创建视频理解的房间
     *
     * @return 结果
     */
    @GetMapping("/createRoom/VideoUnder")
    public BaseResponse<String> createVideoUnderstandRoom() throws IOException {
        zhiPuService.createVideoUnderstandRoom();
        return ResultUtil.success("添加成功");
    }
    /**
     * 用户-文生图
     *
     * @param zhiPuGenerateImageDTO 传递的信息
     * @return 结果
     */
    @GetMapping("/generateImage")
    public BaseResponse<ZhiPuGenerateImageVO> generateImage(@RequestBody ZhiPuGenerateImageDTO zhiPuGenerateImageDTO) throws IOException {
        ZhiPuGenerateImageVO zhiPuGenerateImageVO = zhiPuService.getGenerateImageUrl(zhiPuGenerateImageDTO);
        return ResultUtil.success(zhiPuGenerateImageVO);
    }
    /**
     * 用户-图、视频生文(url或者base64通用)
     *
     * @param zhiPuGenerateMessageDTO 传递的信息
     * @return 结果
     */
    @GetMapping("/generateMessage")
    public BaseResponse<ZhiPuGenerateMessageVO> generateMessage(@RequestBody ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO) throws IOException {
        ZhiPuGenerateMessageVO zhiPuGenerateMessageVO = zhiPuService.getGenerateMessage(zhiPuGenerateMessageDTO);
        return ResultUtil.success(zhiPuGenerateMessageVO);
    }
    /**
     * 用户-文生视频
     *
     * @param zhiPuGenerateVideoDTO 传递的信息
     * @return 结果
     */
    @GetMapping("/generateVideo")
    public BaseResponse<ZhiPuGenerateVideoVO> generateVideo(@RequestBody ZhiPuGenerateVideoDTO zhiPuGenerateVideoDTO) throws IOException {
        ZhiPuGenerateVideoVO zhiPuGenerateVideoVO = zhiPuService.generateVideo(zhiPuGenerateVideoDTO);
        return ResultUtil.success(zhiPuGenerateVideoVO);
    }
    /**
     * 用户-多轮图片理解对话
     *
     * @param zhiPuGenerateMessageDTO 传递的信息
     * @return 结果
     */
    @GetMapping("/generateImageRepeatedly")
    public  BaseResponse<ZhiPuGenerateMessageVO> generateImage(@RequestBody ZhiPuGenerateMessageDTO zhiPuGenerateMessageDTO) throws IOException {
        ZhiPuGenerateMessageVO zhiPuGenerateMessageVO = zhiPuService.getGenerateMessageRepeatedly(zhiPuGenerateMessageDTO);
        return ResultUtil.success(zhiPuGenerateMessageVO);
    }
    /**
     * 用户-获取到图片理解的聊天信息
     *
     * @return 各个房间的信息
     */
    @PostMapping("/getImageRoomMeg")
    public BaseResponse<List<ZhiPuImageMegVO>> getImageRoomMegById(){
        return ResultUtil.success(zhiPuService.getImageRoomMegById());
    }
    /**
     * 用户-获取到视频理解的聊天信息
     *
     * @return 各个房间的信息
     */
    @PostMapping("/getVideoRoomMeg")
    public BaseResponse<List<ZhiPuVideoMegVO>> getVideoRoomMegById(){
        return ResultUtil.success(zhiPuService.getVideoRoomMegById());
    }
}