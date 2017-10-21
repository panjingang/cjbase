package com.xcz.afcs.util;

import java.math.BigDecimal;

/**
 * Created by jingang on 2017/10/21.
 */
public class BigDecimalUtil {

    public static boolean isNullOrZero(final BigDecimal value) {
        return value == null || value.signum() == 0;
    }

    public static boolean isZero(BigDecimal value) {
        return value != null && value.signum() == 0;
    }

    public static boolean isNotZero(BigDecimal value) {
        return value != null && value.signum() != 0;
    }

    public static boolean isSameValue(final BigDecimal val1, final BigDecimal val2) {
        return val1 == null && val2 == null || val1 != null && val2 != null && val1.compareTo(val2) == 0;
    }

    public static BigDecimal subtract(final BigDecimal start, final BigDecimal... values) {
        BigDecimal total = start != null ? start : BigDecimal.ZERO;
        if (values != null) {
            for (final BigDecimal v : values) {
                total = doSubtract(total, v);
            }
        }
        return total;
    }

    private static BigDecimal doSubtract(final BigDecimal v1, final BigDecimal v2) {
        BigDecimal diff = v1;
        if (v1 != null && v2 != null) {
            diff = v1.subtract(v2);
        } else if (v2 != null) {
            diff = v2.negate();
        }
        return diff;
    }

    public static BigDecimal divide(final BigDecimal numerator, final BigDecimal denominator){
        BigDecimal diff = BigDecimal.ZERO;
        if (numerator != null && isNotZero(denominator)) {
            diff = numerator.divide(denominator);
        }
        return diff;
    }

    public static BigDecimal divide(final BigDecimal numerator, final BigDecimal denominator, final int rounding) {
        BigDecimal diff = BigDecimal.ZERO;
        if (numerator != null && isNotZero(denominator)) {
            diff = numerator.divide(denominator, rounding);
        }
        return diff;
    }

    public static BigDecimal multiply(final BigDecimal value, final BigDecimal multiplicand) {
        BigDecimal diff = BigDecimal.ZERO;
        if (value != null && multiplicand != null) {
            diff = value.multiply(multiplicand);
        }
        return diff;
    }

    public static BigDecimal multiply(final BigDecimal value, final BigDecimal... multiplicand) {
        BigDecimal diff = BigDecimal.ZERO;
        if (value != null && multiplicand != null) {
            diff = value;
            for (final BigDecimal bd : multiplicand) {
                if (bd != null) {
                    diff = diff.multiply(bd);
                }
            }
        }
        return diff;
    }

    public static BigDecimal abs(final BigDecimal value) {
        return value != null ? value.abs() : null;
    }

    public static BigDecimal negate(final BigDecimal value) {
        return value != null ? value.negate() : null;
    }

    public static int compareTo(final BigDecimal v1, final BigDecimal v2) {
        int ret = 1;
        if (v1 != null && v2 != null) {
            ret = v1.compareTo(v2);
        }else if (v1 == null && v2 == null) {
            ret = 0;
        }else if (v1 == null) {
            ret = -1;
        }
        return ret;
    }

    public static int absCompareTo(final BigDecimal v1, final BigDecimal v2) {
        return compareTo(abs(v1), abs(v2));
    }

    public static BigDecimal min(final BigDecimal...v1) {
        if (v1 == null) {
            return null;
        }
        BigDecimal min = null;
        for (final BigDecimal bd : v1) {
            min = BigDecimalUtil.compareTo(min, bd) < 0 ? min : bd;
        }
        return min;
    }

    public static BigDecimal max(final BigDecimal...v1) {
        if (v1 == null) {
            return null;
        }
        BigDecimal max = null;
        for (final BigDecimal bd : v1) {
            max = BigDecimalUtil.compareTo(max, bd) >= 0 ? max : bd;
        }
        return max;
    }

    public static BigDecimal etScale(final BigDecimal bd, final Integer scale, final int rounding) {
        if (bd != null && scale != null) {
            return bd.setScale(scale, rounding);
        }
        return null;
    }





}
