package com.xcz.afcs.validate.enums;

/**
 * Created by jingang on 2017/10/13.
 */
public enum ValidateType {
    BLANK(1, "非空"),
    MOBILE(2, "手机号码"),
    IDNO(3, "身份证号码"),
    URL(4, "URL地址"),
    EMAIL(5, "邮箱"),
    TELEPHONE(6, "电话号码"),
    QQ(7, "QQ"),
    PLATE_NO(8, "车牌"),
    ZIPCODE(9, "邮编"),
    IP4(10, "IPV4"),
    ;

    private Integer type;
    private String desc;

    ValidateType(Integer type, String desc){
        this.type = type;
        this.desc = desc;
    }


}
