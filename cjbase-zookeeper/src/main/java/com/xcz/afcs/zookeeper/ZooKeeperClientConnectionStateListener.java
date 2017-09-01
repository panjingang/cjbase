package com.xcz.afcs.zookeeper;

/**
 * Created by jingang on 2017/9/1.
 */
public interface ZooKeeperClientConnectionStateListener {

     void notifyConnected();

     void notifyDisconnected();
}
