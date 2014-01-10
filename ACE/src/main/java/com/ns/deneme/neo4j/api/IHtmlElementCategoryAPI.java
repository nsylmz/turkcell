package com.ns.deneme.neo4j.api;

import java.util.List;
import java.util.Map;

import com.ns.deneme.neo4j.domain.HtmlElement;
import com.ns.deneme.neo4j.domain.HtmlElementCategory;

public interface IHtmlElementCategoryAPI {
	
    public void saveHtmlElementCategory(HtmlElementCategory htmlElementCategory);
    
    public List<HtmlElementCategory> findAll();

	public HtmlElementCategory findHtmlElementCategoryByName(String categoryName);

	public Map<String, List<HtmlElement>> mapHtmlElementsByCategory();
    
}
