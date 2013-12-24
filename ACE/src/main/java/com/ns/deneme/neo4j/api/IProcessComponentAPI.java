package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.ProcessComponent;

public interface IProcessComponentAPI {
	
    public void saveProcessComponent(ProcessComponent processComponent);
    
    public ProcessComponent findProcessComponentByName(String componentName);
    
    public List<ProcessComponent> findAll();
    
}
