package com.ns.deneme.neo4j.domain;

import org.springframework.data.neo4j.annotation.GraphId;

public abstract class AbstractEntity {

	@GraphId
	private Long id;

	public Long getId() {
		return id;
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