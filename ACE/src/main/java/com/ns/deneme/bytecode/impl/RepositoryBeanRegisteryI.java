package com.ns.deneme.bytecode.impl;

import org.springframework.data.neo4j.support.mapping.Neo4jPersistentEntityImpl;

public interface RepositoryBeanRegisteryI {

	public Neo4jPersistentEntityImpl<?> registerNode(String nodeClassName) throws ClassNotFoundException;
	
	public void registerRepository(String repositoryInterface);
}
