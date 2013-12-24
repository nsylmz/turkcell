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

import com.ns.deneme.neo4j.api.IUIComponentAPI;
import com.ns.deneme.neo4j.domain.UIComponent;
import com.ns.deneme.neo4j.repository.UIComponentRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class UIComponentAPI implements IUIComponentAPI {
	
	@Autowired
	public UIComponentRepository uiComponentRepository; 
	
    public void saveUIComponent(UIComponent processComponent) {
    	uiComponentRepository.save(processComponent);        
    }
    
    public List<UIComponent> findAll() {
    	List<UIComponent> processeComponents = new ArrayList<UIComponent>();
    	EndResult<UIComponent> resultSet = uiComponentRepository.findAll();
    	Iterator<UIComponent> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		processeComponents.add((UIComponent) iter.next());
		}
    	return processeComponents;
    }

	@Override
	public UIComponent findUIComponentByName(String componentName) {
		return uiComponentRepository.findByPropertyValue("componentName", componentName.trim());
	}

}
