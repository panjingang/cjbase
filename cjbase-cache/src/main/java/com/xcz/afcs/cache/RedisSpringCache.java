package com.xcz.afcs.cache;

import com.xcz.afcs.util.IOUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.io.*;

/**
 * Created by jingang on 2016/3/22.
 */
public class RedisSpringCache implements Cache, InitializingBean {

    private String name;

    private CacheClient cacheClient;

    public CacheClient getCacheClient() {
        if (null == cacheClient) {
            cacheClient = CacheClientFactory.createCacheClientFromConfigUtil("cache.properties");
        }
        return cacheClient;
    }

        @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void clear() {
    }

    @Override
    public void evict(Object key) {
        cacheClient.delete((String)key);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        put(key, value);
        return get(key);
    }

    @Override
    public void put(Object key, Object value) {
        if (value instanceof Serializable) {
            cacheClient.set((String)key, toString((Serializable)value));
        }
        else {
            throw new IllegalStateException("缓存的值类型必须序列化");
        }
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper element = get(key);
        Object value = (element != null ? element.get() : null);
        if (value == null)
            return null;
        if (type != null && !type.isInstance(value)) {
            throw new IllegalStateException("缓存的值类型指定错误 [" + type.getName() + "]: " + value);
        }
        return (T) value;
    }

    @Override
    public ValueWrapper get(Object key) {
        if (key instanceof String) {
            String data = cacheClient.get((String)key);
            return new SimpleValueWrapper(fromString(data));
        }
        throw new IllegalStateException("缓存的Key类型指定错误目前只支持String");
    }

    @Override
    public Object getNativeCache() {
        return cacheClient;
    }


    private String toString(Serializable obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos    = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        finally{
            IOUtil.close(oos);
            IOUtil.close(bos);
        }
        return Base64.encodeBase64String(bytes);
    }

    private Object fromString(String str) {
        Object obj              = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream    ois = null;
        try {
            byte[] data = Base64.decodeBase64(str);
            bis = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally{
            IOUtil.close(ois);
            IOUtil.close(bis);
        }
        return obj;
    }


}
