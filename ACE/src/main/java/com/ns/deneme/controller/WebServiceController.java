package com.ns.deneme.controller;

import java.util.HashMap;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.deneme.vo.WsRunParams;
import com.ns.deneme.ws.WebServiceManagerI;
import com.ns.deneme.ws.WsdlOperations;

@Controller
@RequestMapping(value = "/ws", method = RequestMethod.POST)
public class WebServiceController {
	
	private static Logger logger = LoggerFactory.getLogger(WebServiceController.class);
	
	@Autowired
	private WebServiceManagerI webServiceManager;
	
	@RequestMapping(value = "/runWS")
	public @ResponseBody Map<String, String> runWebService(@ModelAttribute(value="wsRunParams") WsRunParams wsRunParams, Model model) {
		Map<String, String> data = new HashMap<String, String>();
	   	logger.debug("Running Web Service...");
	   	try {
	   		webServiceManager.callWsOperation(wsRunParams.getWsdlUrl(), wsRunParams.getOperation(), wsRunParams.getOperationSchemaName(), 
	   				wsRunParams.getOperationRequestType(), wsRunParams.getOperationResponseType(), wsRunParams.getParamNameAndparamValue());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("Running Web Service is Ended");
		}
	 	return data;
	}
	 
	@RequestMapping(value = "/readWsdl")
	public @ResponseBody Map<String, String> readWsdl(@RequestParam(value="wsdlUrl") String wsdlUrl, Model model) {
		Map<String, String> data = new HashMap<String, String>();
		try {
			WsdlOperations wsdlOperations = webServiceManager.readWsdlAndGetAllOperations(wsdlUrl);
			data.put("status", "1");
			data.put("message", "Read WSDL success.");
			ObjectMapper mapper = new ObjectMapper();
			data.put("oprations", mapper.writeValueAsString(wsdlOperations.getOperationNames()));
			
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Read WSDL Failed!!!");
		}
		return data;
	}
	 
	@RequestMapping(value = "/getOpDetail")
	public @ResponseBody Map<String, String> getOperationDetail(String wsdlUrl, String operationName, Model model) {
		Map<String, String> data = new HashMap<String, String>();
		Definition definition = webServiceManager.getWsdlDefinition(wsdlUrl);
		Operation operation = webServiceManager.getOperation(operationName, definition);
		Map<String, String> inputParamAndTypes = webServiceManager.getOperationInputParams(operation, definition);
		Map<String, String> outputParamAndTypes = webServiceManager.getOperationOutputParams(operation, definition);
		return data;
	}

}
