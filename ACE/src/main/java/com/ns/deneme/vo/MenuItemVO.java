package com.ns.deneme.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MenuItemVO implements Serializable {
	
	private Long menuItemId;
	
	private Long menuId;
	
	private String menuItemName;
	
	private Long menuItemPageId;
	
	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public Long getMenuItemPageId() {
		return menuItemPageId;
	}

	public void setMenuItemPageId(Long menuItemPageId) {
		this.menuItemPageId = menuItemPageId;
	}

}
