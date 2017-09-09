package com.xcz.afcs.mybatis.entity;

import com.xcz.afcs.mybatis.annonation.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by mac on 2017/9/8.
 */
@Getter
@Setter
public class UpdatableEntity extends BaseEntity {

    @Column(comment = "版本号")
    private Long updateVersion = 0L;

}
