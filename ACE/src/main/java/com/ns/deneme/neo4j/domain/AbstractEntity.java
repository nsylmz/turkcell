package com.ns.deneme.neo4j.domain;

import org.springframework.data.neo4j.annotation.GraphId;

public abstract class AbstractEntity {

	@GraphId
	private Long id;

	public Long getId() {
		return id;
	}
	
}