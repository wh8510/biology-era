package org.example.biologyera.service.Redis;

import org.example.biologyera.model.person.po.PersonToken;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/13$ 00:36$
 * @Params: $
 * @Return $
 */
public interface RedisTokenService {
    /**
     * 保存token到redis
     *
     * @param personToken token类
     */
     void saveTokenByRedis(PersonToken personToken);
}
