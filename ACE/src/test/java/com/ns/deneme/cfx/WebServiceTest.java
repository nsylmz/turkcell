package com.ns.deneme.cfx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.wsdl.Definition;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.wsdl11.WSDLManagerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-config.xml")
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
@TransactionConfiguration(defaultRollback=false)
public class WebServiceTest {
	
	@Test
	public void test() {
		Object[] res = null;
		try {
			WSDLManagerImpl impl = new WSDLManagerImpl();
			Definition definition = impl.getDefinition("http://extprpws.turkcell.com.tr/tsfaws/SubventionService?wsdl");
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Client client = dcf.createClient("http://extprpws.turkcell.com.tr/tsfaws/SubventionService?wsdl", loader);
			Object request = Thread.currentThread().getContextClassLoader().loadClass("com.turkcell.crm.sfa.service.endpoints.subvention.ApprovalItemByEntityCountRequest").newInstance();
			res = client.invoke("getApprovalCountByType", request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Echo response: " + res[0]);
	}

}
