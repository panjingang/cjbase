package com.xcz.afcs.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AfbpProperties {
    
    public static final String PROP_NAME_PREFIX = "afbp.";
    
    private static final String FILE_PATH_ENV = "AFBP_PROPERTIES";
    
    private static final String FILE_NAME = "afbp.properties";

    private static final Logger LOG = LoggerFactory.getLogger(AfbpProperties.class);

    private static Properties prop;
    
    static {
        init();
    }
    
    private static void init() {
        String filePath = System.getenv(FILE_PATH_ENV);
        InputStream is = null;
        try {
            if (StringUtils.isBlank(filePath)) {
                filePath = EnvironmentUtils.getClasspathFilePath(FILE_NAME);
            }
            if (StringUtils.isBlank(filePath)) {
                LOG.warn("Can not find afbp properties, it may result working problem. path: " + filePath);
                return ;
            }
            is = new FileInputStream(filePath);
            prop = new Properties();
            prop.load(is);
        } catch (FileNotFoundException e) {
            LOG.error("Can not find afbp properties, it may result working problem. path: " + filePath, e);
        } catch (Exception e) {
            LOG.error("init afbp properties failed", e);
        } finally {
            IOUtil.close(is);
        }
    }

    public static void loadProperties(String filename) {
        InputStream is  = null;
        String filePath = null;
        try {
            filePath = EnvironmentUtils.getClasspathFilePath(filename);
            is = new FileInputStream(filePath);
            if (prop == null) {
                prop = new Properties();
            }
            prop.load(is);
        } catch (FileNotFoundException e) {
            LOG.error("Can not find "+filename+", it may result working problem. path: " + filePath, e);
        } catch (Exception e) {
            LOG.error("init "+filename+" failed", e);
        } finally {
            IOUtil.close(is);
        }
    }


    public static String getProperty(String key) {
        if (null != prop) {
            return prop.getProperty(key);
        }
        return null;
    }

    public static Map<String,String> getPropertySuffix(String suffix) {
        if (null != prop) {
            Map<String,String> map = new HashMap<>();
            for (Object o : prop.keySet()) {
                String string =  o.toString();
                if(string.endsWith(suffix)){
                    map.put(string,prop.getProperty(string));
                }
            }
           return map;
        }
        return null;
    }
    
    public static String getProperty(String key, String defaultValue) {
        if (null != prop) {
            return prop.getProperty(key, defaultValue);
        }
        return defaultValue;
    }
    
    
    public static void setProperty(String key, String value) {
    	if (null != prop) {
    		prop.setProperty(key, value);
    	}
    }
    
    
    
}
