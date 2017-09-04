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

    public static String formatMobile(String mobile) {
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
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

    public static String formatIdNo(String idNo) {
        return printSensitiveKeepTerminal(idNo);
    }

    public static String formatPassword(String password) {
        return "******";
    }

    private static String printSensitiveKeepTerminal(String info) {
        return printSensitiveKeepTerminal(info, 50);
    }

    private static String printSensitiveKeepTerminal(String info, int maskPercentage) {
        if (StringUtils.isEmpty(info)) {
            return "";
        } else {
            if (maskPercentage > 100) {
                maskPercentage = 100;
            } else if (maskPercentage < 0) {
                maskPercentage = 0;
            }
            int fullLength = info.length();
            int maskLength = fullLength * maskPercentage / 100;
            if (0 == maskLength && maskPercentage > 0) {
                maskLength = 1;
            }
            int plainLength = fullLength - maskLength;
            int plainHalfLength = plainLength / 2;
            if (0 == plainHalfLength && maskPercentage < 100) {
                plainHalfLength = 1;
            }
            int maskStart = plainHalfLength;
            int maskEnd = maskStart + maskLength;
            if (maskEnd > fullLength) {
                maskEnd = fullLength;
            }
            StringBuilder sBuilder = new StringBuilder(info);
            for (int i = maskStart; i <= maskEnd; i++) {
                sBuilder.setCharAt(i, '*');
            }
            return sBuilder.toString();
        }
    }

}


