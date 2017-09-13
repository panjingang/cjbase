package com.xcz.afcs.mybatis.service;

import com.xcz.afcs.mybatis.dao.BaseDao;
import com.xcz.afcs.mybatis.entity.UpdatableEntity;
import com.xcz.afcs.mybatis.model.EntityCriteria;
import com.xcz.afcs.util.ObjectUtil;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by mac on 2017/8/12.
 */
public abstract class BaseService<T extends UpdatableEntity, K> {

    private Class<T> entityClass = null;

    public BaseService() {
        entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    //最大获取500条数据
    public List<T> findAll(){
        return getDAO().findAll(entityClass);
    }

    public int count(EntityCriteria<T> criteria) {
        return getDAO().count(criteria);
    }

    public List<T> query(EntityCriteria<T> criteria) {
        return getDAO().query(criteria);
    }

    public void save(T entity) {
        getDAO().save(entity);
    }

    public void batchSave(List<T> entityList) {
        getDAO().batchSave(entityList);
    }

    public void saveOrUpdate(T entity) {
        if (entity.getPrimaryId() == null) {
            save(entity);
        }else{
            updateCas(entity);
        }
    }

    public T get(EntityCriteria<T> criteria) {
        List<T> list = query(criteria);
        if (ObjectUtil.isNull(list)) {
            return null;
        }
        return list.get(0);
    }

    public T getOne(K id) {
       return getDAO().getOne(id, entityClass);
    }

    public int update(T entity) {
        return getDAO().update(entity);
    }

    public int updateCas(T entity) {
        return getDAO().updateCas(entity);
    }

    public int delete(K id){
        return getDAO().delete(id, entityClass);
    }

    protected abstract BaseDao<T, K> getDAO();


}
