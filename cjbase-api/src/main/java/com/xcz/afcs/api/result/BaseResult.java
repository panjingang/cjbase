package com.xcz.afcs.api.result;

import com.xcz.afcs.validate.annotation.ApiField;

import java.io.Serializable;

public class BaseResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiField(name="返回码")
	private Integer retCode;

	@ApiField(name="返回原因")
	private String retMsg;
	
	public BaseResult(){
		
	}
	
	public BaseResult (int retCode ,String retMsg){
		this.retCode= retCode;
		this.retMsg= retMsg;
	}


	public Integer getRetCode() {
		return retCode;
	}

	public void setRetCode(Integer retCode) {
		this.retCode = retCode;
	}


	public String getRetMsg() {
		return retMsg;
	}


	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

}
