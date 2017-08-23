package com.xcz.afcs.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CacheClientImpl implements CacheClient {
	@Override
	public void set(String key, String value) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public void set(String key, String value, int expireSecs) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public String get(String key) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean expire(String key, int expireSecs) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean persist(String key) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean delete(String key) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean exists(String key) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public long incrBy(String key, long by) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public long incr(String key) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public long decrBy(String key, long by) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public long decr(String key) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public long llen(String key) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public long lpush(String key, String... values) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> lrange(String key, int start, int end) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public String lindex(String key, long index) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public String lset(String key, int index, String value) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public long lrem(String key, long count, String value) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public long zcard(String key) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public Double zincrby(String key, double score, String member) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public Set<String> zrangebyscore(String key,double min,double max,int offset,int count) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public Long zrevrank(String key, String member) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public Set<String> zrange(String key,long start,long stop) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public Set<String> zrevrange(String key, long start, long stop) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public long zdd(String key, double score, String member) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public long zdd(String key,Map<String,Double> scoreMembers) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public Long zrem(String key, String... members) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public Double zscore(String key, String member) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public Long zrank(String key, String member) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public Long sdd(String key, String... members) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public Long scard(String key) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public Long srem(String key, String... members) {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public Set<String> smembers(String key) {
		
		throw new UnsupportedOperationException();
	}


}
