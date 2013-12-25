package com.ns.deneme.neo4j.domain;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@SuppressWarnings("serial")
@NodeEntity
public class UIComponent extends AbstractEntity {

	@Indexed
	private String componentName;
	
	private String componentLabel;

	private String elementName;

	private String elementType;

	private String positionLeft;

	private String positionTop;

	private String processName;

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getElementName() {
		return elementName;
	}

	public String getComponentLabel() {
		return componentLabel;
	}

	public void setComponentLabel(String componentLabel) {
		this.componentLabel = componentLabel;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
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

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

}