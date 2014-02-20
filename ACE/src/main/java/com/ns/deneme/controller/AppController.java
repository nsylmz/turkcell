package com.ns.deneme.controller;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ns.deneme.neo4j.api.IAppAPI;
import com.ns.deneme.neo4j.domain.App;
import com.ns.deneme.neo4j.domain.Entity;
import com.ns.deneme.neo4j.domain.Menu;
import com.ns.deneme.neo4j.domain.Page;

@Controller
@RequestMapping(value = "/app")
public class AppController {
	
	private static Logger logger = LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	private IAppAPI appAPI;
	
	@RequestMapping(value = "/{appName}", method = RequestMethod.GET)
	public String getApp(@PathVariable String appName, Model model) {
	   	logger.debug("Getting App with Name : " + appName + "...");
	   	try {
	   		if (StringUtils.isNotEmpty(appName)) {
	   			App app = appAPI.findByPropertyValue("appName", appName);
	   			if (app != null) {
	   				model.addAttribute("appName", appName);
	   				model.addAttribute("appId", app.getId());
	   				List<String[]> appMenus = new ArrayList<String[]>();
	   				List<String[]> appPages = new ArrayList<String[]>();
	   				List<String[]> appEntities = new ArrayList<String[]>();
	   				Iterator<Menu> menuIter = app.getAppMenus().iterator();
	   				Menu menu;
	   				while (menuIter.hasNext()) {
						menu = (Menu) menuIter.next();
						appMenus.add(new String[]{menu.getId().toString(), menu.getMenuName()});
					}
	   				model.addAttribute("appMenus", appMenus);
	   				
	   				Iterator<Page> pageIter = app.getAppPages().iterator();
	   				Page page;
	   				while (pageIter.hasNext()) {
	   					page = (Page) pageIter.next();
	   					appPages.add(new String[]{page.getId().toString(), page.getPageName()});
					}
	   				model.addAttribute("appPages", appPages);
	   				
	   				Iterator<Entity> entityIter = app.getAppEntities().iterator();
	   				Entity entity;
	   				while (entityIter.hasNext()) {
	   					entity = (Entity) entityIter.next();
	   					appEntities.add(new String[]{entity.getId().toString(), entity.getEntityName()});
					}
	   				model.addAttribute("appEntities", appEntities);
				} else {
					logger.error("There is not exists any app which is named : " + appName);
				}
	   		} else {
	   			logger.error("App Name is Null.");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Getting App is Ended.");
		}
	 	return "/app/App";
	}
	
	@RequestMapping(value = "/ACE", method = RequestMethod.GET)
	public String getAppNames(Model model) {
	   	logger.debug("Getting Apps...");
	   	try {
	   		List<App> apps = appAPI.findAll();
	   		List<String> appNames = new ArrayList<String>();
	   		if (apps != null) {
	   			for (App app : apps) {
	   				appNames.add(app.getAppName());
				}
			}
	   		model.addAttribute("appNames", appNames);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Getting Apps is Ended.");
		}
	 	return "/app/ACE";
	}
	
	@RequestMapping(value = "/createApp", consumes={MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createApp(@RequestBody String appName, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Creating App with Name : " + appName + "...");
	   	try {
	   		if (StringUtils.isNotEmpty(appName)) {
	   			App app = new App();
	   			app.setAppName(appName);
	   			appAPI.save(app);
	   			data.put("status", "1");
				data.put("message", "App Is Successfully Created.");
			} else {
				data.put("status", "-2");
				data.put("message", "App Name Should Not Be Null!!!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Createing App Was Failed!!!");
		} finally {
			logger.debug("Creating App is Ended");
		}
	 	return data;
	}

}
