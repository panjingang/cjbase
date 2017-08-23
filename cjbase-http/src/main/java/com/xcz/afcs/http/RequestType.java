package com.xcz.afcs.http;

public enum RequestType {
	FORM("application/x-www-form-urlencoded"),
	JSON("application/json"),
	STREAM("application/octet-stream"),
	MIME("multipart/form-data"),
	; 
    private String name;
    
	private RequestType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	
	@Override
    public String toString() {
		return new StringBuilder().append(name).toString();
	}
}
