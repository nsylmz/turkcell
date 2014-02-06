package com.ns.deneme.bytecode.impl;

import javassist.CtClass;

public interface RepositoryBeanFactoryI {

	public void createRepository(String repositoryInterface, String node);
	
	public void createNode(String node);
	
	public void createRepositoryAPI(String repositoryAPI);
	
	public void cloneRepositoryAPI(String repositoryAPI, CtClass nodeClass, CtClass repositoryClass);
	
	public CtClass cloneRepositoryApiInterface(String repositoryApiInterface, CtClass nodeClass);
	
	public CtClass cloneRepository(String repositoryInterface, String node);
	
	public CtClass cloneNode(String node);

}
