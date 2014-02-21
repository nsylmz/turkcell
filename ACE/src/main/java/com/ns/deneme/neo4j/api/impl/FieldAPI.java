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

import com.ns.deneme.neo4j.api.IFieldAPI;
import com.ns.deneme.neo4j.domain.Field;
import com.ns.deneme.neo4j.repository.FieldRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class FieldAPI implements IFieldAPI {
	
	@Autowired
	public FieldRepository repository;
	
	public void save(Field field) {
		repository.save(field);
	}

	public void delete(Field field) {
		repository.delete(repository.findOne(field.getId()));
	}

	public void deleteById(Long id) {
		repository.delete(repository.findOne(id));
	}

	public Field findByPropertyValue(String indexedPropertyName, String propertyValue) {
		return repository.findByPropertyValue(indexedPropertyName.trim(), propertyValue.trim());
	}

	public Field findOne(Long id) {
		return repository.findOne(id);
	}
	
	public List<Field> findAll() {
		List<Field> resultList = new ArrayList<Field>();
    	EndResult<Field> resultSet = repository.findAll();
    	Iterator<Field> iter = resultSet.iterator();
    	while (iter.hasNext()) {
    		resultList.add((Field) iter.next());
		}
    	return resultList;
	}

	public List<String[]> mapFieldsToJSON(List<Field> fields) {
		List<String[]> jsonList = new ArrayList<>();
		String[] jsonField;
		for (Field field : fields) {
			jsonField = new String[6];
			jsonField[0] = field.getId().toString();
			jsonField[1] = field.getFieldName();
			jsonField[2] = field.getType();
			jsonField[3] = field.isIndexed() + "";
			jsonField[4] = field.getEntity().getId().toString();
			jsonField[5] = field.getEntity().getEntityName();
			jsonList.add(jsonField);
		}
		return jsonList;
	}
}
