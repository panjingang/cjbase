package com.xcz.afcs.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mac on 2017/11/5.
 */
public class FileUtilsTest {

    @Test
    public void getFileNameNotExtName() {

        String name = FileUtil.getFileNameNotExtName("/temp/11111.jpg");

        Assert.assertEquals(name, "11111");
    }
}
