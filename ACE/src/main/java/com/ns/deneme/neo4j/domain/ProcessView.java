package com.ns.deneme.neo4j.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@SuppressWarnings("serial")
@NodeEntity
public class ProcessView extends AbstractEntity {

	@Indexed(unique = true)
	private String viewName;

	@Fetch
	@RelatedTo(type = "knows", direction = Direction.BOTH)
	private ProcessComponent startProcess;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public ProcessComponent getStartProcess() {
		return startProcess;
	}

	public void setStartProcess(ProcessComponent startProcess) {
		this.startProcess = startProcess;
	}

}