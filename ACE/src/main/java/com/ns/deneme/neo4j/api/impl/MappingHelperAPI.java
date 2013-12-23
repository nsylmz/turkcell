package com.ns.deneme.neo4j.api.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ns.deneme.neo4j.api.IMappingHelperAPI;
import com.ns.deneme.neo4j.domain.MappingHelper;
import com.ns.deneme.neo4j.repository.MappingHelperRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class MappingHelperAPI implements IMappingHelperAPI {
	
	@Autowired
	public MappingHelperRepository mappingHelperRepository; 
	
    public void saveMappingHelper(MappingHelper mappingHelper) {
    	mappingHelperRepository.save(mappingHelper);        
    }

	@Override
	public Set<MappingHelper> findAll() {
		Set<MappingHelper> helpers = new HashSet<MappingHelper>();
		EndResult<MappingHelper> result = mappingHelperRepository.findAll();
		Iterator<MappingHelper> iter = result.iterator();
		while (iter.hasNext()) {
			helpers.add((MappingHelper) iter.next());
		}
		return helpers;
	}
}
