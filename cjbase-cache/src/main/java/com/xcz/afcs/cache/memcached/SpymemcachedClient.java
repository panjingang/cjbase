package com.xcz.afcs.cache.memcached;

import com.xcz.afcs.cache.CacheClientImpl;
import com.xcz.afcs.util.ValueUtil;
import net.spy.memcached.*;
import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SpymemcachedClient extends CacheClientImpl implements MemcachedClient {

    private static final Logger LOG = LoggerFactory.getLogger(SpymemcachedClient.class);

    private net.spy.memcached.MemcachedClient client;
    
    private String connectString;
    
    private List<String> serverStringList;
    
    private MemcachedClientConnConfig connConfig;
    
    private Boolean touchEnable;
    
    private long opTimeout;
    
    public SpymemcachedClient() {
        
    }
    
    public SpymemcachedClient(String connectString) {
        this.connectString = connectString;
    }
    
    public SpymemcachedClient(List<String> serverStringList) {
        this.serverStringList = serverStringList;
    }
    
    public SpymemcachedClient(String connectString, MemcachedClientConnConfig connConfig) {
        this.connectString = connectString;
        this.connConfig = connConfig;
    }
    
    public SpymemcachedClient(List<String> serverStringList, MemcachedClientConnConfig connConfig) {
        this.serverStringList = serverStringList;
        this.connConfig = connConfig;
    }
    
    public SpymemcachedClient init() throws IOException {
        if (null == connConfig) {
            connConfig = new MemcachedClientConnConfig();
        }
        
        // addresses
        List<InetSocketAddress> addresses = null;
        if (StringUtils.isNotBlank(connectString)) {
            addresses = AddrUtil.getAddresses(connectString);
        } else if (!ValueUtil.isEmpty(serverStringList)) {
            addresses = AddrUtil.getAddresses(serverStringList);
        } else {
            throw new IllegalArgumentException("servers is required");
        }
        
        // connectionFactory
        ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();
        
        // protocol
        connectionFactoryBuilder.setProtocol(Protocol.BINARY);
        
        // username/password
        String username = connConfig.getUsername();
        if (StringUtils.isNotBlank(username)) {
            String password = connConfig.getPassword();
            if (null == password) {
                password = "";
            }
            AuthDescriptor authDescriptor = new AuthDescriptor(new String[] { "PLAIN" }, new PlainCallbackHandler(
                    username, password));
            connectionFactoryBuilder.setAuthDescriptor(authDescriptor);
        }
        
        // opTimeout
        opTimeout = connConfig.getOpTimeout();
        connectionFactoryBuilder.setOpTimeout(opTimeout);
        // timeoutExceptionThreshold
        connectionFactoryBuilder.setTimeoutExceptionThreshold(connConfig.getTimeoutExceptionThreshold());
        // hashAlg
        String hashAlg = connConfig.getHashAlg();
        try {
            connectionFactoryBuilder.setHashAlg(DefaultHashAlgorithm.valueOf(hashAlg));
        } catch (Exception e) {
            LOG.warn("invalid hashAlg: " + hashAlg + ", will use default: "
                    + MemcachedClientConnConfig.DEFAULT_HASH_ALG, e);
        }
        // locatorType
        String locatorType = connConfig.getLocatorType();
        try {
            connectionFactoryBuilder.setLocatorType(Locator.valueOf(locatorType));
        } catch (Exception e) {
            LOG.warn("invalid locatorType: " + locatorType + ", will use default: "
                    + MemcachedClientConnConfig.DEFAULT_LOCATOR_TYPE, e);
        }
        // failureMode
        String failureMode = connConfig.getFailureMode();
        try {
            connectionFactoryBuilder.setFailureMode(FailureMode.valueOf(failureMode));
        } catch (Exception e) {
            LOG.warn("invalid failureMode: " + failureMode + ", will use default: "
                    + MemcachedClientConnConfig.DEFAULT_FAILURE_MODE, e);
        }
        
        // connectionFactory
        ConnectionFactory connectionFactory = connectionFactoryBuilder.build();
        
        // client
        client = new net.spy.memcached.MemcachedClient(connectionFactory, addresses);
        
        LOG.info("version info: " + client.getVersions());
        LOG.info("stats info: " + client.getStats());
        checkTouchEnable();
        return this;
    }
    
    public void destory() {
        if (null != client) {
            client.shutdown();
        }
    }
    
    public net.spy.memcached.MemcachedClient getClient() {
        return client;
    }
    
    private void checkTouchEnable() {
        String testKey = getClass().getName();
        String testValue = testKey;
        set(testKey, testValue, 30);
        try {
            touchEnable = touch(testKey, 1);
            LOG.info("touchEnable=" + touchEnable);
        } finally {
            delete(testKey);
        }
    }
    
    protected <V> V fetchFutureResult(Future<V> future, V defaultValue, String opName, String key) {
        try {
            return future.get(opTimeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LOG.error(opName + " failed, key=" + key, e);
            future.cancel(false);
            return defaultValue;
        }
    }
    
    @Override
    public void set(String key, String value) {
        set(key, value, 0);
    }
    
    @Override
    public void set(String key, String value, int expireSecs) {
        OperationFuture<Boolean> future = client.set(key, expireSecs, value);
        fetchFutureResult(future, false, "set", key);
    }
    
    protected Object getObject(String key) {
        GetFuture<Object> future = client.asyncGet(key);
        return fetchFutureResult(future, null, "get", key);
    }
    
    @Override
    public String get(String key) {
        Object value = getObject(key);
        return ValueUtil.getString(value, null);
    }
    
    protected boolean touch(String key, int expireSecs) {
        OperationFuture<Boolean> future = client.touch(key, expireSecs);
        return fetchFutureResult(future, false, "touch", key);
    }
    
    protected CASValue<Object> gets(String key) {
        OperationFuture<CASValue<Object>> future = client.asyncGets(key);
        return fetchFutureResult(future, null, "gets", key);
    }
    
    protected CASResponse cas(String key, Object value, int expireSecs, long casId) {
        OperationFuture<CASResponse> future = client.asyncCAS(key, casId, expireSecs, value);
        return fetchFutureResult(future, null, "cas", key);
    }
    
    @Override
    public boolean expire(String key, int expireSecs) {
        if (touchEnable) {
            return touch(key, expireSecs);
        } else {
            CASValue<Object> value = gets(key);
            if (null != value) {
                CASResponse resp = cas(key, value.getValue(), expireSecs, value.getCas());
                return null != resp && CASResponse.OK == resp;
            }
            return false;
        }
    }
    
    @Override
    public boolean persist(String key) {
        return expire(key, 0);
    }
    
    @Override
    public boolean delete(String key) {
        OperationFuture<Boolean> future = client.delete(key);
        return fetchFutureResult(future, false, "delete", key);
    }
    
    @Override
    public boolean exists(String key) {
        return null != getObject(key);
    }
    
    @Override
    public long incrBy(String key, long by) {
        OperationFuture<Long> future = client.asyncIncr(key, by);
        return fetchFutureResult(future, -1L, "incr", key);
    }
    
    @Override
    public long incr(String key) {
        return incrBy(key, 1);
    }
    
    @Override
    public long decrBy(String key, long by) {
        OperationFuture<Long> future = client.asyncDecr(key, by);
        return fetchFutureResult(future, -1L, "decr", key);
    }
    
    @Override
    public long decr(String key) {
        return decrBy(key, 1);
    }
    
    
    
    public String getConnectString() {
        return connectString;
    }
    
    public void setConnectString(String connectString) {
        if (StringUtils.isNotBlank(connectString)) {
            serverStringList = null;
        }
        this.connectString = connectString;
    }
    
    public List<String> getServerStringList() {
        return serverStringList;
    }
    
    public void setServerStringList(List<String> serverStringList) {
        if (null != serverStringList && !serverStringList.isEmpty()) {
            connectString = null;
        }
        this.serverStringList = serverStringList;
    }
    
    public MemcachedClientConnConfig getConnConfig() {
        return connConfig;
    }
    
    public void setConnConfig(MemcachedClientConnConfig connConfig) {
        this.connConfig = connConfig;
    }
    
    public long getOpTimeout() {
        return opTimeout;
    }
    
    public Boolean getTouchEnable() {
        return touchEnable;
    }
    
}
