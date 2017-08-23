package com.xcz.afcs.api.result;

import com.xcz.afcs.api.view.BaseView;
import com.xcz.afcs.validate.annotation.ApiField;

import java.util.Collection;
import java.util.List;

public class PageResult<T extends BaseView> extends BaseResult {

    public PageResult(int retCode, String retMsg) {
        super(retCode, retMsg);
    }

    public PageResult() {

    }

    @ApiField(name = "列表数据对象")
    private Collection<T> data;

    @ApiField(name = "总记录数")
    private Integer total;

    @ApiField(name = "总页数")
    private Integer pageSize;

    @ApiField(name = "当前页号")
    private Integer pageNum;


    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

}
