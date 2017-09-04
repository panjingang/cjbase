package com.xcz.afcs.zookeeper.watch;

import com.xcz.afcs.core.util.ConcurrentHashSet;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by jingang on 2017/9/1.
 */
public class NodeCacheHandler implements NodeCacheListener {

    private static Logger LOG = LoggerFactory.getLogger(NodeCacheHandler.class);

    private Set<NodeDataCacheListener> registeredListeners = new ConcurrentHashSet();

    private final String path;

    private final NodeCache nodeCache;

    public NodeCacheHandler(String path, NodeCache nodeCache) {
        this.path = path;
        this.nodeCache = nodeCache;
        this.nodeCache.getListenable().addListener(this);
    }

    public void startWatch() {
        try {
            nodeCache.start(true);
        }catch (Exception e) {
            LOG.error("startWatch fail", e);
        }
    }

    public void stopWatch() {
        try {
            nodeCache.close();
        }catch (Exception e) {
            LOG.error("stopWatch fail", e);
        }
    }

    public void destroy() {
        stopWatch();
        clearListener();
    }

    public void clearListener() {
        registeredListeners.clear();
        LOG.info("listeners cleared from nodeWatching " + path);
    }

    public byte[] getData() {
        ChildData cdata = nodeCache.getCurrentData();
        return null == cdata ? null : cdata.getData();
    }

    @Override
    public void nodeChanged() throws Exception {
        final byte[] data = getData();
        for (NodeDataCacheListener listener : registeredListeners) {
            listener.nodeChanged(path, data);
        }
    }

    public void addListener(NodeDataCacheListener listener) {
        if (registeredListeners.add(listener)) {
            byte[] bytes = getData();
            listener.nodeChanged(path, bytes);
        }
    }

    public void removeListener(NodeDataCacheListener listener) {
        registeredListeners.remove(listener);
    }

    @Override
    public String toString() {
        return "NodeCacheHandler [path=" + path + "]";
    }

}
