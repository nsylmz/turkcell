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

import com.ns.deneme.neo4j.api.IAppAPI;
import com.ns.deneme.neo4j.domain.App;
import com.ns.deneme.neo4j.repository.AppRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class AppAPI implements IAppAPI {
	
	@Autowired
	public AppRepository repository;
	
	public void save(App app) {
		repository.save(app);
	}

	public void delete(App app) {
		repository.delete(repository.findOne(app.getId()));
	}

	public void deleteById(Long id) {
		repository.delete(repository.findOne(id));
	}

	public App findByPropertyValue(String indexedPropertyName, String propertyValue) {
		return repository.findByPropertyValue(indexedPropertyName.trim(), propertyValue.trim());
	}

	public App findOne(Long id) {
		return repository.findOne(id);
	}
	
	public List<App> findAll() {
		List<App> resultList = new ArrayList<App>();
    	EndResult<App> resultSet = repository.findAll();
    	Iterator<App> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		resultList.add((App) iter.next());
		}
    	return resultList;
	}
}
