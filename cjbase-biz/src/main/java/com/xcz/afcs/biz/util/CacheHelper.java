package com.xcz.afcs.biz.util;

import com.xcz.afcs.cache.CacheClient;
import com.xcz.afcs.cache.CacheClientFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CacheHelper {

    private static CacheHelper instance = new CacheHelper();

    private CacheClient cacheClient;

    public static CacheHelper getInstance() {
        return instance;
    }

    public CacheClient getCacheClient() {
        if (null == cacheClient) {
            cacheClient = CacheClientFactory.createCacheClientFromConfigUtil("cache.properties");
        }
        return cacheClient;
    }
    
    public void set(String key, String value, int expireSecs) {
        if (0 > expireSecs) {
            getCacheClient().set(key, value);
        } else {
            getCacheClient().set(key, value, expireSecs);
        }
    }
    
    public String get(String key) {
        // 空白key直接返回null
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return getCacheClient().get(key);
    }
    
    public boolean exists(String key) {
        return getCacheClient().exists(key);
    }
    
    public boolean refresh(String key, int expireSecs) {
        if (0 > expireSecs) {
            return getCacheClient().persist(key);
        } else {
            return getCacheClient().expire(key, expireSecs);
        }
    }
    
    public void destroy(String key) {
        getCacheClient().delete(key);
    }
    
    public boolean check(String key, String value) {
        String valueSaved = getCacheClient().get(key);
        return null != valueSaved && valueSaved.equals(value);
    }
    
    public List<String> getList(String key,int start ,int end){
    	return getCacheClient().lrange(key, start, end);
    }
    
    public String lget(String key,long index){
    	return getCacheClient().lindex(key, index);
    }
    
    public String lset(String key,int index,String value){
    	return getCacheClient().lset(key, index, value);
    }
    public long ladd(String key,String value){
    	return getCacheClient().lpush(key, value);
    }
    /**
     * 从存于 key 的列表里移除前 count 次出现的值为 value 的元素
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long lrem(String key,long count,String value){
    	return getCacheClient().lrem(key, count, value);
    }
    
    public long laddAll(String key,String... values){
    	return getCacheClient().lpush(key, values);
    }

    public long laddAll(String key,List<String> values){
    	String[] arr = (String[])values.toArray(new String[values.size()]);
    	return getCacheClient().lpush(key, arr);
    }
    
    public long llen(String key){
    	return getCacheClient().llen(key);
    };
    
    public long lrem(String key,String value){
    	return getCacheClient().lrem(key, 0, value);
    };
    /**
     * 增加成员
     * 如果一个指定的成员已经在对应的有序集合中了，那么其分数就会被更新成最新的，并且该成员会重新调整到正确的位置，以确保集合有序
     * @param key
     * @param score
     * @param member
     * @return
     */
    public long zdd(String key,double score,String member){
    	return getCacheClient().zdd(key, score, member);
    }
    /**
     * 批量增加成员
     * @param key
     * @param scoreMembers
     * @return
     */
    public long zdd(String key,Map<String,Double> scoreMembers){
    	return getCacheClient().zdd(key, scoreMembers);
    }
    /**
     * 获取一个排序的集合中的成员数量
     * @param key
     * @return
     */
	public long zcard(String key){
		return getCacheClient().zcard(key);
	}
	/**
	 * 增量的一名成员在排序设置的评分
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Double zincrby(String key,double score ,String member){
		return getCacheClient().zincrby(key, score, member);
	}
	/**
	 * 获取集合中元素
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public Set<String> zrangebyscore(String key,double min,double max,int offset,int count){
		return getCacheClient().zrangebyscore(key, min, max, offset, count);
	}
	/**
     * 查询成员在队列中的位置(从小到大)
     * @param key
     * @param member
     * @return
     */
    public Long zrank(String key,String member){
    	return getCacheClient().zrank(key, member);
    }
    
	/**
	 * 查询成员在队列中的位置(从大到小)
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrevrank(String key,String member){
		return getCacheClient().zrevrank(key, member);
	}
    
	/**
     * 返回有序集合key中，指定区间的成员(从小到大)
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<String> zrange(String key,long start ,long stop){
    	return getCacheClient().zrange(key, start, stop);
    }
    /**
     * 返回有序集合key中，指定区间的成员(从大到小)
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<String> zrevrange(String key,long start,long stop){
    	return getCacheClient().zrevrange(key, start, stop);
    }
    /**
     * 返回的是从有序集合中删除的成员个数，不包括不存在的成员。
     * @param key
     * @param members
     * @return
     */
    public Long zrem(String key,String... members){
    	return getCacheClient().zrem(key, members);
    }
    
    /**
     * 返回有序集key中，成员member的score值。
     * @param key
     * @return
     */
    public Double zscore(String key,String member){
    	return getCacheClient().zscore(key, member);
    }
    
    /**
     * 添加一个或者多个元素到集合(set)里
     * @param key
     * @param members
     * @return
     */
    public Long sdd(String key,String... members){
    	return getCacheClient().sdd(key, members);
    }
    /**
     * 获取集合里面的元素数量
     * @param key
     * @return
     */
    public Long scard(String key){
    	return getCacheClient().scard(key);
    }
    /**
     * 从集合里删除一个或多个member
     * @param key
     * @param members
     * @return
     */
    public Long srem(String key,String... members){
    	return getCacheClient().srem(key, members);
    }
    /**
     * 获取集合里面的所有元素 
     * @param key
     * @return
     */
    public Set<String> smembers(String key){
    	return getCacheClient().smembers(key);
    }
   
    
    
}
