package com.ns.deneme.vo;

import java.util.Map;

import com.ns.deneme.ws.WSRequestParameter;

public class ProcessComponentVO {

	private String processName;

	private String processType;

	private String positionLeft;

	private String positionTop;

	private Map<String, WSRequestParameter> params;

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getPositionLeft() {
		return positionLeft;
	}

	public void setPositionLeft(String positionLeft) {
		this.positionLeft = positionLeft;
	}

	public String getPositionTop() {
		return positionTop;
	}

	public void setPositionTop(String positionTop) {
		this.positionTop = positionTop;
	}

	public Map<String, WSRequestParameter> getParams() {
		return params;
	}

	public void setParams(Map<String, WSRequestParameter> params) {
		this.params = params;
	}

}
