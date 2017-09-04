package com.xcz.afcs.zookeeper;

import com.xcz.afcs.core.util.ConcurrentHashSet;
import com.xcz.afcs.util.IOUtil;
import com.xcz.afcs.zookeeper.watch.NodeCacheHandler;
import com.xcz.afcs.zookeeper.watch.NodeDataCacheListener;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jingang on 2017/9/1.
 */
public class ZooKeeperClient {

    private static final Logger LOG = LoggerFactory.getLogger(ZooKeeperClient.class);

    public static final char PATH_SEPARATOR_CHAR = '/';

    public static final int DEFAULT_BASE_SLEEP_TIME_MS = 1000;

    public static final int DEFAULT_MAX_RETRIES = 0;

    private ConnectionState connectionState;

    private Set<ZooKeeperClientConnectionStateListener> connectionStateListeners = new ConcurrentHashSet<>();

    private Map<String, NodeCacheHandler> nodeCacheHandlerMap = new ConcurrentHashMap();

    private CuratorFramework client;

    public ZooKeeperClient(String connectString) {
        client = CuratorFrameworkFactory.newClient(connectString, new ExponentialBackoffRetry(
                DEFAULT_BASE_SLEEP_TIME_MS, DEFAULT_MAX_RETRIES));
    }

    /**
     * 创建ZooKeeper客户端。
     * @param connectString 服务器连接串。
     * @param retryPolicy 重试策略。
     */
    public ZooKeeperClient(String connectString, RetryPolicy retryPolicy) {
        client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
    }

    /**
     * 创建ZooKeeper客户端。
     * @param connectString 服务器连接串。
     * @param baseSleepTimeMs 重试间隔时间。
     * @param maxRetries 最大重试次数。
     */
    public ZooKeeperClient(String connectString, int baseSleepTimeMs, int maxRetries) {
        client = CuratorFrameworkFactory.newClient(connectString, new ExponentialBackoffRetry(baseSleepTimeMs,
                maxRetries));
    }

    public void start() {
        // 先确认状态，如果已经启动或关闭时调用start会抛异常
        if (CuratorFrameworkState.LATENT == client.getState()) {
            client.getConnectionStateListenable().addListener(new ClientConnectionStateListener());
            client.start();
        }
    }

    public void close() {
        if (client != null) {
            client.close();
        }
    }

    public CuratorFramework getClient() {
        return client;
    }


    public void create(String path) {
        create(path, CreateMode.PERSISTENT);
    }
    /**
     * 创建指定路径，如果父路径不存在则自动创建。
     * @param path 路径。
     * @param createMode 要创建的路径的类型。
     */
    public void create(String path, CreateMode createMode) {
        if (!exists(path)) {
            try{
                client.create().creatingParentsIfNeeded().withMode(createMode).forPath(path, IOUtil.EMPTY_BYTES);
            }catch (Exception e) {
                LOG.error("create "+path+" fail", e);
            }
        }
    }

    /**
     * 删除指定路径，会递归删除其下子路径，如果路径不存在则不操作。
     * @param path 路径。
     */
    public void delete(String path) {
        // 先检查路径是否存在，如果不存在则不做操作
        if (exists(path)) {
            try {
                // 删除本路径
                client.delete().deletingChildrenIfNeeded().forPath(path);
            } catch (Exception e) {
                LOG.error("delete "+path+" fail", e);
            }
        }
    }

    public boolean exists(String path) {
        try{
            return null != client.checkExists().forPath(path);
        }catch (Exception e) {
            LOG.error("check "+path+" exists fail", e);
        }
        return false;
    }

    public String getStringData(String path) {
        if (!exists(path)) {
            return null;
        }
        try {
            byte[] bytes = client.getData().forPath(path);
            return bytes == null ? null : new String(bytes, IOUtil.CHARSET_NAME_UTF8);
        }catch (Exception e) {
            LOG.error("getStringData "+path+" fail", e);
        }
        return null;
    }

    public void setStringData(String path, String data) {
         setStringData(path, data, CreateMode.PERSISTENT);
    }

    public void setStringData(String path, String data, CreateMode createMode) {
        if (!exists(path)) {
           create(path, createMode);
        }
        try{
            client.setData().forPath(path, data.getBytes(IOUtil.CHARSET_NAME_UTF8));
        }catch (Exception e) {
            LOG.error("setStringData "+path+" fail", e);
        }
    }

    public void watchNodeData(String path, NodeDataCacheListener listener) {
        NodeCacheHandler nodeCacheHandler = getNodeCacheHandler(path);
        nodeCacheHandler.addListener(listener);
    }

    public void removeWatchNodeData(String path, NodeDataCacheListener listener) {
        NodeCacheHandler nodeCacheHandler = nodeCacheHandlerMap.get(path);
        if (null != nodeCacheHandler) {
            nodeCacheHandler.removeListener(listener);
        }
    }

    public void unregisterWatchNodeData(String path) {
        LOG.info("unregister data watch at path: " + path);
        NodeCacheHandler nodeCacheHandler = nodeCacheHandlerMap.get(path);
        if (null != nodeCacheHandler) {
            nodeCacheHandler.destroy();
            nodeCacheHandlerMap.remove(path);
        }
    }


    private NodeCacheHandler getNodeCacheHandler(String path) {
        NodeCacheHandler nodeCacheHandler = nodeCacheHandlerMap.get(path);
        if (nodeCacheHandler != null) {
            return nodeCacheHandler;
        }
        NodeCache nodeCache = new NodeCache(client, path);
        nodeCacheHandler = new NodeCacheHandler(path, nodeCache);
        nodeCacheHandler.startWatch();
        nodeCacheHandlerMap.put(path, nodeCacheHandler);
        return nodeCacheHandler;
    }

    public void addClientConnectionStateListener(ZooKeeperClientConnectionStateListener listener) {
        if (connectionStateListeners.add(listener) && CuratorFrameworkState.STARTED == client.getState()) {
            notifyConnectionStateListener(connectionState, listener);
        }
    }

    public void removeClientConnectionStateListener(ZooKeeperClientConnectionStateListener listener) {
        connectionStateListeners.remove(listener);
    }

    private void notifyConnectionStateListener(ConnectionState state, ZooKeeperClientConnectionStateListener listener) {
        if (state.isConnected()) {
            listener.notifyConnected();
        } else {
            listener.notifyDisconnected();
        }
    }

    private class ClientConnectionStateListener implements ConnectionStateListener {
        @Override
        public void stateChanged(CuratorFramework client, ConnectionState newState) {
            connectionState = newState;
            if (newState.isConnected()) {
                for (ZooKeeperClientConnectionStateListener listener : connectionStateListeners) {
                    listener.notifyConnected();
                }
            } else {
                for (ZooKeeperClientConnectionStateListener listener : connectionStateListeners) {
                    listener.notifyDisconnected();
                }
            }
        }
    }




}
