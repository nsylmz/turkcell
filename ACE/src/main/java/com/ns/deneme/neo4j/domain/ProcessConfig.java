package com.ns.deneme.neo4j.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@SuppressWarnings("serial")
@NodeEntity
public class ProcessConfig extends AbstractEntity {

	@Indexed(unique = true)
	private String configName;

	@Fetch
	@RelatedTo(type = "knows", direction = Direction.BOTH)
	private Set<ProcessInputConfig> inputConfig;

	private String processRunClass;
	
	private String processRunMethod;

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public Set<ProcessInputConfig> getInputConfig() {
		return inputConfig;
	}

	public void setInputConfig(Set<ProcessInputConfig> inputConfig) {
		this.inputConfig = inputConfig;
	}

	public String getProcessRunClass() {
		return processRunClass;
	}

	public void setProcessRunClass(String processRunClass) {
		this.processRunClass = processRunClass;
	}

	public String getProcessRunMethod() {
		return processRunMethod;
	}

	public void setProcessRunMethod(String processRunMethod) {
		this.processRunMethod = processRunMethod;
	}
	
}
