package com.ns.deneme.bytecode;

import javassist.CtClass;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-config.xml")
public class RepositoryBeanFactoryTest {
	
	private static Logger logger = LoggerFactory.getLogger(RepositoryBeanFactoryTest.class);
	
	@Autowired
	private RepositoryBeanFactoryI repositoryBeanFactory;
	
	@Autowired
	private RepositoryBeanRegisteryI repositoryBeanRegistery;
	
	@Before
	public void before() {
//		repositoryBeanFactory.createNode("com.ns.deneme.neo4j.domain.Address");
//		repositoryBeanFactory.createRepository("com.ns.deneme.neo4j.repository.AddressRepository", "com.ns.deneme.neo4j.domain.Address");
//		repositoryBeanFactory.createRepositoryAPI("com.ns.deneme.neo4j.api.impl.AddressAPI");
		
		CtClass node = repositoryBeanFactory.cloneNode("com.ns.deneme.neo4j.domain.Address", null);
		CtClass repository = repositoryBeanFactory.cloneRepository("com.ns.deneme.neo4j.repository.AddressRepository", node.getName());
		repositoryBeanFactory.cloneRepositoryAPI("com.ns.deneme.neo4j.api.impl.AddressAPI", node, repository);
	}
	
	@Test
	public void test() throws ClassNotFoundException {
		repositoryBeanRegistery.registerNode("com.ns.deneme.neo4j.domain.Address");
		repositoryBeanRegistery.registerRepository("com.ns.deneme.neo4j.repository.AddressRepository");
		repositoryBeanRegistery.registerRepositoryAPI("com.ns.deneme.neo4j.api.impl.AddressAPI");
	}
	
}
