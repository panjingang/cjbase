package com.xcz.afcs.mybatis.entity;

import com.xcz.afcs.mybatis.annonation.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jingang on 2017/11/1.
 */
@Getter
@Setter
public abstract class TenantEntity extends UpdatableEntity{

    @Column(comment = "租户编号")
    private Long tenantId;
}
