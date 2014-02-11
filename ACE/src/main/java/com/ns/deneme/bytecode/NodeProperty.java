package com.ns.deneme.bytecode;

import java.util.List;

public class NodeProperty {
	
	private String propertyName;
	
	private Class<?> propertyType;
	
	private List<NodeAnnotationProperty> annotations;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Class<?> propertyType) {
		this.propertyType = propertyType;
	}

	public List<NodeAnnotationProperty> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<NodeAnnotationProperty> annotations) {
		this.annotations = annotations;
	}
	
}
