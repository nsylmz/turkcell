package com.ns.deneme.vo;

import java.util.Map;

import com.ns.deneme.ws.WSRequestParameter;

public class WsRunParams {

	private String wsdlUrl;
	
	private String operation;
	
	private Map<String, WSRequestParameter> paramNameAndparamValue;

	public String getWsdlUrl() {
		return wsdlUrl;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Map<String, WSRequestParameter> getParamNameAndparamValue() {
		return paramNameAndparamValue;
	}

	public void setParamNameAndparamValue(
			Map<String, WSRequestParameter> paramNameAndparamValue) {
		this.paramNameAndparamValue = paramNameAndparamValue;
	}

}
