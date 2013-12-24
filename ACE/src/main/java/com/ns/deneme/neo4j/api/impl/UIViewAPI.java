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

import com.ns.deneme.neo4j.api.IUIVewAPI;
import com.ns.deneme.neo4j.domain.UIView;
import com.ns.deneme.neo4j.repository.UIViewRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class UIViewAPI implements IUIVewAPI {
	
	@Autowired
	public UIViewRepository uiViewRepository;
	
    public void saveUIView(UIView processView) {
    	uiViewRepository.save(processView);        
    }
    
    public UIView findUIViewByName(String viewName) {
    	return uiViewRepository.findByPropertyValue("viewName", viewName.trim());
    }

	@Override
	public List<UIView> findAll() {
		List<UIView> uiViews = new ArrayList<UIView>();
    	EndResult<UIView> resultSet = uiViewRepository.findAll();
    	Iterator<UIView> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		uiViews.add((UIView) iter.next());
		}
    	return uiViews;
	}
}
