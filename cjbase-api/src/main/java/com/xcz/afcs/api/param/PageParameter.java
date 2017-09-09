package com.xcz.afcs.api.param;

import com.xcz.afcs.core.model.Pagination;
import com.xcz.afcs.validate.annotation.ApiField;

import java.io.Serializable;

public class PageParameter extends BaseParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Integer DEFAULT_PAGE_SIZE = 10;

	@ApiField(name="分页号", required=false)
	private Integer pageNum = 1;

	@ApiField(name="每页记录数", required=false, desc="默认10条")
	private Integer pageSize = DEFAULT_PAGE_SIZE;

	private Pagination pagination;

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	public Integer getPageSize() {
		if (this.pageSize == null) {
			this.pageSize = DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}


	public Integer getPageNum() {
		if (this.pageNum == null) {
			this.pageNum = 1;
		}
		return this.pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getOffset() {
		return (this.getPageNum() - 1) * this.getPageSize();
	}


	public Pagination getPagination() {
		 if (pagination == null) {
		 	 pagination = new Pagination();
		 	 pagination.setOffset(getOffset());
		 	 pagination.setPageNo(pageNum);
		 	 pagination.setPageSize(pageSize);
		 }
		 return pagination;
	}
}
