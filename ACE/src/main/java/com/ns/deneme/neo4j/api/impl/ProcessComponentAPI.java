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

import com.ns.deneme.neo4j.api.IProcessComponentAPI;
import com.ns.deneme.neo4j.domain.ProcessComponent;
import com.ns.deneme.neo4j.repository.ProcessComponentRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class ProcessComponentAPI implements IProcessComponentAPI {
	
	@Autowired
	public ProcessComponentRepository processComponentRepository; 
	
    public void saveProcessComponent(ProcessComponent processComponent) {
    	processComponentRepository.save(processComponent);        
    }
    
    public List<ProcessComponent> findAll() {
    	List<ProcessComponent> processeComponents = new ArrayList<ProcessComponent>();
    	EndResult<ProcessComponent> resultSet = processComponentRepository.findAll();
    	Iterator<ProcessComponent> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		processeComponents.add((ProcessComponent) iter.next());
		}
    	return processeComponents;
    }

	@Override
	public ProcessComponent findProcessComponentByName(String componentName) {
		return processComponentRepository.findByPropertyValue("processName", componentName.trim());
	}

}
