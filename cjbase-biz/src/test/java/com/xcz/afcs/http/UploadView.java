package com.xcz.afcs.http;

import java.io.Serializable;

/**
 * Created by jingang on 2017/8/24.
 */
public class UploadView implements Serializable{
    private String corpId;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }
}
