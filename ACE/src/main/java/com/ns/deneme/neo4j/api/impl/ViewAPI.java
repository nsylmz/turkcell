package com.ns.deneme.neo4j.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ns.deneme.neo4j.api.IViewAPI;
import com.ns.deneme.neo4j.domain.View;
import com.ns.deneme.neo4j.repository.ViewRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class ViewAPI implements IViewAPI {
	
	@Autowired
	public ViewRepository viewRepository; 
	
    public void saveView(View view) {
    	viewRepository.save(view);        
    }
}
