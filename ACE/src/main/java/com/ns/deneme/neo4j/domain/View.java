package com.ns.deneme.neo4j.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@SuppressWarnings("serial")
@NodeEntity
public class View extends AbstractEntity {

	@Indexed(unique = true)
	private String viewName;

	private String chart;

	private String featureBars;
	
	@Fetch
	@RelatedTo(type = "knows", direction = Direction.BOTH)
	private Process process;

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

	public String getFeatureBars() {
		return featureBars;
	}

	public void setFeatureBars(String featureBars) {
		this.featureBars = featureBars;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}
	
}