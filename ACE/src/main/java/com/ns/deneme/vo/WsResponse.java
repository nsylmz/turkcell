package com.ns.deneme.vo;

import java.util.List;

public class WsResponse {

	private String className;
	
	private List<WsField> fields;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<WsField> getFields() {
		return fields;
	}

	public void setFields(List<WsField> fields) {
		this.fields = fields;
	}
	
}
