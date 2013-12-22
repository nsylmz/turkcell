package com.ns.deneme.controller;

import java.util.HashMap;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ns.deneme.vo.WsRunParams;
import com.ns.deneme.ws.WebServiceManagerI;
import com.ns.deneme.ws.WsdlOperations;

@Controller
@RequestMapping(value = "/ws", method = RequestMethod.POST)
public class WebServiceController {
	
	private static Logger logger = LoggerFactory.getLogger(WebServiceController.class);
	
	@Autowired
	private WebServiceManagerI webServiceManager;
	
	@RequestMapping(value = "/runWS", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> runWebService(@RequestBody WsRunParams wsRunParams, Model model) {
		Map<String, Object> data = new HashMap<String, Object>();
	   	logger.debug("Running Web Service...");
	   	try {
	   		Definition definition = webServiceManager.getWsdlDefinition(wsRunParams.getWsdlUrl());
			Operation operation = webServiceManager.getOperation(wsRunParams.getOperation(), definition);
			Map<String, String> inputParamAndTypes = webServiceManager.getOperationInputParams(operation, definition);
			Map<String, String> outputParamAndTypes = webServiceManager.getOperationOutputParams(operation, definition);
	   		webServiceManager.callWsOperation(wsRunParams.getWsdlUrl(), wsRunParams.getOperation(), definition.getTargetNamespace(), 
	   				inputParamAndTypes.get(wsRunParams.getOperation()), outputParamAndTypes.get(wsRunParams.getOperation()), 
	   				wsRunParams.getParamNameAndparamValue());
	   		data.put("status", "1");
			data.put("message", "Web Service Run Successfully.");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Running Web Service Was Failed!!!");
		} finally {
			logger.debug("Running Web Service is Ended");
		}
	 	return data;
	}
	 
	@RequestMapping(value = "/readWsdl")
	public @ResponseBody Map<String, Object> readWsdl(@RequestParam(value="wsdlUrl") String wsdlUrl, Model model) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			WsdlOperations wsdlOperations = webServiceManager.readWsdlAndGetAllOperations(wsdlUrl);
			data.put("status", "1");
			data.put("message", "Read WSDL success.");
			data.put("operations", wsdlOperations.getOperationNames());
			
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Read WSDL Failed!!!");
		}
		return data;
	}
	 
	@RequestMapping(value = "/getOpDetail")
	public @ResponseBody Map<String, Object> getOperationDetail(@RequestParam(value="wsdlUrl") String wsdlUrl, 
			@RequestParam(value="operationName") String operationName, Model model) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Definition definition = webServiceManager.getWsdlDefinition(wsdlUrl);
			Operation operation = webServiceManager.getOperation(operationName, definition);
			Map<String, String> inputParamAndTypes = webServiceManager.getOperationInputParams(operation, definition);
			data.put("status", "1");
			data.put("message", "Operation Detail Is Successfully Retrieved.");
			data.put("inputParams", inputParamAndTypes);
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Get Operation Detail Was Failed!!!");
		}
		return data;
	}

}
