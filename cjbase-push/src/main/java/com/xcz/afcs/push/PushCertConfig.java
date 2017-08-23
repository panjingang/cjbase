package com.xcz.afcs.push;

/**
 * Created by jingang on 2017/6/16.
 */
public class PushCertConfig {

    private String appKey;

    private String appSecret;

    private Integer isProduct = 0;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Integer getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(Integer isProduct) {
        this.isProduct = isProduct;
    }
}
