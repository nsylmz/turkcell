package com.ns.deneme.bytecode;

import org.springframework.data.neo4j.support.mapping.Neo4jPersistentEntityImpl;

public interface RepositoryBeanRegisteryI {

	public Neo4jPersistentEntityImpl<?> registerNode(String nodeClassName) throws ClassNotFoundException;
	
	public void registerRepository(String repositoryInterface);
	
	public void registerRepositoryAPI(String repositoryAPI);
	
}
