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
import com.ns.deneme.neo4j.api.IEntityAPI;
import com.ns.deneme.neo4j.domain.App;
import com.ns.deneme.neo4j.domain.Entity;
import com.ns.deneme.vo.AppEntityVO;
import com.ns.deneme.vo.EntityVO;

@Controller
@RequestMapping(value = "/dev/entity", method = RequestMethod.POST)
public class EntityController {
	
	private static Logger logger = LoggerFactory.getLogger(EntityController.class);
	
	@Autowired
	private IAppAPI appAPI;
	
	@Autowired
	private IEntityAPI entityAPI;

	@RequestMapping(value = "/getEntities")
	public @ResponseBody Map<String, Object> getEntities(@RequestParam(value="appId") Long appId, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Getting Entities for App Id : " + appId + " ...");
	   	try {
	   		App app = appAPI.findOne(appId);
	   		List<Entity> entities = new ArrayList<>();
	   		entities.addAll(app.getAppEntities());
	   		data.put("entities", entityAPI.mapEntitiesToJSON(entities)); 
	   		data.put("status", "1");
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Can Not Retrieve Entities For App Id : " + appId + " !!!");
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Getting Entities Is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/deleteEntity")
	public @ResponseBody Map<String, Object> deleteEntity(@RequestParam(value="entityId") Long entityId, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Deleting Entity with Id : " + entityId + " ...");
	   	try {
	   		entityAPI.deleteById(entityId);
	   		data.put("status", "1");
			data.put("message", "Entity Is Successfully Deleted");
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Deleting Entity was Failed!!!");
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Deleting Entity is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/createEntity", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> createEntity(@RequestBody AppEntityVO appEntityVO, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Creating Entity with Name : " + appEntityVO.getEntityName() + " to App Id : " + appEntityVO.getAppId());
	   	try {
	   		if (appEntityVO.getAppId() != null && appEntityVO.getAppId() > 0 && StringUtils.isNotEmpty(appEntityVO.getEntityName())) {
	   			Entity entity = new Entity();
	   			entity.setEntityName(appEntityVO.getEntityName());
	   			entityAPI.save(entity);
	   			
	   			App app = appAPI.findOne(appEntityVO.getAppId());
	   			app.getAppEntities().add(entity);
	   			appAPI.save(app);
	   			data.put("status", "1");
	   			data.put("id", entity.getId());
				data.put("message", "Entity Is Successfully Created and Added To The App.");
			} else {
				data.put("status", "-2");
				data.put("message", "App Id and Entity Name Should Not Be Null!!!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Creating Entity Was Failed!!!");
		} finally {
			logger.debug("Creating Entity is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/updateEntity", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> updateEntity(@RequestBody EntityVO entityVO, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Update Entity with Id : " + entityVO.getEntityId() + "  Name : " + entityVO.getEntityName());
	   	try {
	   		if (entityVO.getEntityId() != null && entityVO.getEntityId() > 0 
	   				&& StringUtils.isNotEmpty(entityVO.getEntityName())) {
	   			Entity entity = entityAPI.findOne(entityVO.getEntityId());
	   			entity.setEntityName(entityVO.getEntityName());
	   			entityAPI.save(entity);
	   			data.put("status", "1");
				data.put("message", "Entity Is Successfully Updated.");
			} else {
				data.put("status", "-2");
				data.put("message", "Entity Id and Entity Name Should Not Be Null!!!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Updating Entity Was Failed!!!");
		} finally {
			logger.debug("Updating Entity is Ended.");
		}
	 	return data;
	}
	
}
