package com.ns.deneme.camel;

import org.apache.camel.builder.RouteBuilder;

public class ACECamelRoute extends RouteBuilder {
	
	private static final String SOAP_ENDPOINT_URI = "cxf://http://localhost:8080/ACE/soap";

    @Override
    public void configure() throws Exception {
    	errorHandler(noErrorHandler());
    	String wsdl = "";
    	// populate the message queue with some messages
        from(SOAP_ENDPOINT_URI).process(new SoapProcessor(wsdl));
    }
}
