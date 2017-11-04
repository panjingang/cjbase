package com.xcz.afcs.util;

import com.xcz.afcs.util.enums.DatePrecision;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DateTimeUtil.class);

    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    
    public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";
    
    public static final String FORMAT_HHMMSS = "HH:mm:ss";
    
    public static final String FORMAT_YYYY = "yyyy";
    
    public static final String FORMAT_YYYYMMDDHHMMSS_NO_BREAK = "yyyyMMddHHmmss";
    
    public static final String FORMAT_YYYYMMDD_NO_BREAK = "yyyyMMdd";
    
    public static final String FORMAT_HHMMSS_NO_BREAK = "HHmmss";
    
    public static final String FORMAT_DEFAULT = FORMAT_YYYYMMDDHHMMSS;

    private static final String ERR_MSG_INVALID_FORMAT = "Invalid format.";

    private static final String ERR_MSG_PARSE_FAILED = "Parse date failed.";


    public static Date now() {
        return getCurrentDate();
    }

    public static Date getCurrentDate(){
        return Calendar.getInstance().getTime();
    }

    public static String getCurrentDateStr(){
        return getCurrentDateStr(FORMAT_DEFAULT);
    }

    public static String getCurrentDateStr(String format){
        return getDateStr(getCurrentDate(), format);
    }

    public static String getDateStr(Date date){
        return getDateStr(date, FORMAT_DEFAULT);
    }


    public static String getDateStr(Date date, String format){
        if (date == null)
            return null;
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (IllegalArgumentException e) {
            LOG.error(ERR_MSG_INVALID_FORMAT, e);
            return null;
        }
    }

    public static String getDateStr(long time, String format){
        try {
            return new SimpleDateFormat(format).format(new Date(time));
        } catch (IllegalArgumentException e) {
            LOG.error(ERR_MSG_INVALID_FORMAT, e);
            return null;
        }
    }

    public static Date toDate(String dateString) throws ParseException {
        return toDate(dateString, FORMAT_DEFAULT);
    }

    public static Date toDate(String dateString, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(dateString);
    }

    public static Date toDateOrNull(String dateString) {
        return toDateOrNull(dateString, FORMAT_DEFAULT);
    }
    
    public static Date toDateOrNull(String dateString, String format) {
        try {
            if (StringUtils.isBlank(dateString)) {
                return null;
            }
            return new SimpleDateFormat(format).parse(dateString);
        } catch (IllegalArgumentException e) {
            LOG.error(ERR_MSG_INVALID_FORMAT, e);
            return null;
        } catch (ParseException e) {
            LOG.error(ERR_MSG_PARSE_FAILED, e);
            return null;
        }
    }

    public static Date toBeginDateOrNull(String dateString) {
        Date date = toDateOrNull(dateString, FORMAT_YYYYMMDD);
        return (date == null) ? null : beginOfDay(date);
    }

    public static Date toEndDateOrNull(String dateString) {
        Date date = toDateOrNull(dateString, FORMAT_YYYYMMDD);
        return (date == null) ? null : endOfDay(date);
    }


    public static Date beginOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date endOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static boolean isBeginOfMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH) == 1;
    }

    public static Date beginOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return beginOfDay(cal.getTime());
    }

    public static Date endOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return endOfDay(cal.getTime());
    }

    public static boolean isEndOfMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH) == cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public static Date halfOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 16);
        return beginOfDay(cal.getTime());
    }

    public static boolean isHalfOfMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH) == 16;
    }


    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isBeginOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    }

    public static Date beginOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return beginOfDay(cal.getTime());
    }

    public static boolean isEndOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public static Date endOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginOfWeek(date));
        cal.add(Calendar.DAY_OF_MONTH, 6);
        return endOfDay(cal.getTime());
    }

    public static Date yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static Date tomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date lastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    public static Date nextMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    //时间格式转换
    public static String transDateFormat(String dateString, String fromFormat, String toFormat) {
        if (!StringUtils.isBlank(dateString)) {
            Date date = toDateOrNull(dateString, fromFormat);
            if (null != date) {
                return getDateStr(date, toFormat);
            }
        }
        return dateString;
    }

    public static Date addDatePrecision(DatePrecision precision, int amount){
        return addDatePrecision(getCurrentDate(), precision, amount);
    }

    public static Date addDatePrecision(Date date, DatePrecision precision, int amount){
       Calendar cal =  Calendar.getInstance();
       cal.setTime(date);
       switch (precision) {
           case YEAR : cal.add(Calendar.YEAR, amount); break;
           case MONTH :cal.add(Calendar.MONTH, amount); break;
           case DAY :cal.add(Calendar.DATE, amount); break;
           case HOUR :cal.add(Calendar.HOUR, amount); break;
           case MINUTE :cal.add(Calendar.MINUTE, amount); break;
           case SECOND :cal.add(Calendar.SECOND, amount); break;
           case MILLISECOND :cal.add(Calendar.MILLISECOND, amount); break;
           default:
               throw new IllegalArgumentException("DatePrecision Invalid");
       }
        return cal.getTime();
    }

    public static Date subDatePrecision(DatePrecision precision, int amount){
        return subDatePrecision(getCurrentDate(), precision, amount);
    }

    public static Date subDatePrecision(Date date, DatePrecision precision, int amount){
        return addDatePrecision(date, precision, -amount);
    }

    /**
     * 判断两个时间的大小
     * @param d1  源时间
     * @param d2  目标时间
     * @return d1>＝d2返回true
     */
    public static boolean compare(Date d1, Date d2) {
        if (d1 == null || d1 == null) {
            throw new IllegalArgumentException("请传入正确的时间参数，不能为空");
        }
        return d1.compareTo(d2) >= 0;
    }

  
}
