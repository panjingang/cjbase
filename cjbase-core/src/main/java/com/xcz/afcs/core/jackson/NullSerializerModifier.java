package com.xcz.afcs.core.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.xcz.afcs.core.jackson.serializer.NullJsonArraySerializer;
import com.xcz.afcs.core.jackson.serializer.NullJsonNumberSerializer;
import com.xcz.afcs.core.jackson.serializer.NullJsonObjectSerializer;
import com.xcz.afcs.core.jackson.serializer.NullJsonStringSerializer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Created by jingang on 2017/6/23.
 */
public class NullSerializerModifier extends BeanSerializerModifier {

    private JsonSerializer<Object> nullJsonObjectSerializer = new NullJsonObjectSerializer();

    private JsonSerializer<Object> nullJsonArraySerializer = new NullJsonArraySerializer();

    private JsonSerializer<Object> nullJsonNumberSerializer = new NullJsonNumberSerializer();

    private JsonSerializer<Object> nullJsonStringSerializer = new NullJsonStringSerializer();

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        // 循环所有的beanPropertyWriter
        for (int i = 0; i < beanProperties.size(); i++) {
            BeanPropertyWriter writer = beanProperties.get(i);
            Class<?> clazz = writer.getPropertyType();
            if (isStringType(clazz)) {
                writer.assignNullSerializer(nullJsonStringSerializer);
            }
            else if (isNumberType(clazz)) {
                writer.assignNullSerializer(nullJsonNumberSerializer);
            }
            else if (isArrayType(clazz)) {
                //给writer注册一个自己的nullSerializer
                writer.assignNullSerializer(nullJsonArraySerializer);
            }
            else if (isObjectType(clazz)) {
                //给writer注册一个自己的nullSerializer
                writer.assignNullSerializer(nullJsonObjectSerializer);
            }
        }
        return beanProperties;
    }

    // 判断是什么类型
    protected boolean isObjectType(Class<?> clazz) {
        return clazz.equals(Object.class);
    }

    // 判断是什么类型
    protected boolean isArrayType(Class<?> clazz) {
        return clazz.isArray() || clazz.equals(List.class) || clazz.equals(Set.class);
    }

    protected boolean isStringType(Class<?> clazz) {
        return clazz.equals(String.class);
    }

    protected boolean isNumberType(Class<?> clazz) {
        return clazz.equals(Integer.class) || clazz.equals(Long.class) || clazz.equals(BigDecimal.class) || clazz.equals(Double.class) || clazz.equals(Float.class) || clazz.equals(Short.class);
    }

}
