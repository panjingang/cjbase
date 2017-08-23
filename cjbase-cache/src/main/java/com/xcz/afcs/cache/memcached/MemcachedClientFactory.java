package com.xcz.afcs.cache.memcached;

import com.xcz.afcs.cache.CacheClientFactory;
import com.xcz.afcs.util.AfbpProperties;
import com.xcz.afcs.util.ValueUtil;

import java.io.IOException;

public class MemcachedClientFactory {
    
    public static final String PROP_KEY_PREFIX = CacheClientFactory.PROP_KEY_PREFIX + "memcached.";
    
    public static final String PROP_KEY_CONNECTSTRING = "connectString";
    
    public static final String PROP_KEY_USERNAME = "username";
    
    public static final String PROP_KEY_PASSWORD = "password";
    
    public static final String PROP_KEY_OPTIMEOUT = "opTimeout";
    
    public static final String PROP_KEY_TIMEOUTEXCEPTIONTHRESHOLD = "timeoutExceptionThreshold";
    
    public static final String PROP_KEY_HASHALG = "hashAlg";
    
    public static final String PROP_KEY_LOCATORTYPE = "locatorType";
    
    public static final String PROP_KEY_FAILUREMODE = "failureMode";
    
    private static MemcachedClientConnConfig defaultConnConfig;

    private static MemcachedClientFactory factoryInstance;

    public static MemcachedClientFactory getFactoryInstance() {
        if (null == factoryInstance) {
            factoryInstance = new MemcachedClientFactory();
        }
        return factoryInstance;
    }

    protected static MemcachedClientConnConfig loadConnConfigFromConfigUtil() {
        MemcachedClientConnConfig connConfig = new MemcachedClientConnConfig();

        String username = AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_USERNAME, null);
        if (null != username) {
            connConfig.setUsername(username);
        }
        String password = AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_PASSWORD, null);
        if (null != password) {
            connConfig.setPassword(password);
        }

        long opTimeout = ValueUtil.getLong(AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_OPTIMEOUT), -1);
        if (0 <= opTimeout) {
            connConfig.setOpTimeout(opTimeout);
        }
        int timeoutExceptionThreshold = ValueUtil.getInt(AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_TIMEOUTEXCEPTIONTHRESHOLD), -1);
        if (0 <= timeoutExceptionThreshold) {
            connConfig.setTimeoutExceptionThreshold(timeoutExceptionThreshold);
        }
        String hashAlg = AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_HASHALG, null);
        if (null != hashAlg) {
            connConfig.setHashAlg(hashAlg);
        }
        String locatorType = AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_LOCATORTYPE, null);
        if (null != locatorType) {
            connConfig.setLocatorType(locatorType);
        }
        String failureMode = AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_FAILUREMODE, null);
        if (null != failureMode) {
            connConfig.setFailureMode(failureMode);
        }
        return connConfig;
    }

    public static MemcachedClient createSpymemcachedClientFromConfigUtil() {
        String connectString = AfbpProperties.getProperty(PROP_KEY_PREFIX + PROP_KEY_CONNECTSTRING);
        MemcachedClientConnConfig connConfig = loadConnConfigFromConfigUtil();
        try {
            return new SpymemcachedClient(connectString, connConfig).init();
        } catch (IOException e) {
            throw new RuntimeException("init spymemcachedClient failed", e);
        }
    }

    public MemcachedClient createSpymemcachedClient(String connectString) {
        try {
            return new SpymemcachedClient(connectString, defaultConnConfig).init();
        } catch (IOException e) {
            throw new RuntimeException("init spymemcachedClient failed", e);
        }
    }

    public MemcachedClient createSpymemcachedClient(String connectString, String username, String password) {
        try {
            MemcachedClientConnConfig connConfig = null;
            if (null == defaultConnConfig) {
                connConfig = new MemcachedClientConnConfig();
            } else {
                try {
                    connConfig = (MemcachedClientConnConfig) defaultConnConfig.clone();
                } catch (CloneNotSupportedException e) {
                    // will not happen
                }
            }
            connConfig.setUsername(username);
            connConfig.setPassword(password);
            return new SpymemcachedClient(connectString, connConfig).init();
        } catch (IOException e) {
            throw new RuntimeException("init spymemcachedClient failed", e);
        }
    }

    public MemcachedClient createSpymemcachedClient(String connectString, MemcachedClientConnConfig connConfig) {
        try {
            return new SpymemcachedClient(connectString, connConfig).init();
        } catch (IOException e) {
            throw new RuntimeException("init spymemcachedClient failed", e);
        }
    }

    public static MemcachedClientConnConfig getDefaultConnConfig() {
        return defaultConnConfig;
    }
    
    public static void setDefaultConnConfig(MemcachedClientConnConfig defaultConnConfig) {
        MemcachedClientFactory.defaultConnConfig = defaultConnConfig;
    }
    
}
