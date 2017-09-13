package com.xcz.afcs.mybatis.dao;

import com.xcz.afcs.mybatis.model.EntityCriteria;
import com.xcz.afcs.mybatis.provider.BaseSQLProvider;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 2017/8/11.
 */
public interface BaseDao<T extends Serializable, K> {

    @SelectProvider(type = BaseSQLProvider.class, method = "findAllSQL")
    List<T> findAll(Class<T> entityCls);

    @SelectProvider(type = BaseSQLProvider.class, method = "countSQL")
    int count(EntityCriteria<T> criteria);

    @SelectProvider(type = BaseSQLProvider.class, method = "querySQL")
    List<T> query(EntityCriteria<T> criteria);

    @SelectProvider(type = BaseSQLProvider.class, method = "queryViewSQL")
    <V> List<V> queryView(EntityCriteria<T> criteria);

    @InsertProvider(type = BaseSQLProvider.class, method = "insertSQL")
    void save(T entity);

    @InsertProvider(type = BaseSQLProvider.class, method = "batchInsertSQL")
    void batchSave(@Param("entityList") List<T> entityList);

    @SelectProvider(type = BaseSQLProvider.class, method = "getOneSQL")
    T getOne(@Param("id") K id, @Param("entityClass") Class<T> entityCls);

    @UpdateProvider(type = BaseSQLProvider.class, method = "updateSQL")
    int update(T entity);

    @UpdateProvider(type = BaseSQLProvider.class, method = "updateCasSQL")
    int updateCas(T entity);

    @DeleteProvider(type = BaseSQLProvider.class, method = "deleteSQL")
    int delete(@Param("id") K id, @Param("entityClass") Class<T> entityCls);

}
