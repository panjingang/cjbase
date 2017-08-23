package com.xcz.afcs.biz.dao;

import java.util.List;

public interface DAOBase<T, K> {
    
     void save(T entity);
    
     T get(K key);
    
     int count(T example);
    
     List<T> query(T example);
    
     int update(T entity);
    
     int delete(K key);
    
}
