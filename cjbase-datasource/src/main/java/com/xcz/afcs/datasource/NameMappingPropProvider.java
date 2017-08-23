package com.xcz.afcs.datasource;

public interface NameMappingPropProvider {
    
     String determineCurrentLookupKey();

     DataSourceConfig getDataSourceConfig(String routerKey);

}
