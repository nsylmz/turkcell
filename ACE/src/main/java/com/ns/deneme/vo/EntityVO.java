package com.ns.deneme.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EntityVO implements Serializable {

	private Long entityId;

	private String entityName;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

}
