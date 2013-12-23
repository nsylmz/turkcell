package com.ns.deneme.neo4j.domain;

import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class View extends AbstractEntity {
	
	private String viewName;
	
	private byte[] view;

	private byte[] bodyChildren;
	
	private byte[] featureBars;
	
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public byte[] getView() {
		return view;
	}

	public void setView(byte[] view) {
		this.view = view;
	}

	public byte[] getBodyChildren() {
		return bodyChildren;
	}

	public void setBodyChildren(byte[] bodyChildren) {
		this.bodyChildren = bodyChildren;
	}

	public byte[] getFeatureBars() {
		return featureBars;
	}

	public void setFeatureBars(byte[] featureBars) {
		this.featureBars = featureBars;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (getId() == null || obj == null
				|| !getClass().equals(obj.getClass())) {
			return false;
		}
		return getId().equals(((AbstractEntity) obj).getId());

	}

	@Override
	public int hashCode() {
		return getId() == null ? 0 : getId().hashCode();
	}
}