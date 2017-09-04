package com.xcz.afcs.lock;

import com.xcz.afcs.lock.impl.redis.RedisDistributedLockImpl;
import com.xcz.afcs.util.DateTimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jingang on 2017/9/4.
 */
public class RedisDistributedLockTest {

    RedisTemplate redisTemplate = null;

    @Before
    public void create() {
         JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
         jedisConnectionFactory.setShardInfo(new JedisShardInfo("127.0.0.1", 6379));
         jedisConnectionFactory.setPoolConfig(new JedisPoolConfig());

         redisTemplate = new RedisTemplate();
         redisTemplate.setValueSerializer(new StringRedisSerializer());
         redisTemplate.setKeySerializer(new StringRedisSerializer());
         redisTemplate.setConnectionFactory(jedisConnectionFactory);
         redisTemplate.afterPropertiesSet();
     }

     @Test
     public void lock() throws InterruptedException {
         DistributedLock distributedLock = new RedisDistributedLockImpl(redisTemplate, "cjbase.user.002");
         ExecutorService es = Executors.newFixedThreadPool(8);
         for(int i=0; i<5; i++) {
             //Thread.sleep(3000);
             es.submit(new Runnable() {
                 @Override
                 public void run() {
                     try {
                         System.out.println(DateTimeUtil.getCurrentDateStr()+" "+"===="+Thread.currentThread().getName()+" try lock");
                         boolean result = distributedLock.tryLock(5000);
                         System.out.println(DateTimeUtil.getCurrentDateStr()+" "+Thread.currentThread().getName()+" get lock "+result);
                         Thread.sleep(5000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         distributedLock.unLock();
                     }
                 }
             });
         }
         try {
             Thread.sleep(1000000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

     }





}
