package com.xcz.afcs.lock.impl.redis;

import com.xcz.afcs.lock.DistributedLock;
import redis.clients.jedis.Jedis;


/**
 * Created by mac on 2017/8/24.
 */
public class RedisDistributedLockImpl implements DistributedLock {

    private static final long INTERVAL_SLEEP_MILL_TIME = 50;

    private static final long DEFAULT_EXPIRE_MILL_TIME = 5000;

    private Thread exclusiveOwnerThread = null;

    private Jedis jedis;

    private String key;

    private long keyExpireMills;

    public RedisDistributedLockImpl(Jedis jedis, String key) {
        this(jedis, key, DEFAULT_EXPIRE_MILL_TIME);
    }

    public RedisDistributedLockImpl(Jedis jedis, String key, long keyExpireMills) {
        this.jedis = jedis;
        this.key   = key;
        this.keyExpireMills = keyExpireMills;
    }

    @Override
    public boolean tryLock(long waitLockMills) throws InterruptedException{
        if (waitLockMills < 1 || keyExpireMills < 1) {
            return false;
        }
        Thread currentThread = Thread.currentThread();
        String threadId  = String.valueOf(currentThread.getId());
        int expireSec = (int)keyExpireMills / 1000 ;
        long timeout = waitLockMills;
        while (timeout >= 0) {
            //未被其他线程使用，占用此锁，并设置失效时间
            if (jedis.setnx(key, threadId  ) == 1) {
                jedis.expire(key, expireSec);
                exclusiveOwnerThread = currentThread;
                return true;
            }
            //设置setnx后，reids crash 会导致expire未设置成功
            long ttl = jedis.ttl(key);
            if (ttl < 0) {
                jedis.setex(key, expireSec, threadId  );
                exclusiveOwnerThread = currentThread;
                return true;
            }
            Thread.sleep(INTERVAL_SLEEP_MILL_TIME);
            timeout -=  INTERVAL_SLEEP_MILL_TIME;
        }
        return false;
    }

    @Override
    public boolean unLock() {
        String currentThreadId = String.valueOf(Thread.currentThread().getId());
        String value = jedis.get(key);
        if (currentThreadId.equals(value)) {
            jedis.del(key);
            return true;
        }
        return false;
    }

}
