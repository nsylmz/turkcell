package com.ns.deneme.controller;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {

	protected static Logger logger = LoggerFactory.getLogger(MainController.class);
	
    @RequestMapping(value = "/UIGeneration", method = RequestMethod.GET)
    public String showUIGeneration(Model model) {
    	
    	logger.debug("Received request to show ui generation screen");
    	
    	return "UIGeneration";
	}
    
    @RequestMapping(value = "/APIGeneration", method = RequestMethod.GET)
    public String showAPIGeneration(Model model) {
    	
    	logger.debug("Received request to show api generation creen");

    	return "APIGeneration";
	}
    
    @RequestMapping(value = "/soap", method = RequestMethod.GET)
    public String runSoapService(Model model) {
    	logger.debug("Running Soap Service...");
    	try {
//    		DynamicClientFactory dcf = DynamicClientFactory.newInstance();
    		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//    		Client client = dcf.createClient("http://www.webservicex.net/CurrencyConvertor.asmx?WSDL");
    		Client client = dcf.createClient("C:\\hello_world.wsdl", this.getClass().getClassLoader());
    		Object[] res = client.invoke("CurrencyConvertor", "test echo");
    		System.out.println("Echo response: " + res[0]);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			logger.debug("Running Soap Service is Ended");
		}
    	return "";
	}
    
}
