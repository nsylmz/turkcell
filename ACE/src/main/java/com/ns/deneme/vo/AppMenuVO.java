package com.ns.deneme.vo;

import java.io.Serializable;


@SuppressWarnings("serial")
public class AppMenuVO implements Serializable {

	private Long appId;
	
	private String menuName;

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
}
