package com.ns.deneme.neo4j.domain;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@SuppressWarnings("serial")
@NodeEntity
public class MappingHelper extends AbstractEntity {
	
	@Indexed(unique = true)
	private String mapName;
	
	private String mapRule;

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getMapRule() {
		return mapRule;
	}

	public void setMapRule(String mapRule) {
		this.mapRule = mapRule;
	}
	
}
