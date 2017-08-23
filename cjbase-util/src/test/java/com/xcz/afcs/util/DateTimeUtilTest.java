package com.xcz.afcs.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jingang on 2017/8/4.
 */
public class DateTimeUtilTest {

    @Test
    public void endOfMonth() {
        //Date now = DateTimeUtil.getCurrentDate();
        //System.out.println(DateTimeUtil.endOfMonth(now));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 6);
        Date now = cal.getTime();
        System.out.println(now);

        System.out.println(DateTimeUtil.isBeginOfWeek());
        System.out.println(DateTimeUtil.isEndOfWeek());
    }
}
