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

import com.ns.deneme.neo4j.api.IProcessConfigAPI;
import com.ns.deneme.neo4j.domain.ProcessConfig;
import com.ns.deneme.neo4j.repository.ProcessConfigRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class ProcessConfigAPI implements IProcessConfigAPI {
	
	@Autowired
	public ProcessConfigRepository processConfigRepository; 
	
    public void saveProcessConfig(ProcessConfig processComponent) {
    	processConfigRepository.save(processComponent);        
    }
    
    public List<ProcessConfig> findAll() {
    	List<ProcessConfig> processeComponents = new ArrayList<ProcessConfig>();
    	EndResult<ProcessConfig> resultSet = processConfigRepository.findAll();
    	Iterator<ProcessConfig> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		processeComponents.add((ProcessConfig) iter.next());
		}
    	return processeComponents;
    }

	@Override
	public ProcessConfig findProcessConfigByName(String configName) {
		return processConfigRepository.findByPropertyValue("configName", configName.trim());
	}

}
