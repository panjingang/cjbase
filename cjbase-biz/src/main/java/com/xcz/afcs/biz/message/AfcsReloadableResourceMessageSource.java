package com.xcz.afcs.biz.message;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by jingang on 2016/8/4.
 */
public class AfcsReloadableResourceMessageSource extends ReloadableResourceBundleMessageSource implements AfcsMessageSource {

    @Override
    public String getMessage(String code) {
        return super.getMessage(code, null, Locale.SIMPLIFIED_CHINESE);
    }

    @Override
    public String getMessage(String code, Map<String, Object> placeHolders) {
        String messageTemplate = super.getMessage(code, null, Locale.SIMPLIFIED_CHINESE);
        return StrSubstitutor.replace(messageTemplate, placeHolders, "${", "}");
    }

    @Override
    public String getMessage(String code, String placeHolderKey, Object placeHolderValue) {
        Map<String, Object> placeHolders = new HashMap<>();
        placeHolders.put(placeHolderKey, placeHolderValue);
        return getMessage(code, placeHolders);
    }

}
