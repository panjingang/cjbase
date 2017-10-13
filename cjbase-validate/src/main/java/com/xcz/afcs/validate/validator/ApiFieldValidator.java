package com.xcz.afcs.validate.validator;

import com.xcz.afcs.util.PatternUtil;
import com.xcz.afcs.util.StrUtil;
import com.xcz.afcs.validate.annotation.ApiField;
import com.xcz.afcs.validate.constants.ValidationConstants;
import com.xcz.afcs.validate.enums.ValidateType;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class ApiFieldValidator implements ConstraintValidator<ApiField, Object> {

    private ApiField apiField;

    @Override
    public void initialize(ApiField apiField) {
        this.apiField = apiField;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
             if (apiField.required()) {
                 return false;
             }else {
                 return true;
             }
        }
        ValidateType fieldType = apiField.type();
        if (value instanceof String) {
            String data = (String)value;
            if(apiField.required() && StringUtils.isBlank(data)) {
                return false;
            }
            switch (fieldType) {
                case MOBILE:    return validateMobile(context, data);
                case IDNO:      return validateIdNo(context, data);
                case EMAIL:     return validateEmail(context, data);
                case QQ:        return validateQQ(context, data);
                case ZIPCODE:   return validateZipcode(context, data);
                case URL:       return validateUrl(context, data);
                case TELEPHONE: return validateTelephone(context, data);
                case PLATE_NO:  return validatePlateNo(context, data);
                case IP4:       return validateIPV4(context, data);
                default:        return validateBlank(context, data);
            }
        }
        return true;
    }

    private boolean validateBlank(ConstraintValidatorContext context, String data) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(ValidationConstants.NOT_BLANK_MESSAGE).addConstraintViolation();
        return StringUtils.isNotBlank(data);
    }

    private boolean validateMobile(ConstraintValidatorContext context, String data) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(ValidationConstants.FORMAT_ERROR_MESSAGE).addConstraintViolation();
        return PatternUtil.isMobile(data);
    }

    private boolean validateIdNo(ConstraintValidatorContext context, String data) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(ValidationConstants.FORMAT_ERROR_MESSAGE).addConstraintViolation();
        return PatternUtil.isIDcard(data);
    }

    private boolean validateEmail(ConstraintValidatorContext context, String data) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(ValidationConstants.FORMAT_ERROR_MESSAGE).addConstraintViolation();
        return PatternUtil.isEmail(data);
    }

    private boolean validateQQ(ConstraintValidatorContext context, String data) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(ValidationConstants.FORMAT_ERROR_MESSAGE).addConstraintViolation();
        return PatternUtil.isQQnumber(data);
    }

    private boolean validateZipcode(ConstraintValidatorContext context, String data) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(ValidationConstants.FORMAT_ERROR_MESSAGE).addConstraintViolation();
        return PatternUtil.isZipcode(data);
    }

    private boolean validateUrl(ConstraintValidatorContext context, String data) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(ValidationConstants.FORMAT_ERROR_MESSAGE).addConstraintViolation();
        List<String> urls = StrUtil.split(data, StrUtil.COMMA);
        for(String url : urls) {
            if (!PatternUtil.isUrl(url)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateTelephone(ConstraintValidatorContext context, String data) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(ValidationConstants.FORMAT_ERROR_MESSAGE).addConstraintViolation();
        return PatternUtil.isTel(data);
    }

    private boolean validatePlateNo(ConstraintValidatorContext context, String data) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(ValidationConstants.FORMAT_ERROR_MESSAGE).addConstraintViolation();
        return PatternUtil.isPlateNo(data.toUpperCase());
    }

    private boolean validateIPV4(ConstraintValidatorContext context, String data) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(ValidationConstants.FORMAT_ERROR_MESSAGE).addConstraintViolation();
        return PatternUtil.isIP4(data);
    }

}
