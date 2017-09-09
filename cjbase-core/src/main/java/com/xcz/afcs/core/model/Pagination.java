package com.xcz.afcs.core.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Pagination implements Serializable {

    private Integer offset;


    private Integer pageSize;


    private Integer pageNo;


}
