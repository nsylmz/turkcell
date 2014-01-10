package com.ns.deneme.neo4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ns.deneme.neo4j.api.IHtmlElementAPI;
import com.ns.deneme.neo4j.api.IHtmlElementCategoryAPI;
import com.ns.deneme.neo4j.domain.HtmlElement;
import com.ns.deneme.neo4j.domain.HtmlElementCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-config.xml")
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
@TransactionConfiguration(defaultRollback=false)
public class HtmElementAPITest {
	
	@Autowired
	private IHtmlElementAPI htmlElementAPI;
	
	@Autowired
	private IHtmlElementCategoryAPI htmlElementCategoryAPI;
	
	@Test
	public void test() {
		try {
			HtmlElementCategory category = new HtmlElementCategory();
			category.setCategoryName("UI-basics");
			htmlElementCategoryAPI.saveHtmlElementCategory(category);
			
			// Button
			HtmlElement element = new HtmlElement();
			element.setCategory(category);
			element.setElementLabel("Button");
			element.setIconName("button.png");
			element.setElementHtml("<button class='temp-component ace-button btn btn-primary ladda-button' element-name='button' data-style='expand-right'>"
				                	+ "<span class='ladda-label'>button</span>"
				                	+ "</button>");
	        htmlElementAPI.saveHtmlElement(element);
	        
	        // Check Box
	        element = new HtmlElement();
			element.setCategory(category);
			element.setElementLabel("Check Box");
			element.setIconName("checkbox.gif");
			element.setElementHtml("<input type='checkbox' name='' value=''>");
	        htmlElementAPI.saveHtmlElement(element);
	        
	        // Radio Button
	        element = new HtmlElement();
			element.setCategory(category);
			element.setElementLabel("Radio Button");
			element.setElementHtml("<input class='temp-component' type='radio' name='' value=''>");
	        element.setIconName("radio-button.jpg");
	        htmlElementAPI.saveHtmlElement(element);
	        
	        // Text Field
	        element = new HtmlElement();
			element.setCategory(category);
			element.setElementLabel("Text Field");
			element.setElementHtml("<input class='temp-component' type='text' name=''>");
	        element.setIconName("text-field.png");
	        htmlElementAPI.saveHtmlElement(element);
	        
	        // Text Area
	        element = new HtmlElement();
			element.setCategory(category);
			element.setElementLabel("Text Area");
			element.setElementHtml("<textarea class='temp-component' rows='4' cols='50'></textarea>");
	        element.setIconName("text-area.png");
	        htmlElementAPI.saveHtmlElement(element);
	        
			category = new HtmlElementCategory();
			category.setCategoryName("List Components");
			htmlElementCategoryAPI.saveHtmlElementCategory(category);
			
			// Text Area
	        element = new HtmlElement();
			element.setCategory(category);
			element.setElementLabel("Combo Box");
			element.setElementHtml("<select class='temp-component'>"
									  + "<option value='Value'>Volvo</option>"
								 + "</select>");
	        element.setIconName("combobox.gif");
	        htmlElementAPI.saveHtmlElement(element);
	        
			category = new HtmlElementCategory();
			category.setCategoryName("Forms");
			htmlElementCategoryAPI.saveHtmlElementCategory(category);
		
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

}
