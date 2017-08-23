package com.xcz.afcs.core.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiUpdateDoc {
    //更新版本
    String version();
    //更新描述
    String desc();
}
