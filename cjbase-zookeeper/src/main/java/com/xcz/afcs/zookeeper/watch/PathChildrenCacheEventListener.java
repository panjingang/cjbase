package com.xcz.afcs.zookeeper.watch;

import org.apache.zookeeper.data.Stat;

/**
 * Created by jingang on 2017/9/1.
 */
public interface PathChildrenCacheEventListener {

    void childPathAdded(String path, byte[] data, Stat stat);

    void childPathUpdated(String path, byte[] data, Stat stat);

    void childPathRemoved(String path, Stat stat);

}
