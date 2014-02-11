package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.FieldAnnotationMember;

public interface IFieldAnnotationMemberAPI {

	public void save(FieldAnnotationMember fieldAnnotationMember);

	public void delete(FieldAnnotationMember fieldAnnotationMember);

	public void deleteById(Long id);

	public FieldAnnotationMember findByPropertyValue(String indexedPropertyName, String propertyValue);

	public List<FieldAnnotationMember> findAll();

	public FieldAnnotationMember findOne(Long id);
	
}
