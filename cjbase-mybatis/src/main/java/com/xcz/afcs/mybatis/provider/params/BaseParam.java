package com.xcz.afcs.mybatis.provider.params;

import com.xcz.afcs.core.model.Pagination;
import com.xcz.afcs.mybatis.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingang on 2017/9/15.
 */
@Getter
@Setter
public class BaseParam implements Serializable{

    private List<Order> orderList;

    private Pagination page;
}
