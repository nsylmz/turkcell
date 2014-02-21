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
import com.ns.deneme.neo4j.api.IEntityAPI;
import com.ns.deneme.neo4j.api.IFieldAPI;
import com.ns.deneme.neo4j.domain.App;
import com.ns.deneme.neo4j.domain.Entity;
import com.ns.deneme.neo4j.domain.Field;
import com.ns.deneme.vo.FieldVO;

@Controller
@RequestMapping(value = "/dev/field", method = RequestMethod.POST)
public class FieldController {
	
	private static Logger logger = LoggerFactory.getLogger(FieldController.class);
	
	@Autowired
	private IAppAPI appAPI;
	
	@Autowired
	private IFieldAPI fieldAPI;
	
	@Autowired
	private IEntityAPI entityAPI;
	
	@RequestMapping(value = "/getFields")
	public @ResponseBody Map<String, Object> getFields(@RequestParam(value="appId") Long appId, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Getting Fields for App Id : " + appId + " ...");
	   	try {
	   		List<Field> fields = new ArrayList<>();
	   		App app = appAPI.findOne(appId);
	   		if (app != null) {
				Set<Entity> entities = app.getAppEntities();
				Iterator<Entity> iter = entities.iterator();
				Entity entity;
				while (iter.hasNext()) {
					entity = (Entity) iter.next();
					fields.addAll(entity.getFields());
				}
			}
	   		data.put("fields", fieldAPI.mapFieldsToJSON(fields)); 
	   		data.put("status", "1");
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Can Not Retrieve Fields For App Id : " + appId + " !!!");
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Getting Fields Is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/deleteField")
	public @ResponseBody Map<String, Object> deleteField(@RequestParam(value="fieldId") Long fieldId, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Deleting Field with Id : " + fieldId + " ...");
	   	try {
	   		fieldAPI.deleteById(fieldId);
	   		data.put("status", "1");
			data.put("message", "Field Is Successfully Deleted");
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Deleting Field was Failed!!!");
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Deleting Field is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/createField", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> createField(@RequestBody FieldVO fieldVO, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Creating Field with Name : " + fieldVO.getFieldName() + " to Entity Id : " + fieldVO.getEntityId());
	   	try {
	   		if (fieldVO.getEntityId() != null && fieldVO.getEntityId() > 0 
	   				&& StringUtils.isNotEmpty(fieldVO.getFieldName())) {
	   			Entity entity = entityAPI.findOne(fieldVO.getEntityId());
	   			
	   			if (entity != null) {
	   				Field field = new Field();
	   				field.setFieldName(fieldVO.getFieldName());
	   				field.setType(fieldVO.getFieldType());
	   				field.setEntity(entity);
	   				field.setIndexed(fieldVO.isIndexed());
	   				fieldAPI.save(field);
	   				
	   				entity.getFields().add(field);
	   				entityAPI.save(entity);
	   				data.put("status", "1");
	   				data.put("id", entity.getId());
	   				data.put("message", "Field Is Successfully Created and Added To The Entity.");
				} else {
					data.put("status", "-3");
					data.put("message", "Given Entity Id " + fieldVO.getEntityId() + " Does Not Exist!!!");
				}
			} else {
				data.put("status", "-2");
				data.put("message", "Entity Id and Field Name Should Not Be Null!!!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Creating Field Was Failed!!!");
		} finally {
			logger.debug("Creating Field is Ended.");
		}
	 	return data;
	}
	
	@RequestMapping(value = "/updateField", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> updateField(@RequestBody FieldVO fieldVO, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Update Field with Id : " + fieldVO.getFieldId() + "  Name : " + fieldVO.getFieldName());
	   	try {
	   		if (fieldVO.getFieldId() != null && fieldVO.getFieldId() > 0
	   				&& fieldVO.getEntityId() != null && fieldVO.getEntityId() > 0 
	   				&& StringUtils.isNotEmpty(fieldVO.getFieldType())
	   				&& StringUtils.isNotEmpty(fieldVO.getFieldName())) {
	   			Field field = fieldAPI.findOne(fieldVO.getFieldId());
	   			if (field != null) {
	   				if (!field.getEntity().getId().equals(fieldVO.getEntityId())) {
						Entity entity = entityAPI.findOne(fieldVO.getEntityId());
						if (entity != null) {
							// Remove Old Relation
							field.getEntity().getFields().remove(field);
							entityAPI.save(field.getEntity());
							
							field.setFieldName(fieldVO.getFieldName());
				   			field.setEntity(entity);
				   			field.setIndexed(fieldVO.isIndexed());
				   			field.setType(fieldVO.getFieldType());
				   			fieldAPI.save(field);
				   			
				   			entity.getFields().add(field);
				   			entityAPI.save(entity);
				   			
				   			data.put("status", "1");
							data.put("message", "Field Is Successfully Updated.");
						} else {
							data.put("status", "-4");
							data.put("message", "Given Entity Id " + fieldVO.getEntityId() + " Does Not Exsit!!!");
						}
					}
				} else {
					data.put("status", "-3");
					data.put("message", "Given Field Id " + fieldVO.getFieldId() + " Does Not Exsit!!!");
				}
			} else {
				data.put("status", "-2");
				data.put("message", "Field Id, Field Name, Field Type and Entity Id Should Not Be Null!!!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Updating Field Was Failed!!!");
		} finally {
			logger.debug("Updating Field is Ended.");
		}
	 	return data;
	}
	
	
}
