package com.xcz.afcs.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jingang on 2017/6/22.
 */
public class StrUtil {

    public static final char C_SPACE = ' ';
    public static final char C_TAB = '	';
    public static final char C_DOT = '.';
    public static final char C_SLASH = '/';
    public static final char C_BACKSLASH = '\\';
    public static final char C_CR = '\r';
    public static final char C_LF = '\n';
    public static final char C_UNDERLINE = '_';
    public static final char C_COMMA = ',';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';
    public static final char C_BRACKET_START = '[';
    public static final char C_BRACKET_END = ']';
    public static final char C_COLON = ':';

    public static final String SPACE = " ";
    public static final String TAB = "	";
    public static final String DOT = ".";
    public static final String DOUBLE_DOT = "..";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String EMPTY = "";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String UNDERLINE = "_";
    public static final String COMMA = ",";
    public static final String DELIM_START = "{";
    public static final String DELIM_END = "}";
    public static final String BRACKET_START = "[";
    public static final String BRACKET_END = "]";
    public static final String COLON = ":";

    public static final String genSetter(String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            throw new RuntimeException("字段不能为空");
        }
        return "set"+StringUtils.capitalize(fieldName);
    }

    public static final String genGetter(String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            throw new RuntimeException("字段不能为空");
        }
        return "get"+StringUtils.capitalize(fieldName);
    }

    public static String cleanBlank(String str) {
        if (str == null) {
            return EMPTY;
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(str.length());
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (false == isBlankChar(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c);
    }

    public static List<String> split(CharSequence str, char separator) {
        return split(str, separator, 0);
    }

    public static String[] splitToArray(CharSequence str, char separator) {
        List<String> result = split(str, separator);
        return result.toArray(new String[result.size()]);
    }

    /**
     * 切分字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数
     * @return 切分后的集合
     */
    public static List<String> split(CharSequence str, char separator, int limit) {
        if (str == null) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>(limit > 0 ? 16 : limit);
        if (limit == 1) {
            list.add(str.toString());
            return list;
        }
        boolean isNotEnd = true; // 未结束切分的标志
        int strLen = str.length();
        StringBuilder sb = new StringBuilder(strLen);
        char c;
        for (int i = 0; i < strLen; i++) {
            c = str.charAt(i);
            if (isNotEnd && c == separator) {
                list.add(sb.toString());
                // 清空StringBuilder
                sb.delete(0, sb.length());
                // 当达到切分上限-1的量时，将所剩字符全部作为最后一个串
                if (limit > 0 && list.size() == limit - 1) {
                    isNotEnd = false;
                }
            } else {
                sb.append(c);
            }
        }
        list.add(sb.toString());// 加入尾串
        return list;
    }

    public static List<String> split(String str, String regex) {
        if (str == null) {
            return new ArrayList<>();
        }
        String[] array = str.split(regex);
        return Arrays.asList(array);
    }

    public String join(String[] array, String separator) {
        if (array == null) {
            return EMPTY;
        }
        return join(Arrays.asList(array), separator);
    }

    public String join(List<String> list, String separator) {
        final StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String item : list) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(separator);
            }
            sb.append(item);
        }
        return sb.toString();
    }

    /**
     * 将驼峰式命名的字符串转换为下划线方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     * <p/>
     * 例如：HelloWorld=》hello_world
     *
     * @param camelCaseStr 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String toUnderlineCase(CharSequence camelCaseStr) {
        if (camelCaseStr == null) {
            return null;
        }
        final int length = camelCaseStr.length();
        StringBuilder sb = new StringBuilder();
        char c;
        boolean isPreUpperCase = false;
        for (int i = 0; i < length; i++) {
            c = camelCaseStr.charAt(i);
            boolean isNextUpperCase = true;
            if (i < (length - 1)) {
                isNextUpperCase = Character.isUpperCase(camelCaseStr.charAt(i + 1));
            }
            if (Character.isUpperCase(c)) {
                if (!isPreUpperCase || !isNextUpperCase) {
                    if (i > 0) sb.append(UNDERLINE);
                }
                isPreUpperCase = true;
            } else {
                isPreUpperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。<br>
     * <p/>
     * 例如：hello_world=》HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String toCamelCase(CharSequence name) {
        if (null == name) {
            return null;
        }
        String camelName = name.toString();
        if (camelName.contains(UNDERLINE)) {
            camelName = camelName.toLowerCase();
            StringBuilder sb = new StringBuilder(camelName.length());
            boolean upperCase = false;
            for (int i = 0; i < camelName.length(); i++) {
                char c = camelName.charAt(i);
                if (c == C_UNDERLINE) {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return camelName;
        }
    }

}
