package org.example.biologyera.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/13$ 21:09$
 * @Params: $
 * @Return $
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);
        // 配置 字符串序列化即可；
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        template.setDefaultSerializer(stringRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

}


