package com.xcz.afcs.zookeeper;

import com.xcz.afcs.zookeeper.watch.NodeDataCacheListener;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jingang on 2017/9/4.
 */
public class ZooKeeperClientTest {

    private ZooKeeperClient client;

    @Before
    public void connect() {
        client = new ZooKeeperClient("192.168.1.138:2181");
        client.start();
    }

    @Test
    public void create() {
        client.create("/test/create");
        Assert.assertTrue(client.exists("/test/create"));
    }

    @Test
    public void delete() {
        client.create("/test/create");
        client.create("/test/delete");
        client.delete("/test");
        Assert.assertFalse(client.exists("/test"));
    }

    @Test
    public void setData() {
        client.setStringData("/test/create/node1", "demo");
        Assert.assertEquals(client.getStringData("/test/create/node1"), "demo");
    }

    @After
    public void close() {
        client.close();
    }

    @Test
    public void watchNodeData() {
        client.watchNodeData("/test/create/node1", new NodeDataCacheListener() {
            @Override
            public void nodeChanged(String path, byte[] data) {
                System.out.println("=============data:"+data ==null ? "" : new String(data));
            }
        });
    }


}
