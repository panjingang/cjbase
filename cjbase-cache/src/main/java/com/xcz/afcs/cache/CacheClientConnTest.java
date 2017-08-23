package com.xcz.afcs.cache;

import com.xcz.afcs.cache.memcached.MemcachedClientFactory;
import com.xcz.afcs.cache.redis.RedisClient;
import com.xcz.afcs.cache.redis.RedisClientFactory;
import org.apache.commons.lang3.StringUtils;

public class CacheClientConnTest {
    
    private static String testKey = CacheClientConnTest.class.getName();
    
    private static String testValue = testKey;
    
    public static void main(String[] args) {
        String type = System.getProperty("type");
        if (StringUtils.isBlank(type)) {
            System.err.println("type is required");
            return;
        }
        if ("redis".equals(type)) {
            testRedisClient();
        } else if ("memcached".equals(type)) {
            testMemcachedClient();
        } else {
            System.err.println("invalid type: " + type + ", cadidates are: redis, memcached");
        }
    }
    
    private static void testRedisClient() {
        String host = System.getProperty("host");
        if (StringUtils.isBlank(host)) {
            System.err.println("host is required");
            return;
        }
        String portString = System.getProperty(host, String.valueOf(RedisClient.DEFAULT_PORT));
        int port = RedisClient.DEFAULT_PORT;
        try {
            port = Integer.parseInt(portString);
        } catch (Exception e) {
            System.err.println("invalid port： " + portString + ", use default: " + port);
        }
        System.out.println("host: '" + host + "'");
        System.out.println("port: '" + port + "'");
        System.out.println();
        String connectString = host + ":" + port;
        CacheClient cacheClient = new RedisClientFactory().createBasicPoolRedisClient(connectString);
        testValue(cacheClient);
    }

    private static void testMemcachedClient() {
        String connectString = System.getProperty("connectString");
        if (StringUtils.isBlank(connectString)) {
            System.err.println("connectString is required");
            return;
        }
        String username = System.getProperty("username");
        String password = System.getProperty("password");
        System.out.println("connectString: '" + connectString + "'");
        System.out.println("username: '" + username + "'");
        System.out.println("password: '" + password + "'");
        System.out.println();
        CacheClient cacheClient = new MemcachedClientFactory().createSpymemcachedClient(connectString, username,
                password);
        testValue(cacheClient);
    }

    private static void testValue(CacheClient cacheClient) {
        try {
            cacheClient.set(testKey, testValue);
            String value = cacheClient.get(testKey);
            System.out.println("testValue: " + value);
            cacheClient.delete(testKey);
            System.out.println("test pass");
            System.exit(0);
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println("test fail");
            System.exit(1);
        }
    }
    
}
