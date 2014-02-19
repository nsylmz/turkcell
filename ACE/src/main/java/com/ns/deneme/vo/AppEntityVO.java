package com.ns.deneme.vo;

import java.io.Serializable;


@SuppressWarnings("serial")
public class AppEntityVO implements Serializable {

	private Long appId;
	
	private String entityName;

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
}
