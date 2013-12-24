package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.ProcessConfig;

public interface IProcessConfigAPI {
	
    public void saveProcessConfig(ProcessConfig processConfig);
    
    public ProcessConfig findProcessConfigByName(String configName);
    
    public List<ProcessConfig> findAll();
    
}
