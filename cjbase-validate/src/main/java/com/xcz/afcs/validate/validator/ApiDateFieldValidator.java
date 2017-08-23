package com.xcz.afcs.validate.validator;


import com.xcz.afcs.util.DateTimeUtil;
import com.xcz.afcs.validate.annotation.ApiDateField;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class ApiDateFieldValidator implements ConstraintValidator<ApiDateField, CharSequence> {

    private String pattern;
    private ApiDateField apiDateField;

    @Override
    public void initialize(ApiDateField apiDateField) {
        this.apiDateField = apiDateField;
        this.pattern      = apiDateField.pattern();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        boolean required = apiDateField.required();
        if (StringUtils.isNotBlank(value)) {
            Date date = DateTimeUtil.toDateOrNull(String.valueOf(value), pattern);
            //可解析但可能解析错，重新format比较
            return null != date && value.equals(DateTimeUtil.getDateStr(date, pattern));
        }
        return required ? false:true;
    }

}
