package com.xcz.afcs.cache.redis;

import com.xcz.afcs.cache.CacheClientImpl;
import com.xcz.afcs.cache.redis.util.RedisClientHelper;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasicPoolRedisClient extends CacheClientImpl implements RedisClient {
    
    protected String connectString;
    
    protected JedisPool jedisPool;
    
    protected RedisClientPoolConfig poolConfig;

    public BasicPoolRedisClient() {

    }

    public BasicPoolRedisClient(String connectString) {
        this.connectString = connectString;
    }

    public BasicPoolRedisClient(String connectString, RedisClientPoolConfig poolConfig) {
        this.connectString = connectString;
        this.poolConfig = poolConfig;
    }

    public BasicPoolRedisClient init() {
        if (null == poolConfig) {
            poolConfig = new RedisClientPoolConfig();
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(poolConfig.getMaxTotal());
        jedisPoolConfig.setMaxIdle(poolConfig.getMaxIdle());
        jedisPoolConfig.setMinIdle(poolConfig.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(poolConfig.getMaxWaitMillis());
        jedisPoolConfig.setMinEvictableIdleTimeMillis(poolConfig.getMinEvictableIdleTimeMillis());
        jedisPoolConfig.setTestOnBorrow(true);
        HostAndPort address = RedisClientHelper.parseAddressUnit(connectString);
        String host = address.getHost();
        int port = address.getPort();
        jedisPool = new JedisPool(jedisPoolConfig, host, port, poolConfig.getConnTimeout());
        return this;
    }

    public void destory() {
        if (null != jedisPool) {
            jedisPool.close();
        }
    }

    public Jedis getJedisFromPool() {
        if (null == jedisPool) {
            throw new IllegalStateException("Please call init method first.");
        }
        return jedisPool.getResource();
    }

    protected String processKey(String key) {
        return key;
    }

    @Override
    public void set(String key, String value) {
        Jedis jedis = getJedisFromPool();
        try {
            jedis.set(processKey(key), value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public void set(String key, String value, int expireSecs) {
        Jedis jedis = getJedisFromPool();
        try {
            jedis.setex(processKey(key), expireSecs, value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public String get(String key) {
        Jedis jedis = getJedisFromPool();
        try {
            return jedis.get(processKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean expire(String key, int expireSecs) {
        Jedis jedis = getJedisFromPool();
        try {
            return 1 == jedis.expire(processKey(key), expireSecs);
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean persist(String key) {
        Jedis jedis = getJedisFromPool();
        try {
            return 1 == jedis.persist(processKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean delete(String key) {
        Jedis jedis = getJedisFromPool();
        try {
            return 1 == jedis.del(processKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean exists(String key) {
        Jedis jedis = getJedisFromPool();
        try {
            return jedis.exists(processKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public long incrBy(String key, long by) {
        Jedis jedis = getJedisFromPool();
        try {
            return jedis.incrBy(processKey(key), by);
        } finally {
            jedis.close();
        }
    }

    @Override
    public long incr(String key) {
        Jedis jedis = getJedisFromPool();
        try {
            return jedis.incr(processKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public long decrBy(String key, long by) {
        Jedis jedis = getJedisFromPool();
        try {
            return jedis.decrBy(processKey(key), by);
        } finally {
            jedis.close();
        }
    }

    @Override
    public long decr(String key) {
        Jedis jedis = getJedisFromPool();
        try {
            return jedis.decr(processKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public long llen(String key) {

		Jedis jedis = getJedisFromPool();
        try {
        	return jedis.llen(processKey(key));
        } finally {
            jedis.close();
        }
	}
    @Override
	public long lpush(String key, String... values) {
    	Jedis jedis = getJedisFromPool();
        try {
        	return jedis.lpush(processKey(key), values);
        } finally {
            jedis.close();
        }
	}

    @Override
	public List<String> lrange(String key, int start, int end) {

    	Jedis jedis = getJedisFromPool();
        try {
        	return jedis.lrange(processKey(key), start, end);
        } finally {
            jedis.close();
        }
	}

    @Override
	public String lindex(String key, long index) {

		Jedis jedis = getJedisFromPool();
        try {
        	return jedis.lindex(key, index);
        } finally {
            jedis.close();
        }
	}

	@Override
	public String lset(String key, int index, String value) {

		Jedis jedis = getJedisFromPool();
        try {

        	return jedis.lset(key, index, value);
        } finally {
            jedis.close();
        }
	}

	@Override
	public long lrem(String key, long count, String value) {

		Jedis jedis = getJedisFromPool();
        try {
        	return jedis.lrem(key, count, value);
        } finally {
            jedis.close();
        }
	}

	@Override
	public long zcard(String key) {

		Jedis jedis = getJedisFromPool();
        try {
        	return jedis.zcard(key);
        } finally {
            jedis.close();
        }
	}

	@Override
	public Double zincrby(String key, double score, String member) {

		Jedis jedis = getJedisFromPool();
        try {
        	return jedis.zincrby(key, score, member);
        } finally {
            jedis.close();
        }
	}

	@Override
	public Set<String> zrange(String key, long start, long stop) {

		Jedis jedis = getJedisFromPool();
		try {
	        	return jedis.zrange(key, start, stop);
	        } finally {
	            jedis.close();
	        }
	}

	@Override
	public Set<String> zrevrange(String key, long start, long stop) {

		Jedis jedis = getJedisFromPool();
		try {
	        	return jedis.zrevrange(key, start, stop);
	        } finally {
	            jedis.close();
	        }
	}

	@Override
	public long zdd(String key, double score, String member) {

		Jedis jedis = getJedisFromPool();
		try {
	        	return jedis.zadd(key, score, member);
	        } finally {
	            jedis.close();
	        }
	}

	@Override
	public long zdd(String key,Map<String,Double> scoreMembers) {

		Jedis jedis = getJedisFromPool();
		try {
        	return jedis.zadd(key, scoreMembers);
        } finally {
            jedis.close();
        }
	}

	@Override
	public Long zrem(String key,String... members) {

		Jedis jedis = getJedisFromPool();
		try {
        	return jedis.zrem(key, members);
        } finally {
            jedis.close();
        }
	}

	@Override
	public Double zscore(String key, String member) {

		Jedis jedis = getJedisFromPool();
		try {
        	return jedis.zscore(key, member);
        } finally {
            jedis.close();
        }
	}

	@Override
	public Long zrank(String key, String member) {

		Jedis jedis = getJedisFromPool();
		try {
        	return jedis.zrank(key, member);
        } finally {
            jedis.close();
        }
	}

	@Override
	public Long zrevrank(String key, String member) {

		Jedis jedis = getJedisFromPool();
		try {
        	return jedis.zrevrank(key, member);
        } finally {
            jedis.close();
        }
	}

	@Override
	public Long sdd(String key, String... members) {

		Jedis jedis = getJedisFromPool();
		try {
        	return jedis.sadd(key, members);
        } finally {
            jedis.close();
        }
	}

	@Override
	public Long scard(String key) {

		Jedis jedis = getJedisFromPool();
		try {
        	return jedis.scard(key);
        } finally {
            jedis.close();
        }
	}


	@Override
	public Long srem(String key, String... members) {

		Jedis jedis = getJedisFromPool();
		try {
        	return jedis.srem(key, members);
        } finally {
            jedis.close();
        }
	}

	@Override
	public Set<String> smembers(String key) {

		Jedis jedis = getJedisFromPool();
		try {
        	return jedis.smembers(key);
        } finally {
            jedis.close();
        }
	}

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public RedisClientPoolConfig getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(RedisClientPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }
    
}
