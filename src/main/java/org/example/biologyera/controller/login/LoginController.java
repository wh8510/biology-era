package org.example.biologyera.controller.login;

import org.example.biologyera.common.model.BaseResponse;
import org.example.biologyera.common.model.ErrorCode;
import org.example.biologyera.exception.AssertionException;
import org.example.biologyera.model.image.vo.ImageVO;
import org.example.biologyera.model.person.dto.LoginRequestDTO;
import org.example.biologyera.model.person.vo.PersonInfoVO;
import org.example.biologyera.service.login.LoginService;
import org.example.biologyera.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/3/12$ 23:27$
 * @Params: $
 * @Return $
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    /**
     * 生成图片验证码
     *
     * @return 图片详情 Vo
     */
    @GetMapping("/image")
    public BaseResponse<ImageVO> getImageMessage(){
//        throw new AssertionException(ErrorCode.OPERATION_ERROR,"sss");
        return ResultUtil.success(loginService.getImageMessage());
    }
    /**
     * 通用 - 账号密码登录
     *
     * @param loginRequestDTO 登录 dto
     * @return 用户详细 Vo
     */
    @PostMapping("/password")
    public BaseResponse<PersonInfoVO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        if (loginRequestDTO.getUsername().isEmpty()|| loginRequestDTO.getPassword().isEmpty()){
            throw new RuntimeException("数据有误");
        }
        return ResultUtil.success(loginService.loginByPassword(loginRequestDTO));
    }
}
