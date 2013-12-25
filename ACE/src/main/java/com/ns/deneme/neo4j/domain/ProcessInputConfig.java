package com.ns.deneme.neo4j.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@SuppressWarnings("serial")
@NodeEntity
public class ProcessInputConfig extends AbstractEntity {

	@Indexed
	private String inputParamName;

	@Indexed
	private String configMethodParamName;

	@Fetch
	@RelatedTo(type = "hasMapper", direction = Direction.INCOMING)
	private Set<MappingHelper> configMethodParamMappingHelper;

	private String inputParamType;

	private String inputParamValue;

	public String getInputParamName() {
		return inputParamName;
	}

	public void setInputParamName(String inputParamName) {
		this.inputParamName = inputParamName;
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

	public String getInputParamType() {
		return inputParamType;
	}

	public void setInputParamType(String inputParamType) {
		this.inputParamType = inputParamType;
	}

	public String getInputParamValue() {
		return inputParamValue;
	}

	public void setInputParamValue(String inputParamValue) {
		this.inputParamValue = inputParamValue;
	}

}
