package com.xcz.afcs.mybatis.provider;

import com.xcz.afcs.mybatis.entity.UserEntity;
import com.xcz.afcs.mybatis.model.EntityCriteria;
import com.xcz.afcs.mybatis.model.Expression;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jingang on 2017/10/13.
 */
public class BaseSQLProviderTest {

     @Test
     public void findAllSQL() {
         String sql = new BaseSQLProvider().findAllSQL(UserEntity.class);
         System.out.println(sql);
     }

    @Test
    public void countSQL() {
        EntityCriteria entityCriteria = new EntityCriteria(UserEntity.class);
        entityCriteria.add(Expression.eq("userId", ""));
        entityCriteria.add(Expression.eq("userName", "测试"));
        String sql = new BaseSQLProvider().countSQL(entityCriteria);
        System.out.println(sql);
    }

    @Test
    public void querySQL() {
        EntityCriteria entityCriteria = new EntityCriteria(UserEntity.class);
        entityCriteria.add(Expression.eq("userId", ""));
        entityCriteria.add(Expression.eq("userName", "测试"));
        String sql = new BaseSQLProvider().querySQL(entityCriteria);
        System.out.println(sql);
    }


    @Test
    public void querySQL1() {
        EntityCriteria entityCriteria = new EntityCriteria(UserEntity.class);
        List columns = Arrays.asList("userId, userName nickName", "createTime");
        entityCriteria.setSelectColumns(columns);
        entityCriteria.add(Expression.eq("userId", ""));
        entityCriteria.add(Expression.eq("userName", "测试"));
        String sql = new BaseSQLProvider().querySQL(entityCriteria);
        System.out.println(sql);
    }


}
