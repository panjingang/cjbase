package com.xcz.afcs.core.model;

import java.io.Serializable;

public class SessionIdentity implements Serializable {

	protected String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
