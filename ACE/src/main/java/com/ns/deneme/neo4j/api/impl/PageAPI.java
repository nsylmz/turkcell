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

import com.ns.deneme.neo4j.api.IPageAPI;
import com.ns.deneme.neo4j.domain.Page;
import com.ns.deneme.neo4j.repository.PageRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class PageAPI implements IPageAPI {
	
	@Autowired
	public PageRepository repository;
	
	public void save(Page page) {
		repository.save(page);
	}

	public void delete(Page page) {
		repository.delete(repository.findOne(page.getId()));
	}

	public void deleteById(Long id) {
		repository.delete(repository.findOne(id));
	}

	public Page findByPropertyValue(String indexedPropertyName, String propertyValue) {
		return repository.findByPropertyValue(indexedPropertyName.trim(), propertyValue.trim());
	}

	public Page findOne(Long id) {
		return repository.findOne(id);
	}
	
	public List<Page> findAll() {
		List<Page> resultList = new ArrayList<Page>();
    	EndResult<Page> resultSet = repository.findAll();
    	Iterator<Page> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		resultList.add((Page) iter.next());
		}
    	return resultList;
	}
}
