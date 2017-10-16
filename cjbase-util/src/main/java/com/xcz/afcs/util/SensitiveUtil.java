package com.xcz.afcs.util;

import org.apache.commons.lang3.StringUtils;

/**
 *  字符串脱敏处理
 */
public class SensitiveUtil {

     public static String chineseName(String fullName) {
         if (StringUtils.isBlank(fullName)) {
             return StrUtil.EMPTY;
         }
         if (fullName.length() == 2) {
             return sensitive(fullName, 1, 1, 0);
         }
         return sensitive(fullName, 1, 2, 0);
     }

    public static String password(String password) {
         return "******";
     }

     public static String idCardNo(String idCardNo) {
         return sensitive(idCardNo, 0, 4,  4);
     }

     public static final String mobilePhone(String mobilePhone) {
         return sensitive(mobilePhone, 3, 4, 3);
     }

    public static String bankCard(String cardNum) {
        return sensitive(cardNum, 6, 4,  4);
    }

    public static String sensitive(String str, int leftLength, int sensitiveLength, int rightLength) {
         int minLength = leftLength + sensitiveLength + rightLength;
         if (StringUtils.isBlank(str)) {
             return StrUtil.EMPTY;
         }
         if (str.length() < minLength) {
             return str;
         }
         StringBuffer sb = new StringBuffer();
         sb.append(StringUtils.left(str, leftLength));
         for(int i=0; i<sensitiveLength; i++) {
             sb.append("*");
         }
         sb.append(StringUtils.right(str, rightLength));
         return sb.toString();
    }

}
