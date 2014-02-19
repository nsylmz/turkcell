package com.ns.deneme.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MenuVO implements Serializable {
	
	private Long menuId;
	
	private String menuName;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
}
