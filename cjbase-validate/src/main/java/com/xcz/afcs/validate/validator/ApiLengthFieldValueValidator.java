package com.xcz.afcs.validate.validator;

import com.xcz.afcs.validate.annotation.ApiLengthField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ApiLengthFieldValueValidator implements ConstraintValidator<ApiLengthField, CharSequence> {

    private ApiLengthField apiField;

    @Override
    public void initialize(ApiLengthField apiField) {
        this.apiField = apiField;
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            boolean required = apiField.required();
            return required ? false : true;
        }
        int length = value.length();
        return length == apiField.len();
    }
}
