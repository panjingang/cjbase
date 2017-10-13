package com.xcz.afcs.mybatis.entity;

import com.xcz.afcs.mybatis.annonation.Column;
import com.xcz.afcs.mybatis.annonation.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by jingang on 2017/10/13.
 */
@Getter
@Setter
@Table(name = "tb_user")
public class UserEntity extends UpdatableEntity{

    @Column(primary = true)
    private Long userId;

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private Date createTime;

    @Column
    private Date updateTime;

    @Override
    public Long getPrimaryId() {
        return userId;
    }
}
