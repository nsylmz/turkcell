package com.ns.deneme.vo;

import java.util.List;

public class ProcessViewVO {

	private String viewName;

	private List<ProcessComponentVO> components;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public List<ProcessComponentVO> getComponents() {
		return components;
	}

	public void setComponents(List<ProcessComponentVO> components) {
		this.components = components;
	}
	
}
