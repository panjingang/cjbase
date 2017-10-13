package com.xcz.afcs.mybatis.model;

import lombok.Getter;

import java.io.Serializable;

/**
 * Created by jingang on 2017/10/13.
 */
@Getter
public class Join implements Serializable{

    public enum JoinType {
        LEFT_JOIN, RIGHT_JOIN, JOIN
    }

    private JoinType joinType;

    private String join;

    private Join (String join, JoinType joinType) {
        this.join = join;
        this.joinType = joinType;
    }

    public static Join leftJoin(String join){
        return new Join(join, JoinType.LEFT_JOIN);
    }

    public static Join rightJoin(String join){
        return new Join(join, JoinType.RIGHT_JOIN);
    }

    public static Join join(String join){
        return new Join(join, JoinType.JOIN);
    }

}
