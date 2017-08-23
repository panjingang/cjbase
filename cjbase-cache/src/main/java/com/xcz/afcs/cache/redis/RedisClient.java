package com.xcz.afcs.cache.redis;

import com.xcz.afcs.cache.CacheClient;
import redis.clients.jedis.Protocol;

public interface RedisClient extends CacheClient {
    
    public static final int DEFAULT_PORT = Protocol.DEFAULT_PORT;;
    
}
