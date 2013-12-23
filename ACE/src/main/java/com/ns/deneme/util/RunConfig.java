package com.ns.deneme.util;

import java.util.HashMap;
import java.util.Map;

public class RunConfig {

	public static final Map<String, String> webServiceConfig = new HashMap<String, String>();
	
	public static final String WS_RUN_CLASS = "webServiceManagerI";
	
	public static final String WS_RUN_METHOD = "callWsOperation";
	
	private static final String WS_RUN_WSDL_URL_KEY = "wsdlUrl";
	
	private static final String WS_RUN_WSDL_URL_VALUE = "wsdlUrl";
	
	private static final String WS_RUN_OPERATION_KEY = "operation";
	
	private static final String WS_RUN_OPERATION_VALUE = "operation";
	
	private static final String WS_RUN_REQUEST_KEY = "operationRequestType";
	
	private static final String WS_RUN_REQUEST_VALUE = "operationRequestType";
	
	private static final String WS_RUN_RESPONSE_KEY = "operationResponseType";
	
	private static final String WS_RUN_RESPONSE_VALUE = "operationResponseType";
	
	private static final String WS_RUN_PARAMS_KEY = "params";
	
	private static final String WS_RUN_PARAMS_VALUE = "paramNameAndparamValue";
	
	static {
		webServiceConfig.put(WS_RUN_WSDL_URL_KEY, WS_RUN_WSDL_URL_VALUE);
		webServiceConfig.put(WS_RUN_OPERATION_KEY, WS_RUN_OPERATION_VALUE);
		webServiceConfig.put(WS_RUN_REQUEST_KEY, WS_RUN_REQUEST_VALUE);
		webServiceConfig.put(WS_RUN_RESPONSE_KEY, WS_RUN_RESPONSE_VALUE);
		webServiceConfig.put(WS_RUN_PARAMS_KEY, WS_RUN_PARAMS_VALUE);
	}
}
