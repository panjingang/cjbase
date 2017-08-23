package com.xcz.afcs.cache.redis;

public class RedisClientPoolConfig implements Cloneable {
    
    public static final int DEFAULT_CONN_TIMEOUT = 2000;
    
    public static final int DEFAULT_MAX_TOTAL = 4;
    
    public static final int DEFAULT_MAX_IDLE = 1;
    
    public static final int DEFAULT_MIN_IDLE = 1;
    
    public static final long DEFAULT_MAX_WAIT_MILLIS = 10 * 1000L;
    
    public static final long DEFAULT_EVICTABLE_IDLE_TIME_MILLIS = 60 * 1000L;
    
    protected int connTimeout = DEFAULT_CONN_TIMEOUT;
    
    protected int maxTotal = DEFAULT_MAX_TOTAL;
    
    protected int maxIdle = DEFAULT_MAX_IDLE;
    
    protected int minIdle = DEFAULT_MIN_IDLE;
    
    protected long maxWaitMillis = DEFAULT_MAX_WAIT_MILLIS;
    
    protected long minEvictableIdleTimeMillis = DEFAULT_EVICTABLE_IDLE_TIME_MILLIS;
    
    public int getConnTimeout() {
        return connTimeout;
    }
    
    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }
    
    public int getMaxTotal() {
        return maxTotal;
    }
    
    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }
    
    public int getMaxIdle() {
        return maxIdle;
    }
    
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }
    
    public int getMinIdle() {
        return minIdle;
    }
    
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }
    
    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }
    
    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }
    
    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }
    
    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
}
