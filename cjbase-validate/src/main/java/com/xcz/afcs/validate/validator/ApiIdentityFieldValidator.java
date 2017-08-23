package com.xcz.afcs.validate.validator;

import com.xcz.afcs.core.util.NCID;
import com.xcz.afcs.core.util.NCIISUtil;
import com.xcz.afcs.validate.annotation.ApiIdentityField;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ApiIdentityFieldValidator implements ConstraintValidator<ApiIdentityField, CharSequence> {

    private ApiIdentityField apiIdentityField;

    @Override
    public void initialize(ApiIdentityField apiIdentityField) {
        this.apiIdentityField = apiIdentityField;
    }
    
    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        boolean required = apiIdentityField.required();
        if (StringUtils.isNotBlank(value)) {
            NCID ncid = NCIISUtil.parseNCID(String.valueOf(value), false);
            return null != ncid && ncid.isValid();
        }
        return required ? false : true;
    }
    
}
