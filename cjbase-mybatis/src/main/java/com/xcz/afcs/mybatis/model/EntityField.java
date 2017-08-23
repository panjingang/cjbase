package com.xcz.afcs.mybatis.model;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by mac on 2017/8/12.
 */
@Getter
@Setter
public class EntityField {

    private String fieldName;

    private Field field;

    private Method getMethod;

    private Method setMethod;

    private String cloumnName;

}
