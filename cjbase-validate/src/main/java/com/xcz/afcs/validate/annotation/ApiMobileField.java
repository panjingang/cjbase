package com.xcz.afcs.validate.annotation;

import com.xcz.afcs.validate.validator.ApiMobileFieldValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = ApiMobileFieldValueValidator.class)
public @interface ApiMobileField{

    String message() default "{xcz.afbp.validation.mobile}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String name() default "";

    String desc() default "";

    boolean required() default true;

}
