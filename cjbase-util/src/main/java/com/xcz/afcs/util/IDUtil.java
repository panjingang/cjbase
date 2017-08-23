package com.xcz.afcs.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class IDUtil {
    
    /**
     * 全限定名分隔符。
     */
    public static final char FULL_ID_DELIMITER = '_';
    
    private static final String EMPTY = "";
    
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    
    private static String buildIDChain(boolean noTailDelimiter, String... ids) {
        if (null != ids && 0 < ids.length) {
            StringBuilder sBuilder = new StringBuilder();
            for (String id : ids) {
                sBuilder.append(id).append(FULL_ID_DELIMITER);
            }
            if (noTailDelimiter) {
                sBuilder.deleteCharAt(sBuilder.length() - 1);
            }
            return sBuilder.toString();
        } else {
            return EMPTY;
        }
    }
    
    /**
     * 生成全限定名前缀。
     * @param ids 按顺序的一组ID。
     * @return 生成的全限定名前缀。
     */
    public static String genFullIDPrefix(String... ids) {
        return buildIDChain(true, ids);
    }
    
    /**
     * 生成全限定名。
     * @param ids 按顺序的一组ID。
     * @return 生成的全限定名。
     */
    public static String genFullID(String... ids) {
        return buildIDChain(true, ids);
    }
    
    /**
     * 从全限定名中提取前缀。
     * @param fullID 全限定名。
     * @return 提取的前缀或空白。
     */
    public static String getPrefixFromFullID(String fullID) {
        if (null != fullID) {
            int lastIndex = fullID.lastIndexOf(FULL_ID_DELIMITER);
            if (-1 != lastIndex) {
                return fullID.substring(0, lastIndex);
            }
        }
        return EMPTY;
    }
    
    /**
     * 从全限定名中提取基础ID。
     * @param fullID 全限定名。
     * @return 提取的基础ID或空白。
     */
    public static String getBaseIDFromFullID(String fullID) {
        if (null != fullID) {
            int lastIndex = fullID.lastIndexOf(FULL_ID_DELIMITER);
            if (-1 != lastIndex && lastIndex < fullID.length()) {
                return fullID.substring(lastIndex + 1);
            }
        }
        return EMPTY;
    }
    
    /**
     * 从全限定名中提取前缀和基础ID。
     * @param fullID 全限定名。
     * @return 提取的前缀和基础ID，是一个长度为2的String数组，第一个位置是前缀，第二个位置是基础ID。
     */
    public static String[] getPrefixAndBaseIDFromFullID(String fullID) {
        String prefix = null;
        String baseID = null;
        if (null != fullID) {
            int lastIndex = fullID.lastIndexOf(FULL_ID_DELIMITER);
            if (-1 != lastIndex) {
                prefix = fullID.substring(0, lastIndex);
                baseID = null;
                if (lastIndex < fullID.length()) {
                    baseID = fullID.substring(lastIndex + 1);
                }
            }
        }
        if (null == prefix) {
            prefix = EMPTY;
        }
        if (null == baseID) {
            baseID = EMPTY;
        }
        return new String[] { prefix, baseID };
    }
    
    /**
     * 完全拆解全限定名，按分隔符分成String数组。
     * @param fullID 全限定名。
     * @return 拆解后的String数组，其中的元素顺序跟出现在全限定名中的相同。
     */
    public static String[] breakDownFullID(String fullID) {
        List<String> idList = new ArrayList<String>();
        int preIndex = 0;
        int index = -1;
        while (-1 != (index = fullID.indexOf(FULL_ID_DELIMITER, preIndex))) {
            idList.add(fullID.substring(preIndex, index));
            preIndex = index + 1;
        }
        if (preIndex < fullID.length()) {
            idList.add(fullID.substring(preIndex));
        }
        return idList.toArray(EMPTY_STRING_ARRAY);
    }
    
    public static String genUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
}
