package com.xcz.afcs.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CacheClient {
    
    public void set(String key, String value);
    
    public void set(String key, String value, int expireSecs);
    
    public String get(String key);
    
    public boolean expire(String key, int expireSecs);
    
    public boolean persist(String key);
    
    public boolean delete(String key);
    
    public boolean exists(String key);
    
    public long incrBy(String key, long by);
    
    public long incr(String key);
    
    public long decrBy(String key, long by);
    
    public long decr(String key);
    /**
     * 返回存储在 key 里的list的长度
     * @param key
     * @return
     */
    public long llen(String key);
    /**
     * 将所有指定的值插入到存于 key 的列表的头部   
     * @param key
     * @param values
     * @return
     */
    public long lpush(String key, String... values);
    
    public List<String> lrange(String key, int start, int end);
    /**
     * 返回列表里的元素的索引 index 存储在 key 里面。
     * @param key
     * @param index
     * @return
     */
    public String lindex(String key, long index);
    /**
     * 设置 index 位置的list元素的值为 value
     * @param key
     * @param index
     * @param value
     * @return
     */
    public String lset(String key, int index, String value);
    /**
     * 从存于 key 的列表里移除前 count 次出现的值为 value 的元素
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long lrem(String key, long count, String value);
    /**
     * 增加成员
     * 如果一个指定的成员已经在对应的有序集合中了，那么其分数就会被更新成最新的，并且该成员会重新调整到正确的位置，以确保集合有序
     * @param key
     * @param score
     * @param member
     * @return
     */
    public long zdd(String key, double score, String member);
    /**
     * 批量增加成员
     * @param key
     * @param scoreMembers
     * @return
     */
    public long zdd(String key, Map<String, Double> scoreMembers);
    
    /**
     * 获取一个排序的集合中的成员数量
     * @param key
     * @return
     */
    public long zcard(String key);
    /**
     * 为有序集key的成员member的score值加上增量increment
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(String key, double score, String member);
    /**
     * 获取集合中元素
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<String> zrangebyscore(String key, double min, double max, int offset, int count);
    
    /**
     * 查询成员在队列中的位置(从小到大)
     * @param key
     * @param member
     * @return
     */
    public Long zrank(String key, String member);
    /**
     * 查询成员在队列中的位置(从大到小)
     * @param key
     * @param member
     * @return
     */
    public Long zrevrank(String key, String member);
    /**
     * 返回有序集合key中，指定区间的成员(从小到大)
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<String> zrange(String key, long start, long stop);
    /**
     * 返回有序集合key中，指定区间的成员(从大到小)
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<String> zrevrange(String key, long start, long stop);
    /**
     * 返回的是从有序集合中删除的成员个数，不包括不存在的成员。
     * @param key
     * @param members
     * @return
     */
    public Long zrem(String key, String... members);
    /**
     * 返回有序集key中，成员member的score值。
     * @param key
     * @return
     */
    public Double zscore(String key, String member);
    
    /**
     * 添加一个或者多个元素到集合(set)里
     * @param key
     * @param members
     * @return
     */
    public Long sdd(String key, String... members);
    /**
     * 获取集合里面的元素数量
     * @param key
     * @return
     */
    public Long scard(String key);
    /**
     * 从集合里删除一个或多个member
     * @param key
     * @param members
     * @return
     */
    public Long srem(String key, String... members);
    /**
     * 获取集合里面的所有元素 
     * @param key
     * @return
     */
    public Set<String> smembers(String key);
    
}
