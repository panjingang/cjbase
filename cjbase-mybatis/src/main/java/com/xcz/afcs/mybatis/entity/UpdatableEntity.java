package com.xcz.afcs.mybatis.entity;

import com.xcz.afcs.mybatis.annonation.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by mac on 2017/9/8.
 */
@Getter
@Setter
public abstract class UpdatableEntity implements Serializable {

    public abstract Long getPrimaryId();

    @Column(comment = "版本号")
    private Long version;

}
