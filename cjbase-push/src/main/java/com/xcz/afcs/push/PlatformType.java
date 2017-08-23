package com.xcz.afcs.push;

import com.xcz.afcs.core.base.TypeEnumMessage;

/**
 * Created by jingang on 2017/6/16.
 */
public enum PlatformType implements TypeEnumMessage {
    IOS("1", "iOS"),
    ANDROID("2", "Android"),
    ;

    private String value;
    private String message;

    PlatformType(String value, String message) {
        this.value = value;
        this.message = message;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
