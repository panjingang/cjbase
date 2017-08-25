package com.xcz.afcs.lock;

import java.util.concurrent.TimeUnit;

/**
 * Created by mac on 2017/8/24.
 */
public interface DistributedLock {

    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    boolean tryLock(String key, long timeout, TimeUnit unit);


    /**
     * 释放锁
     * @param key
     */
    void unLock(String key);



}
