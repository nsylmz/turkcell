package com.ns.deneme.vo;

import java.util.List;
import java.util.Map;

public class ProcessViewVO {

	private String viewName;
	
	private String startProcessName;

	private List<ProcessComponentVO> components;
	
	private Map<String, String> connections;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getStartProcessName() {
		return startProcessName;
	}

	public void setStartProcessName(String startProcessName) {
		this.startProcessName = startProcessName;
	}

	public List<ProcessComponentVO> getComponents() {
		return components;
	}

	public void setComponents(List<ProcessComponentVO> components) {
		this.components = components;
	}

	public Map<String, String> getConnections() {
		return connections;
	}

	public void setConnections(Map<String, String> connections) {
		this.connections = connections;
	}
	
}
