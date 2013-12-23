package com.ns.deneme.neo4j.domain;

import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class View extends AbstractEntity {

	private String viewName;

	private String chart;

	private String bodyChildren;

	private String featureBars;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getChart() {
		return chart;
	}

	public void setChart(String chart) {
		this.chart = chart;
	}

	public String getBodyChildren() {
		return bodyChildren;
	}

	public void setBodyChildren(String bodyChildren) {
		this.bodyChildren = bodyChildren;
	}

	public String getFeatureBars() {
		return featureBars;
	}

	public void setFeatureBars(String featureBars) {
		this.featureBars = featureBars;
	}

}