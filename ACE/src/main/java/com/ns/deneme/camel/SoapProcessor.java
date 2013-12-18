package com.ns.deneme.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class SoapProcessor implements Processor {
	
	private String wsdl;
	
	public SoapProcessor(String wsdl) {
		this.wsdl = wsdl;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(wsdl);
		 
		Object[] res = client.invoke("echo", "test echo");
		System.out.println("Echo response: " + res[0]);
	}

}
