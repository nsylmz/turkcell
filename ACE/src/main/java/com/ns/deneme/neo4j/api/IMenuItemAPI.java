package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.MenuItem;

public interface IMenuItemAPI {

	public void save(MenuItem menuItem);

	public void delete(MenuItem menuItem);

	public void deleteById(Long id);

	public MenuItem findByPropertyValue(String indexedPropertyName, String propertyValue);

	public List<MenuItem> findAll();

	public MenuItem findOne(Long id);
	
}
