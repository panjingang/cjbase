package com.xcz.afcs.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jingang on 2017/10/16.
 */
public class SensitiveUtilTest {

    @Test
    public void chineseName() {
        assertEquals("黎*", SensitiveUtil.chineseName("黎明"));
        assertEquals("小**", SensitiveUtil.chineseName("小财主"));
    }

    @Test
    public void mobilePhone() {
        assertEquals("182****028", SensitiveUtil.mobilePhone("18258265028"));
    }

    @Test
    public void idCardNo() {
        assertEquals("****5226", SensitiveUtil.idCardNo("330302197602215226"));
    }

    @Test
    public void bankCard() {
        assertEquals("622848****0018", SensitiveUtil.bankCard("6228480402564890018"));
    }

}
