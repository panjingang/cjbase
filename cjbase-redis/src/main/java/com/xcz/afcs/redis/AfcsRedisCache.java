package com.xcz.afcs.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by jingang on 2017/9/12.
 */
public class AfcsRedisCache {

    @Setter
    @Getter
    private RedisTemplate<String, Object> redisTemplate;

    public AfcsRedisCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> T get(String key) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        return (T)valueOps.get(key);
    }

    public <T> void save(String key, T object, Long expireMills) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, object, expireMills, TimeUnit.MILLISECONDS);
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasHashKey(String key, String hashKey) {
       return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public void putHashValue(String key, String hashKey, Object hashValue) {
         redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    public void putAllHashValue(String key, Map<String, Object> values) {
        redisTemplate.opsForHash().putAll(key, values);
    }

    public Object getHashValue(String key, String hashKey) {
       return  redisTemplate.opsForHash().get(key, hashKey);
    }

}
