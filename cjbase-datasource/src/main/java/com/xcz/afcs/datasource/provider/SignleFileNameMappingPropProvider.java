package com.xcz.afcs.datasource.provider;

import com.xcz.afcs.datasource.DataSourceConfig;
import com.xcz.afcs.datasource.NameMappingPropProvider;
import com.xcz.afcs.util.EnvironmentUtils;
import com.xcz.afcs.util.IOUtil;
import com.xcz.afcs.util.ValueUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by jingang on 2017/5/24.
 */
public class SignleFileNameMappingPropProvider implements NameMappingPropProvider {

    private static final Logger LOG = LoggerFactory.getLogger(SignleFileNameMappingPropProvider.class);

    private static final String PROP_KEY_DRIVERCLASSNAME = "driverClassName";

    private static final String PROP_KEY_URL = "url";

    private static final String PROP_KEY_USERNAME = "username";

    private static final String PROP_KEY_PASSWORD = "password";

    private static final String PROP_KEY_MAXACTIVE = "maxActive";

    private static final String PROP_KEY_INITIALSIZE = "initialSize";

    private static final String PROP_KEY_MAXWAIT = "maxWait";

    private static final String PROP_KEY_MAXIDLE = "maxIdle";

    private static final String PROP_KEY_MINIDLE = "minIdle";

    private static final String PROP_KEY_REMOVEABANDONED = "removeAbandoned";

    private static final String PROP_KEY_REMOVEABANDONEDTIMEOUT = "removeAbandonedTimeout";

    private static final String PROP_KEY_TIMEBETWEENEVICTIONRUNSMILLS = "timeBetweenEvictionRunsMillis";

    private static final String PROP_KEY_TESTWHILEIDLE  = "testWhileIdle";

    private static final String PROP_KEY_VALIDATIONQUERY = "validationQuery";

    private static final String PROP_KEY_MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";

    private static final String PROP_KEY_CONNECTIONPROPERTIES = "connectionProperties";

    private static final String PROP_KEY_FITLTERS = "filters";

    private final String routerKey = "signle";

    private String propFilePath;

    @Override
    public String determineCurrentLookupKey() {
        return routerKey;
    }

    public Properties loadProperties() {
        String filePath = EnvironmentUtils.findFileInRuntimeConfigDirOrClasspath(propFilePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            Properties prop = new Properties();
            prop.load(fis);
            return prop;
        } catch (Exception e) {
            LOG.error("load properties failed, file: " + filePath, e);
            throw new RuntimeException("加载配置文件"+filePath+"失败");
        } finally {
            IOUtil.close(fis);
        }
    }

    @Override
    public DataSourceConfig getDataSourceConfig(String routerKey) {
        Properties propMap = loadProperties();
        DataSourceConfig config = new DataSourceConfig();
        String driverClassName = ValueUtil.getString(propMap.get(PROP_KEY_DRIVERCLASSNAME), null);
        if (StringUtils.isBlank(driverClassName)) {
            throw new RuntimeException(PROP_KEY_DRIVERCLASSNAME + " for " + routerKey + " is required");
        }
        config.setDriverClassName(driverClassName);

        // url
        String url = ValueUtil.getString(propMap.get(PROP_KEY_URL), null);
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException(PROP_KEY_URL + " for " + routerKey + " is required");
        }
        config.setUrl(url);

        // username
        String username = ValueUtil.getString(propMap.get(PROP_KEY_USERNAME), null);
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException(PROP_KEY_USERNAME + " for " + routerKey + " is required");
        }
        config.setUsername(username);

        // password
        String password = ValueUtil.getString(propMap.get(PROP_KEY_PASSWORD), null);
        if (null == password) {
            throw new RuntimeException(PROP_KEY_PASSWORD + " for " + routerKey + " is required");
        }
        config.setPassword(password);

        // initialSize
        int initialSize = ValueUtil.getInt(propMap.get(PROP_KEY_INITIALSIZE), 0);
        if (-1 != initialSize) {
            config.setInitialSize(initialSize);
        }

        // maxActive
        int maxActive = ValueUtil.getInt(propMap.get(PROP_KEY_MAXACTIVE), -1);
        if (-1 != maxActive) {
            config.setMaxActive(maxActive);
        }

        // maxIdle
        int maxIdle = ValueUtil.getInt(propMap.get(PROP_KEY_MAXIDLE), -1);
        if (-1 != maxIdle) {
            config.setMaxIdle(maxIdle);
        }

        // minIdle
        int minIdle = ValueUtil.getInt(propMap.get(PROP_KEY_MINIDLE), -1);
        if (-1 != minIdle) {
            config.setMinIdle(minIdle);
        }

        // maxWait
        long maxWait = ValueUtil.getLong(propMap.get(PROP_KEY_MAXWAIT), -1);
        if (-1 != maxWait) {
            config.setMaxWait(maxWait);
        }

        // removeAbandoned
        Boolean removeAbandoned = ValueUtil.getBooleanObj(propMap.get(PROP_KEY_REMOVEABANDONED));
        if (null != removeAbandoned) {
            config.setRemoveAbandoned(removeAbandoned);
        }

        // removeAbandonedTimeout
        int removeAbandonedTimeout = ValueUtil.getInt(propMap.get(PROP_KEY_REMOVEABANDONEDTIMEOUT), -1);
        if (-1 != removeAbandonedTimeout) {
            config.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        }

        //testWhileIdle
        Boolean testWhileIdle = ValueUtil.getBooleanObj(propMap.get(PROP_KEY_TESTWHILEIDLE));
        if (null != testWhileIdle) {
            config.setTestWhileIdle(testWhileIdle);
        }

        //validationQuery
        String validationQuery = ValueUtil.getString(propMap.get(PROP_KEY_VALIDATIONQUERY));
        if (StringUtils.isNotBlank(validationQuery)) {
            config.setValidationQuery(validationQuery);
        }

        // timeBetweenEvictionRunsMillis
        long timeBetweenEvictionRunsMillis = ValueUtil.getLong(
                propMap.get(PROP_KEY_TIMEBETWEENEVICTIONRUNSMILLS), -1);
        if (-1 != timeBetweenEvictionRunsMillis) {
            config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        }


        // minEvictableIdleTimeMillis
        long minEvictableIdleTimeMillis = ValueUtil.getLong(propMap.get(PROP_KEY_MINEVICTABLEIDLETIMEMILLIS), -1);
        if (-1 != minEvictableIdleTimeMillis) {
            config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        }

        // connectionProperties
        String connProperties = ValueUtil.getString(propMap.get(PROP_KEY_CONNECTIONPROPERTIES), null);
        if (StringUtils.isNotBlank(connProperties)) {
            config.setConnectionProperties(connProperties);
        }

        // connectionProperties
        String filters = ValueUtil.getString(propMap.get(PROP_KEY_FITLTERS), null);
        if (StringUtils.isNotBlank(filters)) {
            config.setFilters(filters);
        }
        return config;
    }

    public String getPropFilePath() {
        return propFilePath;
    }

    public void setPropFilePath(String propFilePath) {
        this.propFilePath = propFilePath;
    }
}
