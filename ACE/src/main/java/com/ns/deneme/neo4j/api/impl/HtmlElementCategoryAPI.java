package com.ns.deneme.neo4j.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ns.deneme.neo4j.api.IHtmlElementCategoryAPI;
import com.ns.deneme.neo4j.domain.HtmlElement;
import com.ns.deneme.neo4j.domain.HtmlElementCategory;
import com.ns.deneme.neo4j.repository.HtmlElementCategoryRepository;
import com.ns.deneme.neo4j.repository.HtmlElementRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class HtmlElementCategoryAPI implements IHtmlElementCategoryAPI {
	
	@Autowired
	public HtmlElementCategoryRepository htmlElementCategoryRepository;
	
	@Autowired
	public HtmlElementRepository htmlElementRepository;
	
	@Override
    public void saveHtmlElementCategory(HtmlElementCategory htmlElementCategory) {
    	htmlElementCategoryRepository.save(htmlElementCategory);        
    }
    
    @Override
    public HtmlElementCategory findHtmlElementCategoryByName(String categoryName) {
    	return htmlElementCategoryRepository.findByPropertyValue("categoryName", categoryName.trim());
    }

	@Override
	public List<HtmlElementCategory> findAll() {
		List<HtmlElementCategory> htmlElementCategorys = new ArrayList<HtmlElementCategory>();
    	EndResult<HtmlElementCategory> resultSet = htmlElementCategoryRepository.findAll();
    	Iterator<HtmlElementCategory> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		htmlElementCategorys.add((HtmlElementCategory) iter.next());
		}
    	return htmlElementCategorys;
	}
	
	@Override
	public Map<String, List<HtmlElement>> mapHtmlElementsByCategory() {
		Map<String, List<HtmlElement>> elementMap = new HashMap<String, List<HtmlElement>>();
		List<HtmlElementCategory> categories = findAll();
//		PageRequest page = new PageRequest(0, 20, Direction.DESC, "p.categoryName");
//		Page<HtmlElement> resultSet;
		Iterable<HtmlElement> resultSet;
		Iterator<HtmlElement> iter;
		HtmlElement htmlElement;
		List<HtmlElement> elements;
		for (HtmlElementCategory category : categories) {
			resultSet = htmlElementRepository.findHtmlElementByCategory(category.getCategoryName());
			elements = new ArrayList<>();
			iter = resultSet.iterator();
			while (iter.hasNext()) {
				htmlElement = (HtmlElement) iter.next();
				elements.add(htmlElement);
			}
			elementMap.put(category.getCategoryName(), elements);
		}
    	return elementMap;
	}

}
