package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.Process;

public interface IProcessAPI {
	
    public void saveProcess(Process process);
    
    public List<Process> findAll();
}
