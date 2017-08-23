package com.xcz.afcs.validate.validator;

import com.xcz.afcs.validate.annotation.ApiField;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ApiFieldValidator implements ConstraintValidator<ApiField, Object> {

    private ApiField apiField;

    @Override
    public void initialize(ApiField apiField) {
        this.apiField = apiField;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (apiField.required()) {
            if (value == null) {
                return false;
            }
            if (value instanceof String) {
                return StringUtils.isBlank((String)value) ? false : true;
            }
        }
        return true;
    }
}
