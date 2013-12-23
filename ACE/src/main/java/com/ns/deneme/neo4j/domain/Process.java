package com.ns.deneme.neo4j.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Process extends AbstractEntity {
	
	private String processName;
	
	private String processType;

	@RelatedTo(type = "knows", direction = Direction.OUTGOING)
	private Set<ProcessRunConfig> processRunConfig;
	
	private String processRunClass;
	
	private String processRunMethod;

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public Set<ProcessRunConfig> getProcessRunConfig() {
		return processRunConfig;
	}

	public void setProcessRunConfig(Set<ProcessRunConfig> processRunConfig) {
		this.processRunConfig = processRunConfig;
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