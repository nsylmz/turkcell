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

import com.ns.deneme.neo4j.api.IMenuItemAPI;
import com.ns.deneme.neo4j.domain.MenuItem;
import com.ns.deneme.neo4j.repository.MenuItemRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class MenuItemAPI implements IMenuItemAPI {
	
	@Autowired
	public MenuItemRepository repository;
	
	public void save(MenuItem menuItem) {
		repository.save(menuItem);
	}

	public void delete(MenuItem menuItem) {
		repository.delete(repository.findOne(menuItem.getId()));
	}

	public void deleteById(Long id) {
		repository.delete(repository.findOne(id));
	}

	public MenuItem findByPropertyValue(String indexedPropertyName, String propertyValue) {
		return repository.findByPropertyValue(indexedPropertyName.trim(), propertyValue.trim());
	}

	public MenuItem findOne(Long id) {
		return repository.findOne(id);
	}
	
	public List<MenuItem> findAll() {
		List<MenuItem> resultList = new ArrayList<MenuItem>();
    	EndResult<MenuItem> resultSet = repository.findAll();
    	Iterator<MenuItem> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		resultList.add((MenuItem) iter.next());
		}
    	return resultList;
	}

	public List<String[]> mapMenuItemsToJSON(List<MenuItem> menuItems) {
		List<String[]> jsonList = new ArrayList<>();
		String[] jsonMenuItem;
		for (MenuItem menuItem : menuItems) {
			jsonMenuItem = new String[6];
			jsonMenuItem[0] = menuItem.getId().toString();
			jsonMenuItem[1] = menuItem.getMenuItemName();
			jsonMenuItem[2] = menuItem.getMenu().getId().toString();
			jsonMenuItem[3] = menuItem.getMenu().getMenuName();
			if (menuItem.getMenuItemPage() != null) {
				jsonMenuItem[4] = menuItem.getMenuItemPage().getId().toString();
				jsonMenuItem[5] = menuItem.getMenuItemPage().getPageName();
			} else {
				jsonMenuItem[4] = "";
				jsonMenuItem[5] = "No Page Is Set";
			}
			jsonList.add(jsonMenuItem);
		}
		return jsonList;
	}
}
