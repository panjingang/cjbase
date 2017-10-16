package com.xcz.afcs.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by jingang on 2016/4/25.
 */
public class FormatUtil {

    //默认小数点2位
    public static final int DEFAULT_DECIMALS = 2;

    /**
     * 格式化金额,四舍五入
     * @return
     */
    public static String formatAmount(BigDecimal amount) {
        return formatAmount(amount, DEFAULT_DECIMALS);
    }


    public static String formatAmount(BigDecimal amount, int decimals) {
        if (amount == null) {
            amount = new BigDecimal(0);
        }
        return getDecimalFormat(decimals).format(amount);
    }


    /**
     * 格式化百分比
     * @param value
     * @return
     */
    public static String formatPercent(BigDecimal value) {
        if (value == null) {
            value = new BigDecimal(0);
        }
        return formatAmount(value.multiply(new BigDecimal(100)));
    }

    private static DecimalFormat getDecimalFormat(int decimals) {
        if (decimals < 0) {
            decimals = 0;
        }
        if (decimals > 10) {
            decimals = 10;
        }
        if (decimals == 0) {
            return new DecimalFormat("0");
        }
        StringBuilder sb = new StringBuilder("0.");
        for(int i=0; i<decimals; i++) {
            sb.append(0);
        }
        return new DecimalFormat(sb.toString());
    }


    public static String formatBigData(String data) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("(length=");
        if (null == data) {
            sBuilder.append(0);
        } else {
            sBuilder.append(data.length());
        }
        sBuilder.append(')');
        return sBuilder.toString();
    }

}


