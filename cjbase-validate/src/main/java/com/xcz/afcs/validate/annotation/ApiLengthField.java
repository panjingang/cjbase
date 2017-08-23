package com.xcz.afcs.validate.annotation;

import com.xcz.afcs.validate.validator.ApiLengthFieldValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented()
@Constraint(validatedBy = ApiLengthFieldValueValidator.class)
public @interface ApiLengthField {

    String message() default "{xcz.afbp.validation.length}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String name() default "";

    String desc() default "";

    int len() default 0;

    boolean required() default true;

}
