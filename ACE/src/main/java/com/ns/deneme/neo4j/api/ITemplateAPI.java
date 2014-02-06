package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.TemplateNodeEntity;

public interface ITemplateAPI {
	
    public void save(TemplateNodeEntity templateNodeEntity);
    
    public void delete(TemplateNodeEntity templateNodeEntity);
    
    public void deleteById(Long id);
    
    public TemplateNodeEntity findByPropertyValue(String indexedPropertyName, String propertyValue);
    
    public List<TemplateNodeEntity> findAll();
    
    public TemplateNodeEntity findOne(Long id);
    
}
