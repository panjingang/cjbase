package com.xcz.afcs.mybatis.service;

import com.xcz.afcs.mybatis.entity.UpdatableEntity;
import com.xcz.afcs.redis.AfcsRedisCache;
import com.xcz.afcs.redis.CacheKey;

/**
 * Created by jingang on 2017/11/1.
 */
public abstract class BaseCacheService<T extends UpdatableEntity, K> extends BaseService<T , K> {

    private AfcsRedisCache afcsRedisCache;

    private CacheKey cacheKey;

    public BaseCacheService() {
        super();
    }

    public int update(T entity) {
        int rows = super.update(entity);
        putCache(entity);
        return rows;
    }

    public int updateCas(T entity) {
        int rows = super.updateCas(entity);
        putCache(entity);
        return rows;
    }

    public int delete(K id) {
        int rows = super.delete(id);
        removeCache(id);
        return rows;
    }

    public T getOne(K id) {
        T entity = getCache(id);
        if ( entity != null) {
            return entity;
        }
        entity = super.getOne(id);
        if (entity != null) {
            putCache(entity);
        }
        return entity;
    }

    public T getCache(K id) {
        if (afcsRedisCache == null) {
            afcsRedisCache = getAfcsRedisCache();
        }
        if (cacheKey == null) {
            cacheKey = getCacheKey();
        }
        return afcsRedisCache.get(cacheKey.getPrefix()+id);
    }

    public void removeCache(K id) {
        if (afcsRedisCache == null) {
            afcsRedisCache = getAfcsRedisCache();
        }
        if (cacheKey == null) {
            cacheKey = getCacheKey();
        }
        afcsRedisCache.remove(cacheKey.getPrefix()+id);
    }

    public void putCache(T entity) {
        if (afcsRedisCache == null) {
            afcsRedisCache = getAfcsRedisCache();
        }
        if (cacheKey == null) {
            cacheKey = getCacheKey();
        }
        afcsRedisCache.save(cacheKey.getPrefix()+entity.getPrimaryId(), entity, cacheKey.getExpireTime());
    }

    public abstract CacheKey getCacheKey();

    public abstract AfcsRedisCache getAfcsRedisCache();

}
