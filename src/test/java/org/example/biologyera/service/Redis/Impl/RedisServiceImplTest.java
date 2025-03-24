package org.example.biologyera.service.Redis.Impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/3/13$ 21:03$
 * @Params: $
 * @Return $
 */
@SpringBootTest
class RedisServiceImplTest {
    @Autowired
    private RedisTokenServiceImpl redisService;
    @Test
    public void test() {
        System.out.println(redisService.getIdByPersonToken("4767fa338e47477cb955a87a01949dc7"));
        System.out.println(redisService.getPersonTokenById(1001L));
    }
}