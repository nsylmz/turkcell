package com.ns.deneme.neo4j.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@SuppressWarnings("serial")
@NodeEntity
public class HtmlElement extends AbstractEntity {

	@Indexed
	private String elementLabel;

	private String elementHtml;
	
	private String iconName;

	@Fetch
	@RelatedTo(type = "parentCategory", direction = Direction.OUTGOING)
	private HtmlElementCategory category;

	public String getElementLabel() {
		return elementLabel;
	}

	public void setElementLabel(String elementLabel) {
		this.elementLabel = elementLabel;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getElementHtml() {
		return elementHtml;
	}

	public void setElementHtml(String elementHtml) {
		this.elementHtml = elementHtml;
	}

	public HtmlElementCategory getCategory() {
		return category;
	}

	public void setCategory(HtmlElementCategory category) {
		this.category = category;
	}

}