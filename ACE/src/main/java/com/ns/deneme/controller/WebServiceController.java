package com.ns.deneme.controller;

import java.util.HashMap;
import java.util.List;
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
	public @ResponseBody Map<String, String> readWsdl(String wsdlUrl, Model model) {
		Map<String, String> data = new HashMap<String, String>();
		WsdlOperations wsdlOperations = webServiceManager.readWsdlAndGetAllOperations(wsdlUrl);
		return data;
	}
	 
	@RequestMapping(value = "/getOpDetail")
	public @ResponseBody Map<String, String> getOperationDetail(String operationName, Definition definition, Model model) {
		Map<String, String> data = new HashMap<String, String>();
		Operation operation = webServiceManager.getOperation(operationName, definition);
		Map<String, String> inputParamAndTypes = webServiceManager.getOperationInputParams(operation, definition);
		Map<String, String> outputParamAndTypes = webServiceManager.getOperationOutputParams(operation, definition);
		return data;
	}

}
