package com.xcz.afcs.zookeeper.watch;

import com.xcz.afcs.core.util.ConcurrentHashSet;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jingang on 2017/9/1.
 */
public class PathChildrenCacheHandler implements PathChildrenCacheListener {
    private static Logger LOG = LoggerFactory.getLogger(NodeCacheHandler.class);

    private Set<PathChildrenCacheEventListener> registeredListeners = new ConcurrentHashSet();

    private final String path;

    private final PathChildrenCache pathChildrenCache;

    private Map<String, PathChildrenCacheHandler> childHandlers = new ConcurrentHashMap();

    public PathChildrenCacheHandler(String path, PathChildrenCache pathChildrenCache) {
        this.path = path;
        this.pathChildrenCache = pathChildrenCache;
        this.pathChildrenCache.getListenable().addListener(this);
    }

    public void startWatch() {
        try {
            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        }catch (Exception e) {
            LOG.error("startWatch fail", e);
        }
    }

    public void stopWatch() {
        try {
            pathChildrenCache.close();
        }catch (Exception e) {
            LOG.error("stopWatch fail", e);
        }
    }

    public void destroy() {
        stopWatch();
        registeredListeners.clear();
    }

    private void addChildPath(String childPath) {

    }

     private void removeChildHandler(String childPath) {
         PathChildrenCacheHandler childHandler = childHandlers.remove(childPath);
         if (null != childHandler) {
             childHandler.destroy();
         }
     }

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
        ChildData cdata = event.getData();
        String path = cdata.getPath();
        Stat stat   = cdata.getStat();
        byte[] data = cdata.getData();
        PathChildrenCacheEvent.Type type = event.getType();
        switch (type) {
            case CHILD_ADDED:
                 notifyAdded(path, data, stat);
                 addChildPath(path);
                 break;
            case CHILD_UPDATED:
                notifyUpdated(path, data, stat);
                break;
            case CHILD_REMOVED:
                notifyRemoved(path, stat);
                removeChildHandler(path);
                break;
            default: break;
        }
    }


    private void notifyAdded(final String path, final byte[] data, final Stat stat) {
        for (PathChildrenCacheEventListener listener : registeredListeners) {
            listener.childPathAdded(path, data, stat);
        }
    }

    private void notifyRemoved(final String path, final Stat stat) {
        for (PathChildrenCacheEventListener listener : registeredListeners) {
            listener.childPathRemoved(path, stat);
        }
    }

    private void notifyUpdated(final String path, final byte[] data, final Stat stat) {
        for (PathChildrenCacheEventListener listener : registeredListeners) {
            listener.childPathUpdated(path, data, stat);
        }
    }


}
