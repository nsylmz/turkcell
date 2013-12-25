package com.ns.deneme.neo4j.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@SuppressWarnings("serial")
@NodeEntity
public class UIView extends AbstractEntity {

	@Indexed(unique = true)
	private String viewName;

	@Fetch
	@RelatedTo(type = "has", direction = Direction.BOTH)
	private Set<UIComponent> components;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public Set<UIComponent> getComponents() {
		return components;
	}

	public void setComponents(Set<UIComponent> components) {
		this.components = components;
	}

}
