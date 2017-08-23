package com.xcz.afcs.http;

public enum ResponseType {
	JSON("application/json"),
	XML("application/xml"),
	HTML("text/html"),
	; 
    private String name;
    
	private ResponseType(String name) {
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
