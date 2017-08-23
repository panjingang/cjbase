package com.xcz.afcs.validate.validator;

import com.xcz.afcs.core.base.StatusEnumMessage;
import com.xcz.afcs.core.base.TypeEnumMessage;
import com.xcz.afcs.util.ValueUtil;
import com.xcz.afcs.validate.annotation.ApiEnumField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApiEnumFieldValidator implements ConstraintValidator<ApiEnumField, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(ApiEnumFieldValidator.class);

    private ApiEnumField apiEnumField;

    private String stringDelimiter;

    private static final Map<String, List<String>> enumMap = new ConcurrentHashMap<String, List<String>>();

    private List<String> enums;

    @Override
    public void initialize(ApiEnumField apiEnumField) {
        this.apiEnumField = apiEnumField;
        stringDelimiter = apiEnumField.stringDelimiter();
        enums = getEnumValues(apiEnumField.enumCls());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        boolean required = apiEnumField.required();
        String _value;
        if (value == null) {
            value = "";
        }
        if (value instanceof Integer || value instanceof String ) {
            _value = ValueUtil.getString(value);
        }
        else {
            return false;
        }
        if (StringUtils.isNotBlank(_value)) {
            String[] values = String.valueOf(value).split(stringDelimiter);
            for (String valueUnit : values) {
                if (!enums.contains(valueUnit)) {
                    return false;
                }
            }
            return true;
        }
        return required ? false:true;
    }

    private List<String> getEnumValues(Class cls) {
        List<String> enums = enumMap.get(cls.getName());
        if (enums != null) {
            return enums;
        }
        enums = new ArrayList<String>();
        Class[] interfaces = cls.getInterfaces();
        if (interfaces == null) {
            return enums;
        }
        try {
            Method method = cls.getMethod("values");
            boolean type = false;
            for (Class inter : interfaces) {
                if ("TypeEnumMessage".equals(inter.getSimpleName())) {
                    type = true;
                }
            }
            if (type) {
                TypeEnumMessage inter[] = (TypeEnumMessage[]) method.invoke(null, null);
                for (TypeEnumMessage enumMessage : inter) {
                    enums.add(enumMessage.getValue());
                }
            } else {
                StatusEnumMessage inter[] = (StatusEnumMessage[]) method.invoke(null, null);
                for (StatusEnumMessage enumMessage : inter) {
                    enums.add(String.valueOf(enumMessage.getValue()));
                }
            }
            enumMap.put(cls.getName(), enums);
        }catch(Exception e) {
            LOG.error("处理枚举出错", e);
        }
        return enums;
    }
}
