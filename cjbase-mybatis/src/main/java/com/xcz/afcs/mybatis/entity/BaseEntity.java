package com.xcz.afcs.mybatis.entity;

import com.xcz.afcs.mybatis.annonation.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by mac on 2017/8/12.
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    @Column(primary = true, comment="主键编号")
    private Long id;
}
