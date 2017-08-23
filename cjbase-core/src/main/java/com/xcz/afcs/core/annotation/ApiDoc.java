package com.xcz.afcs.core.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiDoc {
    //接口描述
    String desc() default "";

    //开始支持版本
    String since() default "1.0";

    //支持范围
    String scope() default "";

    //更新历史
    ApiUpdateDoc[] history() default {};
}