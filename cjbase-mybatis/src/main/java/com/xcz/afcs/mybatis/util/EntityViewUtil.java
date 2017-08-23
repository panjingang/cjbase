package com.xcz.afcs.mybatis.util;

import com.xcz.afcs.api.view.BaseView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jingang on 2017/8/18.
 */
public class EntityViewUtil {

    private static Map<String, List<String>> modelCache = new ConcurrentHashMap<String, List<String>>();

    public static <T> List<String> parseEntityViewField(Class<T> entityViewCls) {
        if (!BaseView.class.isAssignableFrom(entityViewCls)) {
            throw new RuntimeException(entityViewCls.getName() + "必须继承BaseView");
        }
        List<String> fieldNames = modelCache.get(entityViewCls.getName());
        if (fieldNames != null) {
            return fieldNames;
        }
        fieldNames = new ArrayList<String>();
        parseSuperEntityView(entityViewCls, fieldNames);
        modelCache.put(entityViewCls.getName(), fieldNames);
        return fieldNames;
    }

    private static void parseSuperEntityView(Class cls,  List<String> fieldNames) {
        if (cls == Object.class) {
            return;
        }
        try {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                fieldNames.add(fieldName);
            }
        }catch (Exception e) {
            throw new RuntimeException("解析实体Bean出错");
        }
        parseSuperEntityView(cls.getSuperclass(), fieldNames);
    }
}
