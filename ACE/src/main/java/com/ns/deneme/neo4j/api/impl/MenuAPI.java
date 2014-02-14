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

import com.ns.deneme.neo4j.api.IMenuAPI;
import com.ns.deneme.neo4j.domain.Menu;
import com.ns.deneme.neo4j.repository.MenuRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class MenuAPI implements IMenuAPI {
	
	@Autowired
	public MenuRepository repository;
	
	public void save(Menu menu) {
		repository.save(menu);
	}

	public void delete(Menu menu) {
		repository.delete(repository.findOne(menu.getId()));
	}

	public void deleteById(Long id) {
		repository.delete(repository.findOne(id));
	}

	public Menu findByPropertyValue(String indexedPropertyName, String propertyValue) {
		return repository.findByPropertyValue(indexedPropertyName.trim(), propertyValue.trim());
	}

	public Menu findOne(Long id) {
		return repository.findOne(id);
	}
	
	public List<Menu> findAll() {
		List<Menu> resultList = new ArrayList<Menu>();
    	EndResult<Menu> resultSet = repository.findAll();
    	Iterator<Menu> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		resultList.add((Menu) iter.next());
		}
    	return resultList;
	}

	@Override
	public List<String[]> mapMenusToJSON(List<Menu> menus) {
		List<String[]> jsonList = new ArrayList<>();
		String[] jsonMenu;
		for (Menu menu : menus) {
			jsonMenu = new String[2];
			jsonMenu[0] = menu.getId().toString();
			jsonMenu[1] = menu.getMenuName();
			jsonList.add(jsonMenu);
		}
		return jsonList;
	}
}
