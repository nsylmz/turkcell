package com.ns.deneme.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ns.deneme.neo4j.api.IAppAPI;
import com.ns.deneme.neo4j.api.IMenuAPI;
import com.ns.deneme.neo4j.api.IMenuItemAPI;
import com.ns.deneme.neo4j.api.IPageAPI;
import com.ns.deneme.neo4j.domain.App;
import com.ns.deneme.neo4j.domain.Menu;
import com.ns.deneme.neo4j.domain.MenuItem;
import com.ns.deneme.neo4j.domain.Page;
import com.ns.deneme.vo.AppMenuVO;
import com.ns.deneme.vo.MenuItemVO;
import com.ns.deneme.vo.MenuVO;

@Controller
@RequestMapping(value = "/dev/menu", method = RequestMethod.POST)
public class MenuController {
	
	private static Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private IAppAPI appAPI;
	
	@Autowired
	private IMenuAPI menuAPI;

	@Autowired
	private IMenuItemAPI menuItemAPI;
	
	@Autowired
	private IPageAPI pageAPI;
	
	@RequestMapping(value = "/getMenus")
	public @ResponseBody Map<String, Object> getMenus(@RequestParam(value="appId") Long appId, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Getting Menus for App Id : " + appId + " ...");
	   	try {
	   		App app = appAPI.findOne(appId);
	   		List<Menu> menus = new ArrayList<>();
	   		menus.addAll(app.getAppMenus());
	   		data.put("menus", menuAPI.mapMenusToJSON(menus)); 
	   		data.put("status", "1");
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Can Not Retrieve Menus For App Id : " + appId + " !!!");
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Getting Menus is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/getMenuItems")
	public @ResponseBody Map<String, Object> getMenuItems(@RequestParam(value="appId") Long appId, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Getting MenuItems for App Id : " + appId + " ...");
	   	try {
	   		List<MenuItem> menuItems = new ArrayList<>();
	   		App app = appAPI.findOne(appId);
	   		if (app != null) {
				Set<Menu> menus = app.getAppMenus();
				Iterator<Menu> iter = menus.iterator();
				Menu menu;
				while (iter.hasNext()) {
					menu = (Menu) iter.next();
					menuItems.addAll(menu.getMenuItems());
				}
			}
	   		data.put("menuItems", menuItemAPI.mapMenuItemsToJSON(menuItems));
	   		data.put("status", "1");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Can Not Retrieve Menu Items For App Id : " + appId + " !!!");
		} finally {
			logger.debug("Getting Menu Items is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/deleteMenu")
	public @ResponseBody Map<String, Object> deleteMenu(@RequestParam(value="menuId") Long menuId, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Deleting Menu with Id : " + menuId + " ...");
	   	try {
	   		menuAPI.deleteById(menuId);
	   		data.put("status", "1");
			data.put("message", "Menu Is Successfully Deleted");
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Deleting Menu was Failed!!!");
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Deleting Menu is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/deleteMenuItem")
	public @ResponseBody Map<String, Object> deleteMenuItem(@RequestParam(value="menuItemId") Long menuItemId, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Deleting Menu Item with Id : " + menuItemId + " ...");
	   	try {
	   		menuItemAPI.deleteById(menuItemId);
	   		data.put("status", "1");
			data.put("message", "Menu Item Is Successfully Deleted");
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Deleting Menu Item was Failed!!!");
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Deleting Menu Item is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/createMenu", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> createMenu(@RequestBody AppMenuVO appMenuVO, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Creating Menu with Name : " + appMenuVO.getMenuName() + " to App Id : " + appMenuVO.getAppId());
	   	try {
	   		if (appMenuVO.getAppId() != null && appMenuVO.getAppId() > 0 && StringUtils.isNotEmpty(appMenuVO.getMenuName())) {
	   			Menu menu = new Menu();
	   			menu.setMenuName(appMenuVO.getMenuName());
	   			menuAPI.save(menu);
	   			
	   			App app = appAPI.findOne(appMenuVO.getAppId());
	   			app.getAppMenus().add(menu);
	   			appAPI.save(app);
	   			data.put("status", "1");
	   			data.put("id", menu.getId());
				data.put("message", "Menu Is Successfully Created and Added To The App.");
			} else {
				data.put("status", "-2");
				data.put("message", "App Id and Menu Name Should Not Be Null!!!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Creating Menu Was Failed!!!");
		} finally {
			logger.debug("Creating Menu is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/updateMenu", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> updateMenu(@RequestBody MenuVO updateMenuVO, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Update Menu with Id : " + updateMenuVO.getMenuId() + "  Name : " + updateMenuVO.getMenuName());
	   	try {
	   		if (updateMenuVO.getMenuId() != null && updateMenuVO.getMenuId() > 0 
	   				&& StringUtils.isNotEmpty(updateMenuVO.getMenuName())) {
	   			Menu menu = menuAPI.findOne(updateMenuVO.getMenuId());
	   			menu.setMenuName(updateMenuVO.getMenuName());
	   			menuAPI.save(menu);
	   			data.put("status", "1");
	   			data.put("id", menu.getId());
				data.put("message", "Menu Is Successfully Updated.");
			} else {
				data.put("status", "-2");
				data.put("message", "Menu Id and Menu Name Should Not Be Null!!!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Updating Menu Was Failed!!!");
		} finally {
			logger.debug("Updating Menu is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/updateMenuItem", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> updateMenuItem(@RequestBody MenuItemVO menuItemVO, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Update Menu Item with Id : " + menuItemVO.getMenuItemId());
	   	try {
	   		if (menuItemVO.getMenuItemId() != null && menuItemVO.getMenuItemId() > 0 
	   				&& StringUtils.isNotEmpty(menuItemVO.getMenuItemName())) {
	   			
	   			MenuItem menuItem = menuItemAPI.findOne(menuItemVO.getMenuItemId());
	   			menuItem.setMenuItemName(menuItemVO.getMenuItemName());
	   			if (menuItemVO.getMenuItemPageId() != null && menuItemVO.getMenuItemPageId() > 0
	   					&& !menuItemVO.getMenuItemPageId().equals(menuItem.getMenuItemPage().getId())) {
	   				Page page = pageAPI.findOne(menuItemVO.getMenuItemPageId());
	   				if (page != null) {
	   					menuItem.setMenuItemPage(page);
					} else {
						menuItem.setMenuItemPage(null);
						logger.error("Given Page Id " + menuItemVO.getMenuItemPageId() + " Is Not Exsits!!!");
					}
				}
	   			if (menuItemVO.getMenuId() != null && menuItemVO.getMenuId() > 0) {
					Menu menu = menuAPI.findOne(menuItemVO.getMenuId());
					if (menu != null) {
						Menu oldMenu = menuItem.getMenu();
						if (!menu.getId().equals(oldMenu.getId())) {
							// Remove Old Relation
							oldMenu.getMenuItems().remove(menuItem);
							menuAPI.save(oldMenu);
							
							menuItem.setMenu(menu);
							menu.getMenuItems().add(menuItem);
							
							menuItemAPI.save(menuItem);
							menuAPI.save(menu);
						} else {
							menuItemAPI.save(menuItem);
						}
			   			data.put("status", "1");
			   			data.put("id", menuItem.getId());
						data.put("message", "Menu Is Successfully Updated.");
					} else {
						data.put("status", "-3");
						data.put("message", "Given Menu Id " + menuItemVO.getMenuId() + " Does Not Exsit!!!");
					}
	   			}
			} else {
				data.put("status", "-2");
				data.put("message", "Menu Item Id and Menu Item Name Should Not Be Null!!!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Updating Menu Item Was Failed!!!");
		} finally {
			logger.debug("Updating Menu Item is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/createMenuItem", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> createMenuItem(@RequestBody MenuItemVO menuItemVO, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Creating Menu Item with Name : " + menuItemVO.getMenuItemName());
	   	try {
	   		if (StringUtils.isNotEmpty(menuItemVO.getMenuItemName())) {
	   			MenuItem menuItem = new MenuItem();
	   			menuItem.setMenuItemName(menuItemVO.getMenuItemName());
	   			if (menuItemVO.getMenuId() != null && menuItemVO.getMenuId() > 0) {
					Menu menu = menuAPI.findOne(menuItemVO.getMenuId());
					if (menu != null) {
						menuItem.setMenu(menu);
						menu.getMenuItems().add(menuItem);
						if (menuItemVO.getMenuItemPageId() != null && menuItemVO.getMenuItemPageId() > 0
			   					&& !menuItemVO.getMenuItemPageId().equals(menuItem.getMenuItemPage().getId())) {
			   				Page page = pageAPI.findOne(menuItemVO.getMenuItemPageId());
			   				if (page != null) {
			   					menuItem.setMenuItemPage(page);
							} else {
								logger.error("Given Page Id " + menuItemVO.getMenuItemPageId() + " Does Not Exsit!!!");
							}
			   			}
			   			menuItemAPI.save(menuItem);
			   			menuAPI.save(menu);
			   			data.put("status", "1");
			   			data.put("id", menuItem.getId());
						data.put("message", "Menu Item Is Successfully Created.");
					} else {
						data.put("status", "-3");
						data.put("message", "Given Menu Id " + menuItemVO.getMenuId() + " Does Not Exsit!!!");
						logger.error("Given Menu Id " + menuItemVO.getMenuId() + " Does Not Exsit!!!");
					}
				}
			} else {
				data.put("status", "-2");
				data.put("message", "Menu Item Name Should Not Be Null!!!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Creating Menu Item Was Failed!!!");
		} finally {
			logger.debug("Creating Menu Item Is Ended.");
		}
	 	return data;
	}

}
