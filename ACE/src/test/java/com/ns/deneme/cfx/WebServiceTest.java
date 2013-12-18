package com.ns.deneme.cfx;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Definition;
import javax.wsdl.Operation;
import javax.wsdl.Part;
import javax.wsdl.PortType;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.wsdl11.WSDLManagerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-config.xml")
public class WebServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		Object[] res = null;
		try {
			WSDLManagerImpl impl = new WSDLManagerImpl();
			Definition defs = impl.getDefinition("http://www.webservicex.net/globalweather.asmx?WSDL");
			Map<QName, PortType> portTypes = (Map<QName, PortType>) defs.getPortTypes();
			Set<QName> ptKeySet = portTypes.keySet();
			Iterator<QName> ptIter = ptKeySet.iterator();
			QName ptKey = null;
			String partKey = null;
			Set<String> partKeySet = null;
			Iterator<String> partIter = null;
			PortType pt;
			Map<String, Part> parts = null;
			Part part = null;
			String partType = null;
			while (ptIter.hasNext()) {
				ptKey = (QName) ptIter.next();
				pt = portTypes.get(ptKey);
				
				System.out.println("PortType" + pt.getQName());
				for (Operation op : (List<Operation>)pt.getOperations()) {
					System.out.println("----------------Operation Name : " + op.getName());
					System.out.println("----------------Operation Type : " + op.getStyle().toString());
					if (op.getParameterOrdering() != null) {
						System.out.println("------Parameter Order------");
						for (String partName : (List<String>) op.getParameterOrdering()) {
							System.out.println("Part Name : " + partName);
						}
					}
					System.out.println("------Request Parameters------");
					parts = op.getInput().getMessage().getParts();
					partKeySet = parts.keySet();
					partIter = partKeySet.iterator();
					while (partIter.hasNext()) {
						partKey = (String) partIter.next();
						part = parts.get(partKey);
						System.out.println("Part Name : " + part.getName());
						partType = "";
						if (part.getTypeName() != null) {
							partType = part.getTypeName().toString().replace("{http://www.w3.org/2001/XMLSchema}", "");
						}
						System.out.println("Part Type : " + partType);
					}
					
					System.out.println("------Response Parameters------");
					parts = op.getOutput().getMessage().getParts();
					partKeySet = parts.keySet();
					partIter = partKeySet.iterator();
					while (partIter.hasNext()) {
						partKey = (String) partIter.next();
						part = parts.get(partKey);
						System.out.println("Part Name : " + part.getName());
						partType = "";
						if (part.getTypeName() != null) {
							partType = part.getTypeName().toString().replace("{http://www.w3.org/2001/XMLSchema}", "");
						}
						System.out.println("Part Type : " + partType);
					}
					
					/*
					Element el = (Element) ((Part) op.getInput().getMessage().getParts().get(0)).getDocumentationElement();

					System.out.println(el.);

					System.out.println("Response Parameters");
					*/
				}
			}
			/*
			Object request = Thread
					.currentThread()
					.getContextClassLoader()
					.loadClass(
							"com.turkcell.crm.sfa.service.endpoints.subvention.ApprovalItemByEntityCountRequest")
					.newInstance();
			res = client.invoke("getApprovalCountByType", request);
			*/
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Client client = dcf.createClient("http://www.webservicex.net/globalweather.asmx?WSDL", loader);
			res = client.invoke("GetWeather", "Turkey", "Istanbul / Ataturk");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Echo response: " + res[0]);
	}
/*
	private static void listParameters(Element element) {

		ComplexType ct = (ComplexType) element.getEmbeddedType();

		for (Element e : ct.getSequence().getElements()) {

			System.out.println(e.getName() + " " + e.getType());

		}

	}
*/
}
