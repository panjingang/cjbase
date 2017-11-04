package com.xcz.afcs.validate.annotation;

import com.xcz.afcs.util.DateTimeUtil;
import com.xcz.afcs.validate.validator.ApiDateFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = ApiDateFieldValidator.class)
public @interface ApiDateField {

    String message() default "{xcz.afbp.validation.date}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String name() default "";

    String desc() default "";

    boolean required() default true;
    /**
     * 指明格式
     * @return
     */
    String pattern() default DateTimeUtil.FORMAT_YYYYMMDD;
}
