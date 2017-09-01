package com.xcz.afcs.zookeeper.watch;

/**
 * Created by jingang on 2017/9/1.
 */
public interface NodeDataCacheListener {
    /**
     * 节点有变化，节点增删或数据更新。
     * @param path 节点路径。
     * @param data 当前数据，如果为null表示节点不存在（或获取数据发生异常）。
     */
    void nodeChanged(String path, byte[] data);
}
