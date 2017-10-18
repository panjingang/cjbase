package com.xcz.afcs.validate;

import com.xcz.afcs.core.enums.BaseErrorCode;
import com.xcz.afcs.core.exception.BaseBusinessException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class ValidateUtil {
    
    private static Validator validator;
    
    static {
        init();
    }
    
    private static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    public static <T> void validOrThrowException(T bean) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if (!violations.isEmpty()) {
            throw new BaseBusinessException(BaseErrorCode.PARAM_ERROR.getCode(), buildMessageFromViolations(violations));
        }
    }
    
    public static <T> Set<ConstraintViolation<T>> validOrReturnViolations(T bean) {
        return validator.validate(bean);
    }
 
    private static <T> String buildMessageFromViolations(Set<ConstraintViolation<T>> violations) {
        List<ConstraintViolation<T>> list = new ArrayList<>(violations);
        Collections.sort(list, new Comparator<ConstraintViolation<T>>() {
            @Override
            public int compare(ConstraintViolation<T> o1, ConstraintViolation<T> o2) {
                return o1.getMessage().compareTo(o2.getMessage());
            }
        });

        StringBuilder sBuilder = new StringBuilder(violations.size() * 32);
        for (ConstraintViolation<T> violation : violations) {
            sBuilder.append(violation.getMessage());
            break;
        }
        return sBuilder.toString();
    }
    
}
