/*
 * Author: LovelyCheng   Time:17-10-10 上午9:49
 */

package com.xcz.afcs.mybatis.plugin;

import com.xcz.afcs.mybatis.model.EntityCriteria;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jingang on 2017/9/30.
 */
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class MybatisQueryInterceptor implements Interceptor{

    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<ResultMapping>(0);

    private static final Map<String, MappedStatement> mappedStatementMap = new ConcurrentHashMap<>();

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        if (args[1] instanceof EntityCriteria) {
            EntityCriteria entityCriteria = (EntityCriteria) args[1];
            Class<?> viewCls   = entityCriteria.getResultViewCls();
            Class<?> resultType = entityCriteria.getEntityClass();
            if (ms.getId().endsWith(".query")) {
                 if (viewCls != null) {
                     resultType = viewCls;
                 }
                String shortName = resultType.getSimpleName();
                MappedStatement newMs = mappedStatementMap.get(ms.getId()+"-"+shortName);
                if (newMs == null) {
                    newMs = newMappedStatement(ms, resultType);
                    mappedStatementMap.put(ms.getId()+"-"+shortName, newMs);
                }
                args[0] = newMs;
            }
        }
        return invocation.proceed();
    }

    public MappedStatement newMappedStatement(MappedStatement ms, Class resultType) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), ms.getSqlSource(), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        //count查询返回值int
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), resultType, EMPTY_RESULTMAPPING).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }


}
