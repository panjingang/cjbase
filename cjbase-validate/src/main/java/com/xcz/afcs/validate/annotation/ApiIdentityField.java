package com.xcz.afcs.validate.annotation;

import com.xcz.afcs.validate.validator.ApiIdentityFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = ApiIdentityFieldValidator.class)
public @interface ApiIdentityField {

    String message() default "{xcz.afbp.validation.identity}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String name() default "";

    String desc() default "";

    boolean required() default true;

}
