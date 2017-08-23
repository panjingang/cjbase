package com.xcz.afcs.cache;

import com.xcz.afcs.cache.memcached.MemcachedClientFactory;
import com.xcz.afcs.cache.redis.RedisClientFactory;
import com.xcz.afcs.util.AfbpProperties;

public class CacheClientFactory {
    
    public static final String PROP_KEY_PREFIX = "cache.";
    
    public static final String PROP_KEY_IMPLTYPE = "implType";
    
    public static CacheClient createCacheClientFromConfigUtil(String filename) {
        AfbpProperties.loadProperties(filename);
        String implType = AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_IMPLTYPE);
        if (CacheClientImplTypes.MEMCACHED.equals(implType)) {
            return MemcachedClientFactory.createSpymemcachedClientFromConfigUtil();
        } else if (CacheClientImplTypes.REDIS.equals(implType)) {
            return RedisClientFactory.createBasicPoolRedisClientFromConfigUtil();
        } else if (CacheClientImplTypes.REDIS_CLUSTER.equals(implType)) {
            return RedisClientFactory.createClusterRedisClientFromConfigUtil();
        } else {
            throw new IllegalArgumentException("invalid cacheImpl: " + implType);
        }
    }
    
}
