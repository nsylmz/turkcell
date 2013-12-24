package com.ns.deneme.vo;

import java.util.List;

public class UIViewVO {
	
	private String viewName;
	
	private List<UIComponentVO> components;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public List<UIComponentVO> getComponents() {
		return components;
	}

	public void setComponents(List<UIComponentVO> components) {
		this.components = components;
	} 
	
}
