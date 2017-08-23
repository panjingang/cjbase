package com.xcz.afcs.cache.redis;

import com.xcz.afcs.cache.CacheClientImpl;
import com.xcz.afcs.cache.redis.util.RedisClientHelper;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClusterRedisClient extends CacheClientImpl implements RedisClient {
    
    protected String connectString;
    
    protected JedisCluster jedisPool;
    
    protected RedisClientPoolConfig poolConfig;
    
    protected int maxRedirections = 20;
    
    public ClusterRedisClient(String connectString) {
        this.connectString = connectString;
    }
    
    public ClusterRedisClient(String connectString, RedisClientPoolConfig poolConfig) {
        this.connectString = connectString;
        this.poolConfig = poolConfig;
    }
    
    public ClusterRedisClient init() {
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
        
        Set<HostAndPort> addresses = RedisClientHelper.parseConnectStringToAddresses(connectString);
        
        jedisPool = new JedisCluster(addresses, poolConfig.getConnTimeout(), maxRedirections, jedisPoolConfig);
        return this;
    }
    
    public void destory() {
        if (null != jedisPool) {
            jedisPool.close();
        }
    }
    
    protected String processKey(String key) {
        return key;
    }
    
    @Override
    public void set(String key, String value) {
        jedisPool.set(processKey(key), value);
    }
    
    @Override
    public void set(String key, String value, int expireSecs) {
        jedisPool.setex(processKey(key), expireSecs, value);
    }
    
    @Override
    public String get(String key) {
        return jedisPool.get(processKey(key));
    }
    
    @Override
    public boolean expire(String key, int expireSecs) {
        return 1 == jedisPool.expire(processKey(key), expireSecs);
    }
    
    @Override
    public boolean persist(String key) {
        return 1 == jedisPool.persist(processKey(key));
    }
    
    @Override
    public boolean delete(String key) {
        return 1 == jedisPool.del(processKey(key));
    }
    
    @Override
    public boolean exists(String key) {
        return jedisPool.exists(processKey(key));
    }
    
    @Override
    public long incrBy(String key, long by) {
        return jedisPool.incrBy(processKey(key), by);
    }
    
    @Override
    public long incr(String key) {
        return jedisPool.incr(processKey(key));
    }
    
    @Override
    public long decrBy(String key, long by) {
        return jedisPool.decrBy(processKey(key), by);
    }
    
    @Override
    public long decr(String key) {
        return jedisPool.decr(processKey(key));
    }
    
    @Override
    public long llen(String key) {
		
		return jedisPool.llen(processKey(key));
	}
    @Override
	public long lpush(String key, String... values) {
		return jedisPool.lpush(processKey(key), values);
	}
    
    @Override
	public List<String> lrange(String key, int start, int end) {
		
    	return jedisPool.lrange(processKey(key), start, end);
	}
    
    @Override
	public String lindex(String key, long index) {
		
		return jedisPool.lindex(key, index);
	}

	@Override
	public String lset(String key, int index, String value) {
		
		return jedisPool.lset(key, index, value);
	}

	@Override
	public long lrem(String key, long count, String value) {
		
		return jedisPool.lrem(key, count, value);
	}

	@Override
	public long zcard(String key) {
		
		return jedisPool.zcard(key);
	}
	
	@Override
	public Double zincrby(String key, double score, String member) {
		
		return jedisPool.zincrby(key, score, member);
	}
	
	@Override
	public Set<String> zrangebyscore(String key,double min,double max,
			int offset,int count){
		
		return jedisPool.zrangeByScore(key, min, max, offset, count);
	}
	
	@Override
	public Long zrevrank(String key, String member) {
		
		return jedisPool.zrevrank(key, member);
	}
	
	@Override
	public Set<String> zrange(String key, long start, long stop) {
		
		return jedisPool.zrange(key, start, stop);
	}
	
	@Override
	public long zdd(String key, double score, String member) {
		
		return jedisPool.zadd(key, score, member);
	}
	
	@Override
	public long zdd(String key,Map<String,Double> scoreMembers) {
		
		return jedisPool.zadd(key, scoreMembers);
	}
	
	@Override
	public Long zrem(String key, String... members) {
		
		return jedisPool.zrem(key, members);
	}
	
	@Override
	public Double zscore(String key, String member) {
				
		return jedisPool.zscore(key, member);
	}
	
	
	@Override
	public Long zrank(String key, String member) {
		
		return jedisPool.zrank(key, member);
	}
	
	@Override
	public Long sdd(String key, String... members) {
		
		return jedisPool.sadd(key, members);
	}
	
	@Override
	public Long scard(String key) {
		
		return jedisPool.scard(key);
	}
	

	@Override
	public Long srem(String key, String... members) {
		
		return jedisPool.srem(key, members);
	}
	
	@Override
	public Set<String> smembers(String key) {
		
		return jedisPool.smembers(key);
	}
	
    public String getConnectString() {
        return connectString;
    }
    
    public RedisClientPoolConfig getPoolConfig() {
        return poolConfig;
    }
    
}
