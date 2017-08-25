package com.xcz.afcs.lock.impl;

import com.xcz.afcs.lock.DistributedLock;

import java.util.concurrent.TimeUnit;

/**
 * Created by mac on 2017/8/24.
 */
public class ZookeeperDistributedLockImpl implements DistributedLock{

    @Override
    public boolean tryLock(String key, long timeout, TimeUnit unit) {
        return false;
    }

    @Override
    public void unLock(String key) {

    }
}
