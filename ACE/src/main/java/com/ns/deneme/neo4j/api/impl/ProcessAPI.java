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

import com.ns.deneme.neo4j.api.IProcessAPI;
import com.ns.deneme.neo4j.domain.Process;
import com.ns.deneme.neo4j.repository.ProcessRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class ProcessAPI implements IProcessAPI {
	
	@Autowired
	public ProcessRepository processRepository; 
	
    public void saveProcess(Process process) {
    	processRepository.save(process);        
    }
    
    public List<Process> findAll() {
    	List<Process> processes = new ArrayList<Process>();
    	EndResult<Process> resultSet = processRepository.findAll();
    	Iterator<Process> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		processes.add((Process) iter.next());
		}
    	return processes;
    }

}
