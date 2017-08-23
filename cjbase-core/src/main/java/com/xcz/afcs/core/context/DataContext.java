package com.xcz.afcs.core.context;

import com.xcz.afcs.core.model.SessionIdentity;

import java.util.HashMap;
import java.util.Map;

public class DataContext {
    
    public static final String NAMESPACE = DataContext.class.getName();

    public static final String SESSION_IDENTITY = NAMESPACE + ".session_identity";
    public static final String TENANT_ID        = NAMESPACE + ".tenantId";


    protected static ThreadLocal<DataContext> dataContext = new ThreadLocal<DataContext>() {
        protected DataContext initialValue() {
            return new DataContext();
        }
    };
    
    private Map<String, Object> store = new HashMap<>();

    protected static DataContext getDataContext() {
        return dataContext.get();
    }
    
    public static Map<String, Object> getStore() {
        return getDataContext().store;
    }
    
    public static Object get(String key) {
        return getStore().get(key);
    }
    
    public static Object put(String key, Object value) {
        return getStore().put(key, value);
    }
    
    public static Object remove(String key) {
        return getStore().remove(key);
    }
    
    public static void clear() {
        getStore().clear();
    }


    public static SessionIdentity getSessionIdentity() {
        return (SessionIdentity)get(SESSION_IDENTITY);
    }

    public static String getTenantId(){
        return (String)get(TENANT_ID);
    }
}
