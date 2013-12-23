package com.ns.deneme.neo4j.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class ProcessRunConfig extends AbstractEntity {
	
	private String configParamName;
	
	private String configMethodParamName;
	
	@RelatedTo(type = "knows", direction = Direction.INCOMING)
	private Set<MappingHelper> configMethodParamMappingHelper;
	
	private String configParamType;
	
	private String configParamValue;

	public String getConfigParamName() {
		return configParamName;
	}

	public void setConfigParamName(String configParamName) {
		this.configParamName = configParamName;
	}

	public String getConfigMethodParamName() {
		return configMethodParamName;
	}

	public void setConfigMethodParamName(String configMethodParamName) {
		this.configMethodParamName = configMethodParamName;
	}

	public Set<MappingHelper> getConfigMethodParamMappingHelper() {
		return configMethodParamMappingHelper;
	}

	public void setConfigMethodParamMappingHelper(
			Set<MappingHelper> configMethodParamMappingHelper) {
		this.configMethodParamMappingHelper = configMethodParamMappingHelper;
	}

	public String getConfigParamType() {
		return configParamType;
	}

	public void setConfigParamType(String configParamType) {
		this.configParamType = configParamType;
	}

	public String getConfigParamValue() {
		return configParamValue;
	}

	public void setConfigParamValue(String configParamValue) {
		this.configParamValue = configParamValue;
	}
	
}
