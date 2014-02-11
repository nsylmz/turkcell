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

import com.ns.deneme.neo4j.api.IFieldAnnotationMemberAPI;
import com.ns.deneme.neo4j.domain.FieldAnnotationMember;
import com.ns.deneme.neo4j.repository.FieldAnnotationMemberRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class FieldAnnotationMemberAPI implements IFieldAnnotationMemberAPI {
	
	@Autowired
	public FieldAnnotationMemberRepository repository;
	
	public void save(FieldAnnotationMember fieldAnnotationMember) {
		repository.save(fieldAnnotationMember);
	}

	public void delete(FieldAnnotationMember fieldAnnotationMember) {
		repository.delete(repository.findOne(fieldAnnotationMember.getId()));
	}

	public void deleteById(Long id) {
		repository.delete(repository.findOne(id));
	}

	public FieldAnnotationMember findByPropertyValue(String indexedPropertyName, String propertyValue) {
		return repository.findByPropertyValue(indexedPropertyName.trim(), propertyValue.trim());
	}

	public FieldAnnotationMember findOne(Long id) {
		return repository.findOne(id);
	}
	
	public List<FieldAnnotationMember> findAll() {
		List<FieldAnnotationMember> resultList = new ArrayList<FieldAnnotationMember>();
    	EndResult<FieldAnnotationMember> resultSet = repository.findAll();
    	Iterator<FieldAnnotationMember> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		resultList.add((FieldAnnotationMember) iter.next());
		}
    	return resultList;
	}
}
