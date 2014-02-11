package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.Entity;

public interface IEntityAPI {

	public void save(Entity entity);

	public void delete(Entity entity);

	public void deleteById(Long id);

	public Entity findByPropertyValue(String indexedPropertyName, String propertyValue);

	public List<Entity> findAll();

	public Entity findOne(Long id);
	
}
