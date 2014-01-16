package com.ns.deneme.neo4j.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@SuppressWarnings("serial")
@NodeEntity
public class TemplateEntity implements Serializable {

	@GraphId
	private Long id;
	
	@Version
	private int version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
