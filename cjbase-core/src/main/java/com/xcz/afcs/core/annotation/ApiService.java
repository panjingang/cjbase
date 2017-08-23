package com.xcz.afcs.core.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiService {

    String name() default "";

    String desc() default "";

    boolean record() default false;
}
