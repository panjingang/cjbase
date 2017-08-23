package com.xcz.afcs.biz.message;

import java.util.Map;

/**
 * Created by jingang on 2016/8/4.
 */
public interface AfcsMessageSource {

    String getMessage(String code);

    String getMessage(String code, Map<String, Object> placeHolders);

    String getMessage(String code, String placeHolderKey, Object placeHolderValue);
}
