package com.ns.deneme.neo4j.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@SuppressWarnings("serial")
@NodeEntity
public class TemplateNodeEntity implements Serializable {

	@GraphId
	private Long id;
	
	@Indexed(unique = true)
	private String name;
	
	@Version
	private int version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
