package com.xcz.afcs.api.result;

import com.xcz.afcs.validate.annotation.ApiField;

public class ObjectResult<T> extends BaseResult {
	public ObjectResult(int retCode, String retMsg) {
		super(retCode, retMsg);
	}

	public ObjectResult(){
		
	}
	private static final long serialVersionUID = 1L;

	@ApiField(name="数据对象")
	private T data;


	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
