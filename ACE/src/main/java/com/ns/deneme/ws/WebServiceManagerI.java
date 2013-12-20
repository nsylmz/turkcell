package com.ns.deneme.ws;

import java.util.List;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.Operation;

public interface WebServiceManagerI {
	
	public Object[] callWsOperation(String wsdlUrl, String operation, String operationSchemaName, String operationRequestType, 
			String operationResponseType, Map<String, WSRequestParameter> paramNameAndparamValue);
	
	public WsdlOperations readWsdlAndGetAllOperations(String wsdlUrl);
	
	public Operation getOperation(String operationName, Definition definition);
	
	public Map<String, String> getOperationInputParams(Operation op, Definition definition);
	
	public Map<String, String> getOperationOutputParams(Operation op, Definition definition);
	
	public List<String> getAllOperationNames(Definition definition);
	
	public String getOperationInputName(Operation op, Definition definition);
	
	public String getOperationOutputName(Operation op, Definition definition);

}
