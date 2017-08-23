package com.xcz.afcs.cache.redis;

import com.xcz.afcs.cache.CacheClientFactory;
import com.xcz.afcs.util.AfbpProperties;
import com.xcz.afcs.util.ValueUtil;

public class RedisClientFactory {
    
    public static final String PROP_KEY_PREFIX = CacheClientFactory.PROP_KEY_PREFIX + "redis.";
    
    public static final String PROP_KEY_CONNECTSTRING = "connectString";
    
    public static final String PROP_KEY_CONNTIMEOUT = "connTimeout";
    
    public static final String PROP_KEY_MAXTOTAL = "maxTotal";
    
    public static final String PROP_KEY_MAXIDLE = "maxIdle";
    
    public static final String PROP_KEY_MINIDLE = "minIdle";
    
    public static final String PROP_KEY_MAXWAITMILLIS = "maxWaitMillis";
    
    public static final String PROP_KEY_MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";
    
    private static RedisClientPoolConfig defaultPoolConfig;

    private static RedisClientFactory factoryInstance;

    public static RedisClientFactory getFactoryInstance() {
        if (null == factoryInstance) {
            factoryInstance = new RedisClientFactory();
        }
        return factoryInstance;
    }

    protected static RedisClientPoolConfig loadPoolConfigFromConfigUtil() {
        RedisClientPoolConfig poolConfig = new RedisClientPoolConfig();
        int connTimeout = ValueUtil.getInt(AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_CONNTIMEOUT), -1);
        if (0 <= connTimeout) {
            poolConfig.setConnTimeout(connTimeout);
        }
        int maxTotal = ValueUtil.getInt(AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_MAXTOTAL), -1);
        if (0 <= maxTotal) {
            poolConfig.setMaxTotal(maxTotal);
        }
        int maxIdle = ValueUtil.getInt(AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_MAXIDLE), -1);
        if (0 <= maxIdle) {
            poolConfig.setMaxIdle(maxIdle);
        }
        int minIdle = ValueUtil.getInt(AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_MINIDLE), -1);
        if (0 <= minIdle) {
            poolConfig.setMinIdle(minIdle);
        }
        long maxWaitMillis = ValueUtil.getLong(AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_MAXWAITMILLIS), -1);
        if (0 <= maxWaitMillis) {
            poolConfig.setMaxWaitMillis(maxWaitMillis);
        }
        long minEvictableIdleTimeMillis = ValueUtil.getLong(AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_MINEVICTABLEIDLETIMEMILLIS),
                -1);
        if (0 <= minEvictableIdleTimeMillis) {
            poolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        }
        return poolConfig;
    }

    public static RedisClient createClusterRedisClientFromConfigUtil() {
        String connectString = AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_CONNECTSTRING);
        RedisClientPoolConfig poolConfig = loadPoolConfigFromConfigUtil();
        return new ClusterRedisClient(connectString, poolConfig).init();
    }

    public static RedisClient createBasicPoolRedisClientFromConfigUtil() {
        String connectString = AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_CONNECTSTRING);
        RedisClientPoolConfig poolConfig = loadPoolConfigFromConfigUtil();
        return new BasicPoolRedisClient(connectString, poolConfig).init();
    }

    public RedisClient createBasicPoolRedisClient(String host) {
        return new BasicPoolRedisClient(host, defaultPoolConfig).init();
    }

    public RedisClient createBasicPoolRedisClient(String host, RedisClientPoolConfig poolConfig) {
        return new BasicPoolRedisClient(host, poolConfig).init();
    }

    public static RedisClientPoolConfig getDefaultPoolConfig() {
        return defaultPoolConfig;
    }
    
    public static void setDefaultPoolConfig(RedisClientPoolConfig defaultPoolConfig) {
        RedisClientFactory.defaultPoolConfig = defaultPoolConfig;
    }
    
}
