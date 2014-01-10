package com.ns.deneme.neo4j.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.CypherDslRepository;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.ns.deneme.neo4j.domain.HtmlElement;

public interface HtmlElementRepository extends GraphRepository<HtmlElement>, CypherDslRepository<HtmlElement> {
	
	 @Query("start category=node:HtmlElementCategory(categoryName={0}) match category-[:parentCategory]-htmlElement return htmlElement")   
	 public Iterable<HtmlElement> findHtmlElementByCategory(String categoryName);   

}