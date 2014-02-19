package com.ns.deneme.neo4j.api.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ns.deneme.neo4j.api.IEntityAPI;
import com.ns.deneme.neo4j.domain.Entity;
import com.ns.deneme.neo4j.domain.Menu;
import com.ns.deneme.neo4j.repository.EntityRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class EntityAPI implements IEntityAPI {
	
	@Autowired
	public EntityRepository repository;
	
	public void save(Entity entity) {
		repository.save(entity);
	}

	public void delete(Entity entity) {
		repository.delete(repository.findOne(entity.getId()));
	}

	public void deleteById(Long id) {
		repository.delete(repository.findOne(id));
	}

	public Entity findByPropertyValue(String indexedPropertyName, String propertyValue) {
		return repository.findByPropertyValue(indexedPropertyName.trim(), propertyValue.trim());
	}

	public Entity findOne(Long id) {
		return repository.findOne(id);
	}
	
	public List<Entity> findAll() {
		List<Entity> resultList = new ArrayList<Entity>();
    	EndResult<Entity> resultSet = repository.findAll();
    	Iterator<Entity> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		resultList.add((Entity) iter.next());
		}
    	return resultList;
	}
	
	public List<String[]> mapEntitiesToJSON(List<Entity> entities) {
		List<String[]> jsonList = new ArrayList<>();
		String[] jsonEntity;
		for (Entity entity : entities) {
			jsonEntity = new String[2];
			jsonEntity[0] = entity.getId().toString();
			jsonEntity[1] = entity.getEntityName();
			jsonList.add(jsonEntity);
		}
		return jsonList;
	}
}
