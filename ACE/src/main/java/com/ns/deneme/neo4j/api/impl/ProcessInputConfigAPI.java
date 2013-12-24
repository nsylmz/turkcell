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

import com.ns.deneme.neo4j.api.IProcessInputConfigAPI;
import com.ns.deneme.neo4j.domain.ProcessInputConfig;
import com.ns.deneme.neo4j.repository.ProcessInputConfigRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class ProcessInputConfigAPI implements IProcessInputConfigAPI {
	
	@Autowired
	public ProcessInputConfigRepository processConfigRepository; 
	
    public void saveProcessInputConfig(ProcessInputConfig processComponent) {
    	processConfigRepository.save(processComponent);        
    }
    
    public List<ProcessInputConfig> findAll() {
    	List<ProcessInputConfig> processeComponents = new ArrayList<ProcessInputConfig>();
    	EndResult<ProcessInputConfig> resultSet = processConfigRepository.findAll();
    	Iterator<ProcessInputConfig> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		processeComponents.add((ProcessInputConfig) iter.next());
		}
    	return processeComponents;
    }

	@Override
	public ProcessInputConfig findProcessInputConfigByInputParamName(String inputParamName) {
		return processConfigRepository.findByPropertyValue("inputParamName", inputParamName.trim());
	}
	
	@Override
	public ProcessInputConfig findProcessInputConfigByMethodParamName(String methodParamName) {
		return processConfigRepository.findByPropertyValue("configMethodParamName", methodParamName.trim());
	}

}
