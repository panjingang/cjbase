package com.xcz.afcs.util;

import java.util.*;
import java.util.Map.Entry;

public class CollectionUtil {
    
    public static Map<String, Object> merge(Map<String, Object>... maps) {
        int length = 0;
        if (null == maps || 0 == (length = maps.length)) {
            return new HashMap<String, Object>();
        }
        Map<String, Object> base = maps[0];
        for (int i = 1; i < length; i++) {
            Map<String, Object> other = maps[i];
            if (null == other) {
                continue;
            }
            for (Entry<String, Object> entry : other.entrySet()) {
                base.put(entry.getKey(), entry.getValue());
            }
        }
        return base;
    }
    
    public static Map<String, Object> merge(Collection<Map<String, Object>> maps) {
        if (null == maps || maps.isEmpty()) {
            return new HashMap<String, Object>();
        }
        Iterator<Map<String, Object>> mapsIt = maps.iterator();
        Map<String, Object> base = null;
        while (mapsIt.hasNext()) {
            Map<String, Object> map = mapsIt.next();
            if (null == map) {
                continue;
            }
            if (null == base) {
                base = map;
            } else {
                for (Entry<String, Object> entry : map.entrySet()) {
                    base.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return base;
    }
    
    public static Map<String, Object> merge(Map<String, Object> base, Properties prop) {
        if (null == base) {
            base = new HashMap<String, Object>();
        }
        if (null != prop && !prop.isEmpty()) {
            for (Entry<Object, Object> entry : prop.entrySet()) {
                base.put((String) entry.getKey(), entry.getValue());
            }
        }
        return base;
    }
    
    public static Map<String, Object> subMap(Map<String, Object> sourceMap, String... keys) {
        Map<String, Object> subMap = new HashMap<String, Object>();
        if (null == sourceMap || sourceMap.isEmpty()) {
            return subMap;
        }
        if (null == keys || 0 == keys.length) {
            subMap.putAll(sourceMap);
        } else {
            for (String key : keys) {
                if (sourceMap.containsKey(key)) {
                    subMap.put(key, sourceMap.get(key));
                }
            }
        }
        return subMap;
    }
    
}
