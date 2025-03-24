package org.example.biologyera.service.Redis.Impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.example.biologyera.model.person.po.PersonToken;
import org.example.biologyera.service.Redis.RedisTokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/13$ 00:46$
 * @Params: $
 * @Return $
 */
@Repository
@RequiredArgsConstructor
public class RedisTokenServiceImpl implements RedisTokenService {
    private final RedisTemplate<Object, Object> redisTemplate;
    private final static String TABLE_NAME = "PersonToken";

    private final static String TABLE_NAME_INDEX_PERSON_ID = "PersonTokenIndexPersonId";
    /**
     * 保存token到redis
     *
     * @param personToken token类
     */
    @Override
    public void saveTokenByRedis(PersonToken personToken) {
        String valJson = JSONUtil.toJsonStr(personToken);
        String token = getPersonTokenById(personToken.getPersonId());
        if (ObjectUtil.isNotNull(token)) {
            redisTemplate.delete(TABLE_NAME + ":" + token);
        }
        System.out.println(valJson);
        redisTemplate.opsForValue().set(TABLE_NAME + ":" + personToken.getToken(), valJson);
        redisTemplate.opsForValue().set(TABLE_NAME_INDEX_PERSON_ID + ":" + personToken.getPersonId(), valJson);
    }

    /**
     * 通过id获取token
     *
     * @param personId id
     */
    public String getPersonTokenById(Long personId) {
        Object value = redisTemplate.opsForValue().get(TABLE_NAME_INDEX_PERSON_ID + ":" + personId);
        PersonToken personToken = JSONUtil.toBean((String) value, PersonToken.class);
        if (ObjectUtil.isNull(personToken)) {
            return null;
        }
        return personToken.getToken();
    }
    /**
     * 通过token获取id
     *
     * @param token token
     */
    public Long getIdByPersonToken(String token) {
        Object value = redisTemplate.opsForValue().get(TABLE_NAME + ":" + token);
        PersonToken personToken = JSONUtil.toBean((String) value, PersonToken.class);
        if (ObjectUtil.isNull(personToken)) {
            return null;
        }
        return personToken.getPersonId();
    }
}
