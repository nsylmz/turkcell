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

import com.ns.deneme.neo4j.api.ITemplateAPI;
import com.ns.deneme.neo4j.domain.TemplateNodeEntity;
import com.ns.deneme.neo4j.repository.TemplateRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class TemplateAPI implements ITemplateAPI {
	
	@Autowired
	public TemplateRepository repository;
	
	public void save(TemplateNodeEntity nodeEntity) {
		repository.save(nodeEntity);
	}

	public void delete(TemplateNodeEntity nodeEntity) {
		repository.delete(repository.findOne(nodeEntity.getId()));
	}

	public void deleteById(Long id) {
		repository.delete(repository.findOne(id));
	}

	public TemplateNodeEntity findByPropertyValue(String indexedPropertyName, String propertyValue) {
		return repository.findByPropertyValue(indexedPropertyName.trim(), propertyValue.trim());
	}

	public TemplateNodeEntity findOne(Long id) {
		return repository.findOne(id);
	}
	
	public List<TemplateNodeEntity> findAll() {
		List<TemplateNodeEntity> resultList = new ArrayList<TemplateNodeEntity>();
    	EndResult<TemplateNodeEntity> resultSet = repository.findAll();
    	Iterator<TemplateNodeEntity> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		resultList.add((TemplateNodeEntity) iter.next());
		}
    	return resultList;
	}
}
