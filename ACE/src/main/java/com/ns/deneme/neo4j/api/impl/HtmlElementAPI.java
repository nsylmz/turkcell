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

import com.ns.deneme.neo4j.api.IHtmlElementAPI;
import com.ns.deneme.neo4j.domain.HtmlElement;
import com.ns.deneme.neo4j.repository.HtmlElementRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class HtmlElementAPI implements IHtmlElementAPI {
	
	@Autowired
	public HtmlElementRepository htmlElementRepository;
	
	@Override
    public void saveHtmlElement(HtmlElement htmlElement) {
    	htmlElementRepository.save(htmlElement);        
    }
    
    @Override
    public HtmlElement findHtmlElementByLabel(String elementLabel) {
    	return htmlElementRepository.findByPropertyValue("elementLabel", elementLabel.trim());
    }
    
    public void temp() {
    	
    }

	@Override
	public List<HtmlElement> findAll() {
		List<HtmlElement> htmlElements = new ArrayList<HtmlElement>();
    	EndResult<HtmlElement> resultSet = htmlElementRepository.findAll();
    	Iterator<HtmlElement> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		htmlElements.add((HtmlElement) iter.next());
		}
    	return htmlElements;
	}

}
