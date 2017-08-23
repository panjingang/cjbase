package com.xcz.afcs.mybatis.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Expression {

    public enum EXP {
        EQ, LIKE, GT, LT, GTE, LTE, IN
    }

    private EXP exp;

    private String cloumnName;

    private String propertyName;

    private String paramName;

    private Object value;

    private Expression (String propertyName, EXP exp, Object value) {
        this.propertyName = propertyName;
        this.exp          = exp;
        this.value        = value;
    }


    public static Expression eq(String propertyName, Object value) {
        return new Expression(propertyName, EXP.EQ, value);
    }

    public static Expression like(String propertyName, Object value) {
        return new Expression(propertyName, EXP.LIKE, value);
    }

    public static Expression gt(String propertyName, Object value) {
        return new Expression(propertyName, EXP.GT, value);
    }

    public static Expression lt(String propertyName, Object value) {
        return new Expression(propertyName, EXP.LT, value);
    }

    public static Expression gte(String propertyName, Object value) {
        return new Expression(propertyName, EXP.GTE, value);
    }

    public static Expression lte(String propertyName, Object value) {
        return new Expression(propertyName, EXP.LTE, value);
    }

    public static  <T> Expression in(String propertyName, List<T> value) {
        return new Expression(propertyName, EXP.IN, value);
    }

}
