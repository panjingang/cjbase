package com.xcz.afcs.validate.validator;

import com.xcz.afcs.validate.annotation.ApiMobileField;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ApiMobileFieldValueValidator implements ConstraintValidator<ApiMobileField, CharSequence> {

    private ApiMobileField apiMobileField;

    @Override
    public void initialize(ApiMobileField apiMobileField) {
        this.apiMobileField = apiMobileField;
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        boolean required = apiMobileField.required();
        if (StringUtils.isNotBlank(value)) {
            return Pattern.matches("^(1[34578]\\d{9})?$", value);
        }
        return required ? false:true;
    }

}
