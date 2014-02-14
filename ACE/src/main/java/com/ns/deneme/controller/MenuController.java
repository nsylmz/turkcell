package com.ns.deneme.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.ns.deneme.neo4j.domain.App;
import com.ns.deneme.neo4j.domain.Menu;
import com.ns.deneme.neo4j.domain.MenuItem;

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
	
	@RequestMapping(value = "/getMenus", consumes={MediaType.APPLICATION_JSON_VALUE})
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
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Deleting Menu is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/getMenuItems", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> getMenuItems(@RequestParam(value="menuId") Long menuId, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Getting MenuItems for Menu Id : " + menuId + " ...");
	   	try {
	   		Menu menu = menuAPI.findOne(menuId);
	   		List<MenuItem> menuItems = new ArrayList<>();
	   		menuItems.addAll(menu.getMenuItems());
	   		data.put("menus", menuItemAPI.mapMenuItemsToJSON(menuItems));
	   		data.put("status", "1");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Deleting Menu is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/deleteMenu", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> deleteMenu(@RequestParam(value="menuId") Long menuId, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Deleting Menu with Id : " + menuId + " ...");
	   	try {
	   		menuAPI.deleteById(menuId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Deleting Menu is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/deleteMenuItem", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> deleteMenuItem(@RequestParam(value="menuItemId") Long menuItemId, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Deleting Menu Item with Id : " + menuItemId + " ...");
	   	try {
	   		menuItemAPI.deleteById(menuItemId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Deleting Menu Item is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/createMenu", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> createMenu(@RequestParam(value="appId") Long appId, @RequestBody String menuName, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Creating Menu with Name : " + menuName + " to App Id : " + appId);
	   	try {
	   		if (appId != null && appId > 0 && StringUtils.isNotEmpty(menuName)) {
	   			Menu menu = new Menu();
	   			menu.setMenuName(menuName);
	   			menuAPI.save(menu);
	   			
	   			App app = appAPI.findOne(appId);
	   			app.getAppMenus().add(menu);
	   			appAPI.save(app);
	   			data.put("status", "1");
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
	
	@RequestMapping(value = "/createMenuItem", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> createMenuItem(@RequestBody String menuItemName, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Creating Menu Item with Name : " + menuItemName);
	   	try {
	   		if (StringUtils.isNotEmpty(menuItemName)) {
	   			MenuItem menuItem = new MenuItem();
	   			menuItem.setMenuItemName(menuItemName);
	   			menuItemAPI.save(menuItem);
	   			data.put("status", "1");
				data.put("message", "Menu Item Is Successfully Created.");
			} else {
				data.put("status", "-2");
				data.put("message", "Menu Item Name Should Not Be Null!!!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Creating Menu Item Was Failed!!!");
		} finally {
			logger.debug("Creating Menu is Ended.");
		}
	 	return data;
	}

}
