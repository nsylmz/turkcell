package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.Page;

public interface IPageAPI {

	public void save(Page page);

	public void delete(Page page);

	public void deleteById(Long id);

	public Page findByPropertyValue(String indexedPropertyName, String propertyValue);

	public List<Page> findAll();

	public Page findOne(Long id);
	
}
