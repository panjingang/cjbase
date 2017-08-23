package com.xcz.afcs.datasource;

public class DataSourceConfig {

    private String driverClassName;
    
    private String url;
    
    private String username;
    
    private String password;
    
    private Integer maxActive;
    
    private Integer initialSize;
    
    private Long maxWait;
    
    private Integer maxIdle;
    
    private Integer minIdle;
    
    private Boolean removeAbandoned;
    
    private Integer removeAbandonedTimeout;
    
    private Long timeBetweenEvictionRunsMillis;
    
    private Long minEvictableIdleTimeMillis;

    private Boolean testWhileIdle;

    private String validationQuery;
    
    private String connectionProperties;

    private String filters;
    
    public String getDriverClassName() {
        return driverClassName;
    }
    
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Integer getMaxActive() {
        return maxActive;
    }
    
    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }
    
    public Integer getInitialSize() {
        return initialSize;
    }
    
    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }
    
    public Long getMaxWait() {
        return maxWait;
    }
    
    public void setMaxWait(Long maxWait) {
        this.maxWait = maxWait;
    }
    
    public Integer getMaxIdle() {
        return maxIdle;
    }
    
    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }
    
    public Integer getMinIdle() {
        return minIdle;
    }
    
    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }
    
    public Boolean getRemoveAbandoned() {
        return removeAbandoned;
    }
    
    public void setRemoveAbandoned(Boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }
    
    public Integer getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }
    
    public void setRemoveAbandonedTimeout(Integer removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }
    
    public Long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }
    
    public void setTimeBetweenEvictionRunsMillis(Long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }
    
    public Long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }
    
    public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }
    
    public String getConnectionProperties() {
        return connectionProperties;
    }
    
    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public Boolean getTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(Boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DataSourceConfig [driverClassName=").append(driverClassName).append(", url=").append(url)
                .append(", username=").append(username).append(", password=").append("******")
                .append(", maxActive=").append(maxActive).append(", initialSize=").append(initialSize)
                .append(", maxWait=").append(maxWait).append(", maxIdle=").append(maxIdle).append(", minIdle=")
                .append(minIdle).append(", removeAbandoned=").append(removeAbandoned)
                .append(", removeAbandonedTimeout=").append(removeAbandonedTimeout)
                .append(", timeBetweenEvictionRunsMillis=").append(timeBetweenEvictionRunsMillis)
                .append(", minEvictableIdleTimeMillis=").append(minEvictableIdleTimeMillis)
                .append(", testWhileIdle=").append(testWhileIdle)
                .append(", validationQuery=").append(validationQuery)
                .append(", connectionProperties=").append(connectionProperties).append("]");
        return builder.toString();
    }

}
