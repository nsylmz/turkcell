package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.App;

public interface IAppAPI {

	public void save(App app);

	public void delete(App app);

	public void deleteById(Long id);

	public App findByPropertyValue(String indexedPropertyName, String propertyValue);

	public List<App> findAll();

	public App findOne(Long id);
	
}
