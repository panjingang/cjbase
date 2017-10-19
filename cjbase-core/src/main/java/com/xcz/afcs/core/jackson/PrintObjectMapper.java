package com.xcz.afcs.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.xcz.afcs.util.FormatUtil;

import java.io.IOException;

/**
 * Created by jingang on 2017/10/19.
 */
public class PrintObjectMapper extends CustomObjectMapper {

    public PrintObjectMapper() {
        super();
        SimpleModule module = new SimpleModule();
        module.addSerializer(String.class, new JsonSerializer<String>() {
            @Override
            public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                if (value == null) {
                    jgen.writeNumber("");
                }else{
                    if (value.length() > 512) {
                        jgen.writeString(FormatUtil.formatBigData(value));
                    }else{
                        jgen.writeString(value);
                    }
                }
            }
        });
        this.registerModule(module);
    }

}
