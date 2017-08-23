package com.xcz.afcs.util;

import com.xcz.afcs.util.enums.BasicType;

/**
 * Created by jingang on 2017/6/22.
 */
public class ClassUtil {

    public static <T> Class<T> getClass(T obj){
        return ((null == obj) ? null : (Class<T>)obj.getClass());
    }

    public static <T> String getSimpleClassName(T obj) {
        Class<T> cls = getClass(obj);
        if (cls == null) {
            return null;
        }
        return cls.getSimpleName();
    }

    public static <T> String getClassName(T obj) {
        Class<T> cls = getClass(obj);
        if (cls == null) {
            return null;
        }
        return cls.getName();
    }

    /**
     * @return 当前线程的class loader
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获得class loader<br>
     * <p/>
     * 若当前线程class loader不存在，取当前类的class loader
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassUtil.class.getClassLoader();
            if(null == classLoader){
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    /**
     * 是否为包装类型
     *
     * @param clazz 类
     * @return 是否为包装类型
     */
    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return BasicType.wrapperPrimitiveMap.containsKey(clazz);
    }

    /**
     * 是否为基本类型（包括包装类和原始类）
     *
     * @param clazz 类
     * @return 是否为基本类型
     */
    public static boolean isBasicType(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
    }

    /**
     * 检查目标类是否可以从原类转化<br>
     * <p/>
     * 转化包括：<br>
     * <p/>
     * 1、原类是对象，目标类型是原类型实现的接口<br>
     * <p/>
     * 2、目标类型是原类型的父类<br>
     * <p/>
     * 3、两者是原始类型或者包装类型（相互转换）
     *
     * @param targetType 目标类型
     * @param sourceType 原类型
     * @return 是否可转化
     */
    public static boolean isAssignable(Class<?> targetType, Class<?> sourceType) {
        if (null == targetType || null == sourceType) {
            return false;
        }
        // 对象类型
        if (targetType.isAssignableFrom(sourceType)) {
            return true;
        }
        // 基本类型
        if (targetType.isPrimitive()) {
            // 原始类型
            Class<?> resolvedPrimitive = BasicType.wrapperPrimitiveMap.get(sourceType);
            if (resolvedPrimitive != null && targetType.equals(resolvedPrimitive)) {
                return true;
            }
        } else {
            // 包装类型
            Class<?> resolvedWrapper = BasicType.primitiveWrapperMap.get(sourceType);
            if (resolvedWrapper != null && targetType.isAssignableFrom(resolvedWrapper)) {
                return true;
            }
        }
        return false;
    }


}
