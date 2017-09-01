package com.xcz.afcs.core.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支持并发的HashSet，基于{@link ConcurrentHashMap}，参考{@link java.util.HashSet}实现。
 * @author zhaohc
 * 
 * @param <E>
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E>, Cloneable, java.io.Serializable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    private transient ConcurrentHashMap<E, Boolean> map;
    
    public ConcurrentHashSet() {
        map = new ConcurrentHashMap<E, Boolean>();
    }
    
    public ConcurrentHashSet(Collection<? extends E> c) {
        map = new ConcurrentHashMap<E, Boolean>(Math.max((int) (c.size() / .75f) + 1, 16));
        addAll(c);
    }
    
    public ConcurrentHashSet(int initialCapacity, float loadFactor) {
        map = new ConcurrentHashMap<E, Boolean>(initialCapacity, loadFactor);
    }
    
    public ConcurrentHashSet(int initialCapacity) {
        map = new ConcurrentHashMap<E, Boolean>(initialCapacity);
    }
    
    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }
    
    @Override
    public int size() {
        return map.size();
    }
    
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }
    
    public boolean add(E e) {
        return null == map.putIfAbsent(e, Boolean.FALSE);
    }
    
    @Override
    public boolean remove(Object o) {
        return Boolean.FALSE == map.remove(o);
    }
    
    @Override
    public void clear() {
        map.clear();
    }
    
}
