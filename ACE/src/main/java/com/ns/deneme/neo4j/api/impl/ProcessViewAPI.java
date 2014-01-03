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

import com.ns.deneme.neo4j.api.IProcessViewAPI;
import com.ns.deneme.neo4j.domain.ProcessView;
import com.ns.deneme.neo4j.repository.ProcessViewRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class ProcessViewAPI implements IProcessViewAPI {
	
	@Autowired
	public ProcessViewRepository processViewRepository;
	
    public void saveProcessView(ProcessView processView) {
    	processViewRepository.save(processView);        
    }
    
    public void deleteProcessView(String viewName) {
    	processViewRepository.delete(processViewRepository.findByPropertyValue("viewName", viewName.trim()));
    }
    
    public ProcessView findProcessViewByName(String viewName) {
    	return processViewRepository.findByPropertyValue("viewName", viewName.trim());
    }

	@Override
	public List<ProcessView> findAll() {
		List<ProcessView> processeViews = new ArrayList<ProcessView>();
    	EndResult<ProcessView> resultSet = processViewRepository.findAll();
    	Iterator<ProcessView> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		processeViews.add((ProcessView) iter.next());
		}
    	return processeViews;
	}
}
