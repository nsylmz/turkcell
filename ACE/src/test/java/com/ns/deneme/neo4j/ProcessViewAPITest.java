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

import com.ns.deneme.neo4j.api.IProcessViewAPI;
import com.ns.deneme.neo4j.domain.ProcessView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-config.xml")
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
@TransactionConfiguration(defaultRollback=false)
public class ProcessViewAPITest {
	
	@Autowired
	private IProcessViewAPI processViewAPI;
	
	@Test
	public void test() {
		ProcessView processView = processViewAPI.findProcessViewByName("SubventionService");
		System.out.println(processView.getViewName());
		System.out.println(processView.getId());
	}

}
