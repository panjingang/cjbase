package com.xcz.afcs.api.util;

import com.xcz.afcs.core.base.StatusEnumMessage;
import com.xcz.afcs.core.base.TypeEnumMessage;
import com.xcz.afcs.util.ValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EnumValueUtil {

    private static final Logger LOG = LoggerFactory.getLogger(EnumValueUtil.class);

    private static final Map<String, Map<String, String>> enumMap = new ConcurrentHashMap<>();

    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    public static <T extends StatusEnumMessage> String getStatusEnumMessage(Class<T> enumCls, Integer status) {
        Map<String, String> enums = getEnumValues(enumCls);
        String message = enums.get(String.valueOf(status));
        return ValueUtil.getString(message);
    }

    public static <T extends TypeEnumMessage> String getTypeEnumMessage(Class<T> enumCls, String type) {
        Map<String, String> enums = getEnumValues(enumCls);
        String message = enums.get(type);
        return ValueUtil.getString(message);
    }

    public static Map<String, String> getEnumValues(Class cls) {
        Map<String, String> enums = enumMap.get(cls.getName());
        if (enums != null) {
            return enums;
        }

        try {
            lock.readLock().lock();
            enums = new LinkedHashMap<>();
            Class[] interfaces = cls.getInterfaces();
            if (interfaces == null) {
                return enums;
            }
            try {
                Method method = cls.getMethod("values");
                boolean type = false;
                for (Class inter : interfaces) {
                    if ("TypeEnumMessage".equals(inter.getSimpleName())) {
                        type = true;
                    }
                }
                if (type) {
                    TypeEnumMessage inter[] = (TypeEnumMessage[]) method.invoke(null, null);
                    for (TypeEnumMessage enumMessage : inter) {
                        enums.put(enumMessage.getValue(), enumMessage.getMessage());
                    }
                } else {
                    StatusEnumMessage inter[] = (StatusEnumMessage[]) method.invoke(null, null);
                    for (StatusEnumMessage enumMessage : inter) {
                        enums.put(String.valueOf(enumMessage.getValue()), enumMessage.getMessage());
                    }
                }
                enumMap.put(cls.getName(), enums);
            } catch (Exception e) {
                LOG.error("处理枚举出错", e);
            }
        }
        finally {
            lock.readLock().unlock();
        }
        return enums;
    }

}
