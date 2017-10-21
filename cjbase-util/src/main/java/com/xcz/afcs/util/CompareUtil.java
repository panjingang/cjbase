package com.xcz.afcs.util;

import java.math.BigDecimal;

/**
 * Created by jingang on 2017/9/27.
 */
public class CompareUtil {

    public static boolean compare(BigDecimal src, BigDecimal dst) {
        if (src == null || dst == null) {
            throw new IllegalArgumentException("比较的值不能为空");
        }
        return src.compareTo(dst) >= 0 ;
    }


}
