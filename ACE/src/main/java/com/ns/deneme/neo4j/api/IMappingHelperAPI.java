package com.ns.deneme.neo4j.api;

import java.util.Set;

import com.ns.deneme.neo4j.domain.MappingHelper;

public interface IMappingHelperAPI {
	
    public void saveMappingHelper(MappingHelper mappingHelper);
    
    public Set<MappingHelper> findAll();
}
