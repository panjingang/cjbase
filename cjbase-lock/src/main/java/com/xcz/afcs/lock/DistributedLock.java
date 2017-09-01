package com.xcz.afcs.lock;

/**
 * Created by mac on 2017/8/24.
 */
public interface DistributedLock {

    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
     * @param waitLockMills
     * @return
     */
     boolean tryLock(long waitLockMills) throws InterruptedException ;


    /**
     * 释放锁
     */
    boolean unLock();

}
