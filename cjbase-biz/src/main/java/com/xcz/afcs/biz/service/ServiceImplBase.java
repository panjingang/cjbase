package com.xcz.afcs.biz.service;

import com.xcz.afcs.biz.dao.DAOBase;
import com.xcz.afcs.biz.entity.Pageable;
import com.xcz.afcs.biz.entity.Updatable;
import com.xcz.afcs.biz.model.Pagination;
import com.xcz.afcs.biz.util.SessionHelper;
import com.xcz.afcs.core.enums.BaseErrorCode;
import com.xcz.afcs.core.exception.BaseBusinessException;
import com.xcz.afcs.util.ValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public abstract class ServiceImplBase<T extends Pageable, K> implements ServiceBase<T, K> {

    private static final Logger LOG = LoggerFactory.getLogger(SessionHelper.class);

    public void save(T entity) {
        if (entity instanceof Updatable) {
            Updatable updatable = (Updatable) entity;
            updatable.setUpdateVersion(0L);
        }
        entity.initForClearNull();
        getDAO().save(entity);
    }

    public void saveOrUpdate(T entity) {
        if (entity.getId() == null || entity.getId() == 0) {
            save(entity);
        }else{
            update(entity);
        }
    }
    
    public T get(K key) {
        if (ValueUtil.isEmpty(key)) {
            return null;
        }
        return getDAO().get(key);
    }
    
    @Override
    public int count(T example) {
        return getDAO().count(example);
    }
    
    public List<T> query(T example) {
        return getDAO().query(example);
    }
    
    public List<T> queryAll() {
        return getDAO().query(null);
    }
    
    @Override
    public List<T> queryWithPagination(T example, Pagination page) {
        int count = count(example);
        if (null == page) {
            page = example.getPage();
        }
        page.setTotalItemNum(count);
        page.calc();
        example.setPage(page);
        return query(example);
    }
    
    
    public T setCountPage(T example){
    	 int count = count(example);
    	 Pagination page = example.getPage();
         page.setTotalItemNum(count);
         page.calc();
         example.setPage(page);
         return example;
    }
    
    
    public int updateCAS(T entity) {
        return updateCAS(entity, true);
    }
    
    public int updateCAS(T entity, boolean throwException) {
        int updateNum = update(entity);
        if (0 == updateNum) {
            if (throwException) {
                throw new BaseBusinessException(BaseErrorCode.CONCURRENT_MODIFY);
            } else {
                LOG.error("concurrent modify: " + entity);
            }
        } else {
            if (entity instanceof Updatable) {
                Updatable updatable = (Updatable) entity;
                long updateVersion = ValueUtil.getLong(updatable);
                updatable.setUpdateVersion(updateVersion + 1);
            }
        }
        return updateNum;
    }
    
    public int update(T entity) {
        return getDAO().update(entity);
    }
    
    public int delete(K key) {
        return getDAO().delete(key);
    }
    
    protected abstract DAOBase<T, K> getDAO();
    
}
