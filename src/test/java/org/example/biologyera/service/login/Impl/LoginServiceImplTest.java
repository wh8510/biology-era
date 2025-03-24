package org.example.biologyera.service.login.Impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/3/17$ 15:26$
 * @Params: $
 * @Return $
 */
@SpringBootTest
class LoginServiceImplTest {
    @Autowired
    private LoginServiceImpl loginService;
    @Test
    public void testImage() {
        loginService.getImageMessage();
    }
}