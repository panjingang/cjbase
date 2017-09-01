package com.xcz.afcs.lock.impl.zookeeper;

import com.xcz.afcs.lock.DistributedLock;
import com.xcz.afcs.zookeeper.ZooKeeperClient;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by mac on 2017/8/24.
 */
public class ZookeeperDistributedLockImpl implements DistributedLock{

    private static Logger LOG = LoggerFactory.getLogger(ZookeeperDistributedLockImpl.class);

    private InterProcessMutex mutex;

    public ZookeeperDistributedLockImpl(ZooKeeperClient zkClient, String path) {
        mutex = new InterProcessMutex(zkClient.getClient(), path);
    }

    @Override
    public boolean tryLock(long waitLockMills){
        try {
            if (mutex.acquire(waitLockMills, TimeUnit.MILLISECONDS)) {
                return true;
            }
        }catch (Exception e) {
            LOG.error("tryLock Fail", e);
        }
        return false;
    }

    @Override
    public boolean unLock() {
        try {
            if (mutex.isAcquiredInThisProcess()) {
                mutex.release();
                return true;
            }
        } catch (Exception e) {
            LOG.error("unLock Fail", e);
        }
        return false;
    }
}
