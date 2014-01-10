package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.HtmlElement;

public interface IHtmlElementAPI {
	
    public void saveHtmlElement(HtmlElement htmlElement);
    
    public HtmlElement findHtmlElementByLabel(String elementLabel);
    
    public List<HtmlElement> findAll();
    
}
