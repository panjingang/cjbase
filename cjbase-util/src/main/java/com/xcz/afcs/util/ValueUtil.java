package com.xcz.afcs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

public class ValueUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ValueUtil.class);

    public static String getString(Object object, String defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        return String.valueOf(object);
    }
    
    public static String getString(Object object) {
        return getString(object, "");
    }
    
    public static int getInt(Object object, int defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static int getInt(Object object) {
        return getInt(object, 0);
    }
    
    public static long getLong(Object object, long defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }
        try {
            return Long.parseLong(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static long getLong(Object object) {
        return getLong(object, 0);
    }
    
    public static double getDouble(Object object) {
        return getDouble(object, 0);
    }
    
    public static double getDouble(Object object, double defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static BigDecimal getBigDecimal(Object object) {
          if (null == object) {
              return new BigDecimal(0);
          }
          if (object instanceof BigDecimal){
              return (BigDecimal)object;
          }
          return new BigDecimal(String.valueOf(object));
    }
    
    public static boolean getBoolean(Object object, boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        return getBooleanObj(String.valueOf(object));
    }
    
    public static boolean getBoolean(Object object) {
        return getBoolean(object, false);
    }
    
    public static Boolean getBooleanObj(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        return "true".equalsIgnoreCase(String.valueOf(object));
    }
    
    public static Boolean getBooleanObj(Object object) {
        return getBooleanObj(object, null);
    }
    
    public static String getString(Map<String, Object> map, String key, String defaultValue) {
    	if (map == null) {
    		return defaultValue;
    	}
        return getString(map.get(key), defaultValue);
    }
    
    public static String getString(Map<String, Object> map, String key) {
        return getString(map.get(key));
    }
    
    public static int getInt(Map<String, Object> map, String key, int defaultValue) {
    	if (map == null) {
    		return defaultValue;
    	}
        return getInt(map.get(key), defaultValue);
    }
    
    public static int getInt(Map<String, Object> map, String key) {
        return getInt(map.get(key));
    }
    
    public static long getLong(Map<String, Object> map, String key, long defaultValue) {
    	if (map == null) {
    		return defaultValue;
    	}
        return getLong(map.get(key), defaultValue);
    }
    
    public static long getLong(Map<String, Object> map, String key) {
        return getLong(map.get(key));
    }
    
    public static double getDouble(Map<String, Object> map, String key, double defaultValue) {
    	if (map == null) {
    		return defaultValue;
    	}
        return getDouble(map.get(key), defaultValue);
    }
    
    public static double getDouble(Map<String, Object> map, String key) {
        return getDouble(map.get(key));
    }
    
    public static boolean getBoolean(Map<String, Object> map, String key, boolean defaultValue) {
    	if (map == null) {
    		return defaultValue;
    	}
        return getBoolean(map.get(key), defaultValue);
    }
    
    public static boolean getBoolean(Map<String, Object> map, String key) {
        return getBoolean(map.get(key));
    }
    
    public static Boolean getBooleanObj(Map<String, Object> map, String key, Boolean defaultValue) {
    	if (map == null) {
    		return defaultValue;
    	}
        return getBooleanObj(map.get(key), defaultValue);
    }
    
    public static Boolean getBooleanObj(Map<String, Object> map, String key) {
        return getBooleanObj(map.get(key));
    }
    
    public static boolean isEmpty(Object o) {
        if (null == o) {
            return true;
        } else {
            if (o instanceof String) {
                return ((String) o).isEmpty();
            } else if (o instanceof Collection) {
                return ((Collection<?>) o).isEmpty();
            } else if (o instanceof Map) {
                return ((Map<?, ?>) o).isEmpty();
            } else if (o.getClass().isArray()) {
                return 0 == Array.getLength(o);
            } else if (o instanceof Iterator) {
                return !((Iterator<?>) o).hasNext();
            } else if (o instanceof Enumeration) {
                return !((Enumeration<?>) o).hasMoreElements();
            }
            return false;
        }
    }
    
    public static int countTrue(boolean... conditions) {
        int c = 0;
        for (boolean condition : conditions) {
            c += condition ? 1 : 0;
        }
        return c;
    }
    
    public static int countFalse(boolean... conditions) {
        int c = 0;
        for (boolean condition : conditions) {
            c += condition ? 0 : 1;
        }
        return c;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, Object> getMap(Map<String, Object> map, String key) {
        try {
            Object value = map.get(key);
            if (value instanceof Map) {
                return (Map<String, Object>) value;
            } else if (value instanceof List) {
                List list = (List) value;
                if (!list.isEmpty()) {
                    Object element = list.get(0);
                    if (element instanceof Map) {
                        return (Map<String, Object>) element;
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("getMap error", e);
        }
        return null;
    }
    
}
