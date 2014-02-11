package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.FieldAnnotation;

public interface IFieldAnnotationAPI {

	public void save(FieldAnnotation fieldAnnotation);

	public void delete(FieldAnnotation fieldAnnotation);

	public void deleteById(Long id);

	public FieldAnnotation findByPropertyValue(String indexedPropertyName, String propertyValue);

	public List<FieldAnnotation> findAll();

	public FieldAnnotation findOne(Long id);
	
}
