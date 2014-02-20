package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.Field;

public interface IFieldAPI {

	public void save(Field field);

	public void delete(Field field);

	public void deleteById(Long id);

	public Field findByPropertyValue(String indexedPropertyName, String propertyValue);

	public List<Field> findAll();

	public Field findOne(Long id);

	public List<String[]> mapFieldsToJSON(List<Field> fields);
	
}
