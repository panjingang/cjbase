package com.xcz.afcs.cache.memcached;

import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.FailureMode;

public class MemcachedClientConnConfig implements Cloneable {
    
    public static final long DEFAULT_OP_TIMEOUT = 2500;
    
    public static final int DEFAULT_TIMEOUNT_EXCEPTION_THRESHOLD = 1998;
    
    public static final String DEFAULT_HASH_ALG = DefaultHashAlgorithm.KETAMA_HASH.name();
    
    public static final String DEFAULT_LOCATOR_TYPE = Locator.CONSISTENT.name();
    
    public static final String DEFAULT_FAILURE_MODE = FailureMode.Redistribute.name();
    
    protected String username;
    
    protected String password;
    
    protected long opTimeout = DEFAULT_OP_TIMEOUT;
    
    protected int timeoutExceptionThreshold = DEFAULT_TIMEOUNT_EXCEPTION_THRESHOLD;
    
    protected String hashAlg = DEFAULT_HASH_ALG;
    
    protected String locatorType = DEFAULT_LOCATOR_TYPE;
    
    protected String failureMode = DEFAULT_FAILURE_MODE;
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public long getOpTimeout() {
        return opTimeout;
    }
    
    public void setOpTimeout(long opTimeout) {
        this.opTimeout = opTimeout;
    }
    
    public int getTimeoutExceptionThreshold() {
        return timeoutExceptionThreshold;
    }
    
    public void setTimeoutExceptionThreshold(int timeoutExceptionThreshold) {
        this.timeoutExceptionThreshold = timeoutExceptionThreshold;
    }
    
    public String getHashAlg() {
        return hashAlg;
    }
    
    public void setHashAlg(String hashAlg) {
        this.hashAlg = hashAlg;
    }
    
    public String getLocatorType() {
        return locatorType;
    }
    
    public void setLocatorType(String locatorType) {
        this.locatorType = locatorType;
    }
    
    public String getFailureMode() {
        return failureMode;
    }
    
    public void setFailureMode(String failureMode) {
        this.failureMode = failureMode;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
}
