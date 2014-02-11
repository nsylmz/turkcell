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

import com.ns.deneme.neo4j.api.IFieldAnnotationAPI;
import com.ns.deneme.neo4j.domain.FieldAnnotation;
import com.ns.deneme.neo4j.repository.FieldAnnotationRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class FieldAnnotationAPI implements IFieldAnnotationAPI {
	
	@Autowired
	public FieldAnnotationRepository repository;
	
	public void save(FieldAnnotation fieldAnnotation) {
		repository.save(fieldAnnotation);
	}

	public void delete(FieldAnnotation fieldAnnotation) {
		repository.delete(repository.findOne(fieldAnnotation.getId()));
	}

	public void deleteById(Long id) {
		repository.delete(repository.findOne(id));
	}

	public FieldAnnotation findByPropertyValue(String indexedPropertyName, String propertyValue) {
		return repository.findByPropertyValue(indexedPropertyName.trim(), propertyValue.trim());
	}

	public FieldAnnotation findOne(Long id) {
		return repository.findOne(id);
	}
	
	public List<FieldAnnotation> findAll() {
		List<FieldAnnotation> resultList = new ArrayList<FieldAnnotation>();
    	EndResult<FieldAnnotation> resultSet = repository.findAll();
    	Iterator<FieldAnnotation> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		resultList.add((FieldAnnotation) iter.next());
		}
    	return resultList;
	}
}
