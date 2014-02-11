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

import com.ns.deneme.neo4j.api.IMenuItemAPI;
import com.ns.deneme.neo4j.domain.MenuItem;
import com.ns.deneme.neo4j.repository.MenuItemRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class MenuItemAPI implements IMenuItemAPI {
	
	@Autowired
	public MenuItemRepository repository;
	
	public void save(MenuItem menuItem) {
		repository.save(menuItem);
	}

	public void delete(MenuItem menuItem) {
		repository.delete(repository.findOne(menuItem.getId()));
	}

	public void deleteById(Long id) {
		repository.delete(repository.findOne(id));
	}

	public MenuItem findByPropertyValue(String indexedPropertyName, String propertyValue) {
		return repository.findByPropertyValue(indexedPropertyName.trim(), propertyValue.trim());
	}

	public MenuItem findOne(Long id) {
		return repository.findOne(id);
	}
	
	public List<MenuItem> findAll() {
		List<MenuItem> resultList = new ArrayList<MenuItem>();
    	EndResult<MenuItem> resultSet = repository.findAll();
    	Iterator<MenuItem> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		resultList.add((MenuItem) iter.next());
		}
    	return resultList;
	}
}
