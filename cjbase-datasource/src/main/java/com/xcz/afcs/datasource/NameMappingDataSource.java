package com.xcz.afcs.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.xcz.afcs.core.enums.BaseErrorCode;
import com.xcz.afcs.core.exception.BaseBusinessException;
import com.xcz.afcs.util.ValueUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NameMappingDataSource extends DruidDataSource {

    private static final Logger LOG = LoggerFactory.getLogger(NameMappingDataSource.class);

    private NameMappingPropProvider nameMappingPropProvider;

    protected Map<String, DruidDataSource> dsMap = new HashMap<String, DruidDataSource>();

    protected Lock dsMapLock = new ReentrantLock();

    public DruidDataSource getDataSource(String routerKey) {
        DruidDataSource ds = dsMap.get(routerKey);
        if (null == ds) {
            dsMapLock.lock();
            try {
                ds = dsMap.get(routerKey);
                if (null == ds) {
                    if (nameMappingPropProvider == null) {
                        LOG.warn("未定义 NameMappingPropProvider routerKey " + routerKey);
                        throw new BaseBusinessException(BaseErrorCode.DATASOURCE_ERROR);
                    }
                    DataSourceConfig config = nameMappingPropProvider.getDataSourceConfig(routerKey);
                    if (config == null) {
                        LOG.warn("未定义 NameMappingPropProvider routerKey " + routerKey);
                        throw new BaseBusinessException(BaseErrorCode.DATASOURCE_ERROR);
                    }
                    ds = createDataSource(routerKey, config);
                    if (testDataSource(ds)) {
                        dsMap.put(routerKey, ds);
                    }else{
                        throw new BaseBusinessException(BaseErrorCode.DATASOURCE_ERROR);
                    }
                }
            } finally {
                dsMapLock.unlock();
            }
        }
        return ds;
    }

    private boolean testDataSource(DataSource dataSource) {
        if (dataSource == null) {
            return false;
        }
        Connection conn = null;
        try{
            conn = dataSource.getConnection();
            return conn == null ? false : true;
        }catch (Exception e) {
           LOG.error("测试链接异常", e);
        }
        finally {
            if (conn != null) {
                try{
                    conn.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    private DruidDataSource createDataSource(String routerKey, DataSourceConfig config) {
         DruidDataSource dataSource = new DruidDataSource();
         //driverClassName
         String driverClassName = config.getDriverClassName();
         if (StringUtils.isBlank(driverClassName)) {
             LOG.warn("driverClassName for " + routerKey + " is required");
             throw new BaseBusinessException(BaseErrorCode.DATASOURCE_ERROR);
         }
        dataSource.setDriverClassName(driverClassName);

        // url
        String url = config.getUrl();
        if (StringUtils.isBlank(config.getUrl())) {
            LOG.warn("url for " + routerKey + " is required");
            throw new BaseBusinessException(BaseErrorCode.DATASOURCE_ERROR);
        }
        dataSource.setUrl(config.getUrl());

        // username
        String username = config.getUsername();
        if (StringUtils.isBlank(username)) {
            LOG.warn(" username for " + routerKey + " is required");
            throw new BaseBusinessException(BaseErrorCode.DATASOURCE_ERROR);
        }
        dataSource.setUsername(username);

        // password
        String password = config.getPassword();
        if (null == password) {
            LOG.warn(" password for " + routerKey + " is required");
            throw new BaseBusinessException(BaseErrorCode.DATASOURCE_ERROR);
        }
        dataSource.setPassword(password);

        // initialSize
        int initialSize = ValueUtil.getInt(config.getInitialSize(), this.initialSize);
        dataSource.setInitialSize(initialSize);

        // maxActive
        int maxActive = ValueUtil.getInt(config.getMaxActive(), this.maxActive);
        dataSource.setMaxActive(maxActive);

        // maxIdle
        int maxIdle = ValueUtil.getInt(config.getMaxIdle(), this.maxIdle);
        dataSource.setMaxIdle(maxIdle);

        // minIdle
        int minIdle = ValueUtil.getInt(config.getMinIdle(), this.minIdle);
        dataSource.setMinIdle(minIdle);

        // maxWait
        long maxWait = ValueUtil.getLong(config.getMaxWait(), this.maxWait);
        dataSource.setMaxWait(maxWait);

        // removeAbandoned
        Boolean removeAbandoned = ValueUtil.getBooleanObj(config.getRemoveAbandoned(), this.removeAbandoned);
        dataSource.setRemoveAbandoned(removeAbandoned);

        // removeAbandonedTimeout
        int removeAbandonedTimeout = ValueUtil.getInt(config.getRemoveAbandonedTimeout(), 60000);
        dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);


        //testWhileIdle
        Boolean testWhileIdle = ValueUtil.getBooleanObj(config.getTestWhileIdle());
        if (null != testWhileIdle) {
            dataSource.setTestWhileIdle(testWhileIdle);
        }

        //validationQuery
        String validationQuery = ValueUtil.getString(config.getValidationQuery());
        if (StringUtils.isBlank(validationQuery)) {
            validationQuery = this.validationQuery;
        }
        dataSource.setValidationQuery(validationQuery);

        // timeBetweenEvictionRunsMillis
        long timeBetweenEvictionRunsMillis = ValueUtil.getLong(config.getTimeBetweenEvictionRunsMillis(), this.timeBetweenEvictionRunsMillis);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        // minEvictableIdleTimeMillis
        long minEvictableIdleTimeMillis = ValueUtil.getLong(config.getMinEvictableIdleTimeMillis(), this.minEvictableIdleTimeMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        // connectionProperties
        String connProperties = ValueUtil.getString(config.getConnectionProperties());
        if (StringUtils.isNotBlank(connProperties)) {
            dataSource.setConnectionProperties(connProperties);
        }

        // connectionProperties
        String filters = ValueUtil.getString(config.getFilters(), null);
        if (StringUtils.isNotBlank(filters)) {
            try {
                dataSource.setFilters(filters);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOG.info("create "+routerKey+" datasource:" + dataSource);
        return dataSource;

    }

    @Override
    public DruidPooledConnection getConnection() throws SQLException {
        return getKeySpecifiedDataSource().getConnection();
    }

    protected DruidDataSource getKeySpecifiedDataSource() {
        if (nameMappingPropProvider == null) {
            throw new IllegalArgumentException("NameMappingPropProvider 为NULL， 请设置相应的数据源配置");
        }
        String key = nameMappingPropProvider.determineCurrentLookupKey();
        return getDataSource(key);
    }

    public void removeDataSource(String sourceName) throws SQLException {
        dsMapLock.lock();
        try {
            DruidDataSource ds = dsMap.remove(sourceName);
            if (null != ds) {
                ds.close();
            }
        } finally {
            dsMapLock.unlock();
        }
    }

    public void refreshDataSource(String sourceName) throws SQLException {
        LOG.info("...reloading datasource of " + sourceName);
        dsMapLock.lock();
        try {
            removeDataSource(sourceName);
            getDataSource(sourceName);
        } finally {
            dsMapLock.unlock();
        }
        LOG.info("...reloaded datasource of " + sourceName);
    }

    public NameMappingPropProvider getNameMappingPropProvider() {
        return nameMappingPropProvider;
    }

    public void setNameMappingPropProvider(NameMappingPropProvider nameMappingPropProvider) {
        this.nameMappingPropProvider = nameMappingPropProvider;
    }
}
