package com.xcz.afcs.validate.annotation;

import com.xcz.afcs.validate.validator.ApiFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.io.Serializable;
import java.lang.annotation.*;

@Documented
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = ApiFieldValidator.class)
public @interface ApiField {

    String message() default "{xcz.afbp.validation.blank}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String name() default "";

    String desc() default "";

    Class<? extends Serializable> subCls() default String.class;

    boolean required() default true;

}
