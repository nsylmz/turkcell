package com.ns.deneme.ws;

import java.util.List;

import javax.wsdl.Definition;

public class WsdlOperations {

	private Definition definition;
	
	private List<String> operationNames;

	public Definition getDefinition() {
		return definition;
	}

	public void setDefinition(Definition definition) {
		this.definition = definition;
	}

	public List<String> getOperationNames() {
		return operationNames;
	}

	public void setOperationNames(List<String> operationNames) {
		this.operationNames = operationNames;
	}
	
}
