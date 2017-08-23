package com.xcz.afcs.mybatis.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by mac on 2017/8/13.
 */
@Getter
@Setter
public class Order implements Serializable{

    private boolean ascending;
    private String propertyName;
    private String cloumnName;

    protected Order(String propertyName, boolean ascending) {
        this.propertyName = propertyName;
        this.ascending = ascending;
    }

    public static Order asc(String propertyName) {
        return new Order( propertyName, true );
    }

    public static Order desc(String propertyName) {
        return new Order( propertyName, false );
    }

}
