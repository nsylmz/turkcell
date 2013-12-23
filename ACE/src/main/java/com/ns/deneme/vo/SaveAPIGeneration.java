package com.ns.deneme.vo;

import java.util.Map;

import com.ns.deneme.neo4j.domain.View;
import com.ns.deneme.ws.WSRequestParameter;

public class SaveAPIGeneration {

	private View view;

	private String processName;

	private String processType;

	private Map<String, WSRequestParameter> params;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

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

	public Map<String, WSRequestParameter> getParams() {
		return params;
	}

	public void setParams(Map<String, WSRequestParameter> params) {
		this.params = params;
	}

}
