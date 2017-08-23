package com.xcz.afcs.biz.model;

public class DefaultSessionKey implements SessionKey {

    private String prefixKey;

    private String key;

    public DefaultSessionKey(String prefixKey, String key) {
        this.prefixKey = prefixKey;
        this.key = key;
    }

    public String getKey(){
        return prefixKey+key;
    }

}
