package com.xcz.afcs.mybatis.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Pagination implements Serializable {

    private Integer offset;

    private Integer pageSize;

}
