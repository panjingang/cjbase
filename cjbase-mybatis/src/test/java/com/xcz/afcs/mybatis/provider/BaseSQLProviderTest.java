package com.xcz.afcs.mybatis.provider;

import com.xcz.afcs.mybatis.entity.UserEntity;
import com.xcz.afcs.mybatis.model.EntityCriteria;
import com.xcz.afcs.mybatis.model.Expression;
import com.xcz.afcs.mybatis.model.Join;
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

    @Test
    public void querySQL2() {
        EntityCriteria entityCriteria = new EntityCriteria(UserEntity.class);
        List columns = Arrays.asList("t1.userId, t1.userName nickName", "t1.createTime");
        entityCriteria.setSelectColumns(columns);
        entityCriteria.setTableName("tb_user t1");
        entityCriteria.add(Join.leftJoin("tb_user_info t2 ON t1.userId = t2.userId"));
        entityCriteria.add(Expression.eq("t1.userId", ""));
        entityCriteria.add(Expression.eq("t1.userName", "测试"));
        String sql = new BaseSQLProvider().querySQL(entityCriteria);
        System.out.println(sql);
    }


}
