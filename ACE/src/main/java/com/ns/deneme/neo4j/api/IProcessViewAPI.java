package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.ProcessView;

public interface IProcessViewAPI {
	
    public void saveProcessView(ProcessView processView);
    
    public ProcessView findProcessViewByName(String viewName);
    
    public List<ProcessView> findAll();
    
}
