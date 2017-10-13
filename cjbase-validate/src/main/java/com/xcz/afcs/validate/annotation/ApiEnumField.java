package com.xcz.afcs.validate.annotation;

import com.xcz.afcs.validate.validator.ApiEnumFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = ApiEnumFieldValidator.class)
public @interface ApiEnumField{

    String message() default "{xcz.afcs.validation.enum}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String name() default "";

    String desc() default "";

    Class<?> enumCls();

    /**
     * 字符串分割符，应为正则，用于{@link String#split(String)}
     * @return
     */
    String stringDelimiter() default ",";

    boolean required() default true;

}
