package com.ns.deneme.cfx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Operation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ns.deneme.ws.WSRequestParameter;
import com.ns.deneme.ws.WebServiceManagerI;
import com.ns.deneme.ws.WsdlOperations;
import com.ns.deneme.ws.impl.WebServiceManagerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-config.xml")
public class WebServiceTest {
	
	private static Logger logger = LoggerFactory.getLogger(WebServiceManagerImpl.class);
	
	@Autowired
	private WebServiceManagerI webServiceManager;
	
	@Test
	public void testWsCall() {
		try {
			WsdlOperations wsdlOperations = webServiceManager.generateClientAndGetAllOperations("http://extprpws.turkcell.com.tr/tsfaws/SubventionService?wsdl");
			Operation operation = webServiceManager.getOperation(wsdlOperations.getOperationNames().get(0), wsdlOperations.getDefinition());
			Map<String, String> inputParamAndTypes = webServiceManager.getOperationInputParams(operation, wsdlOperations.getDefinition());
			Map<String, String> outputParamAndTypes = webServiceManager.getOperationOutputParams(operation, wsdlOperations.getDefinition());
			Map<String, WSRequestParameter> paramNameAndparamValue = new HashMap<String, WSRequestParameter>();
			Set<String> keySet = inputParamAndTypes.keySet();
			Iterator<String> iter = keySet.iterator();
			WSRequestParameter parameter = null;
			String param = null;
			String type = null;
			while (iter.hasNext()) {
				param = (String) iter.next();
				type = inputParamAndTypes.get(param);
				parameter = new WSRequestParameter();
				parameter.setParamType(type);
				parameter.setParamValue("0");
				paramNameAndparamValue.put(param, parameter);
			}
			webServiceManager.callWsOperation(wsdlOperations.getDefinition().getDocumentBaseURI(), operation.getName(), 
					wsdlOperations.getDefinition().getTargetNamespace(), 
					inputParamAndTypes.get(webServiceManager.getOperationInputName(operation, wsdlOperations.getDefinition())), 
					outputParamAndTypes.get(webServiceManager.getOperationOutputName(operation, wsdlOperations.getDefinition())), 
					paramNameAndparamValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/*
	public void test() {
		Object[] res = null;
		try {
			
			Bus bus = CXFBusFactory.getThreadDefaultBus();
			WSDLServiceFactory wsdl = new WSDLServiceFactory(bus, "http://extprpws.turkcell.com.tr/tsfaws/SubventionService?wsdl");
			Definition defs = wsdl.getDefinition();
			
			List<String> ops = webServiceManager.getAllOperationNames(defs);
			Operation operation = webServiceManager.getOperation(ops.get(0), defs);
			Map<String, String> paramAndTypes = webServiceManager.getOperationInputParams(operation, defs);
			Set<String> keys = paramAndTypes.keySet();
			Iterator<String> paramIter = keys.iterator();
			String paramName = null;
			while (paramIter.hasNext()) {
				paramName = (String) paramIter.next();
				System.out.println(paramName + " type : " + paramAndTypes.get(paramName));
			}
			
			Types types = defs.getTypes();
			Schema schema = null;
			for (Object e : types.getExtensibilityElements()) {
			    if (e instanceof Schema) {
			        schema = (Schema)e;
			        break;
			    }
			}
			XSSchemaSet sset = null;
			Iterator<XSSchema> xsSchemaIter = null;
			XSSchema xsSchema = null;
			Iterator<XSElementDecl> xselementDeclIter = null;
			XSElementDecl xsElementDecl = null;
			XSComplexType xsComplexType = null;
			if (schema != null) {
			    Element schemaElement = schema.getElement();
			    Map<String, Vector<SchemaImportImpl>> imports = schema.getImports();
			    Set<String> keySet = imports.keySet();
			    Iterator<String> iter = keySet.iterator();
			    String key;
			    while (iter.hasNext()) {
			    	key = (String) iter.next();
			    	System.out.println("Import Schema Location " + imports.get(key).firstElement().getSchemaLocationURI());
			    	XSOMParser parser = new XSOMParser();
			    	parser.parse(new URL(imports.get(key).firstElement().getSchemaLocationURI()));
			    	sset = parser.getResult();
			    	xsSchemaIter = sset.iterateSchema();
					while(xsSchemaIter.hasNext()) {
						xsSchema = (XSSchema) xsSchemaIter.next();
						xselementDeclIter = xsSchema.iterateElementDecls();
						while (xselementDeclIter.hasNext()) {
							xsElementDecl = (XSElementDecl) xselementDeclIter.next();
							printElement(xsSchema, xsElementDecl);
						}
					}
				}
			    Document document = schemaElement.getOwnerDocument();
			    DOMImplementationLS domImplLS = (DOMImplementationLS) document
			        .getImplementation();
			    LSSerializer serializer = domImplLS.createLSSerializer();
			    String str = serializer.writeToString(schemaElement);
			    System.out.println(str);
			}
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
						partType = part.getElementName().getLocalPart();
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
						partType = part.getElementName().getLocalPart();
						System.out.println("Part Type : " + partType);
					}
				}
			}
			Object request = Thread
					.currentThread()
					.getContextClassLoader()
					.loadClass(
							"com.turkcell.crm.sfa.service.endpoints.subvention.ApprovalItemByEntityCountRequest")
					.newInstance();
			res = client.invoke("getApprovalCountByType", request);
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Client client = dcf.createClient("C:\\SubventionService.wsdl", loader);
			client.getEndpoint().getService();
//			res = client.invoke("GetWeather", "Turkey", "Istanbul / Ataturk");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Echo response: " + res[0]);
	}
	*/
}
