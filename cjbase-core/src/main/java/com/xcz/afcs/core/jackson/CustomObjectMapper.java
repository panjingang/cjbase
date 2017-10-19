package com.xcz.afcs.core.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by jingang on 2016/7/18.
 */
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        super();
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        this.setSerializerFactory(this.getSerializerFactory().withSerializerModifier(new NullSerializerModifier()));
    }
}
