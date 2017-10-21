package com.xcz.afcs.util;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by jingang on 2017/6/21.
 */
public class ObjectUtil {

    public static boolean isNull(Collection<?> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNull(Object[] list) {
        if (list == null || list.length == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNull(Object object) {
        return object == null ? true : false;
    }

    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }


    public static  boolean equals(Object src, Object dest) {
        if (src == dest) {
            return true;
        }
        if (src == null || dest == null) {
            return false;
        }
        return src.equals(dest);
    }

    public static <T> boolean isSerializable(T object) {
        if (object == null || false == (object instanceof Serializable)) {
            return false;
        }
        return true;
    }




}
