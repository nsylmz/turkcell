package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.Menu;

public interface IMenuAPI {

	public void save(Menu menu);

	public void delete(Menu menu);

	public void deleteById(Long id);

	public Menu findByPropertyValue(String indexedPropertyName, String propertyValue);

	public List<Menu> findAll();

	public Menu findOne(Long id);

	public List<String[]> mapMenusToJSON(List<Menu> menus);
	
}
