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

import com.ns.deneme.neo4j.domain.MappingHelper;
import com.ns.deneme.neo4j.repository.MappingHelperRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-config.xml")
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
@TransactionConfiguration(defaultRollback=false)
public class MappingHelperTest {
	
	@Autowired
	private MappingHelperRepository mappingHelperRepository;
	
	@Test
	public void test() {
		MappingHelper helper = new MappingHelper();
		helper.setMapName("paramType");
		helper.setMapRule("WSRequestParameter.paramType");
		mappingHelperRepository.save(helper);
		
		helper = new MappingHelper();
		helper.setMapName("paramValue");
		helper.setMapRule("WSRequestParameter.paramValue");
		mappingHelperRepository.save(helper);
		
		helper = new MappingHelper();
		helper.setMapName("paramName");
		helper.setMapRule("Map.key");
		mappingHelperRepository.save(helper);
	}

}
