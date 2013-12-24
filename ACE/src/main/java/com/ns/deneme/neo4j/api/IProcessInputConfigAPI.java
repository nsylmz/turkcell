package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.ProcessInputConfig;

public interface IProcessInputConfigAPI {
	
    public void saveProcessInputConfig(ProcessInputConfig uiView);
    
    public ProcessInputConfig findProcessInputConfigByInputParamName(String inputParamName);
    
    public ProcessInputConfig findProcessInputConfigByMethodParamName(String methodParamName);
    
    public List<ProcessInputConfig> findAll();
    
}
