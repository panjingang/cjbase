package com.xcz.afcs.biz.service;

import com.xcz.afcs.biz.entity.Pageable;
import com.xcz.afcs.biz.model.Pagination;

import java.util.List;

public interface ServiceBase<T extends Pageable, K> {
    
     void save(T entity);
    
     T get(K key);
    
     int count(T param);
    
     List<T> query(T param);
    
     List<T> queryAll();
    
     List<T> queryWithPagination(T param, Pagination page);

     int update(T entity);
    
     int updateCAS(T entity);
    
     int updateCAS(T entity, boolean throwException);
    
     int delete(K key);

     void saveOrUpdate(T entity);
    
}
