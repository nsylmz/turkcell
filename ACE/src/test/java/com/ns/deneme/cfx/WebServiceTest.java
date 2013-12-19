package com.ns.deneme.cfx;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.wsdl.Definition;
import javax.wsdl.Operation;
import javax.wsdl.Part;
import javax.wsdl.PortType;
import javax.wsdl.Types;
import javax.wsdl.extensions.schema.Schema;
import javax.xml.namespace.QName;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.wsdl11.WSDLServiceFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import com.ibm.wsdl.extensions.schema.SchemaImportImpl;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.XSTerm;
import com.sun.xml.xsom.parser.XSOMParser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-config.xml")
public class WebServiceTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		Object[] res = null;
		try {
			
			Bus bus = CXFBusFactory.getThreadDefaultBus();
			WSDLServiceFactory wsdl = new WSDLServiceFactory(bus, "C:\\SubventionService.wsdl");
			Definition defs = wsdl.getDefinition();
			
			List<String> ops = getAllOperationNames(defs);
			Operation operation = getOperation(ops.get(0), defs);
			Map<String, String> paramAndTypes = getOperationInputParams(operation, defs);
			Set<String> keys = paramAndTypes.keySet();
			Iterator<String> paramIter = keys.iterator();
			String paramName = null;
			while (paramIter.hasNext()) {
				paramName = (String) paramIter.next();
				System.out.println(paramName + " type : " + paramAndTypes.get(paramName));
			}
			
			
//			WSDLManagerImpl wsdl = new WSDLManagerImpl();
			/*
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
//			    	generate(imports.get(key).firstElement().getSchemaLocationURI());
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
			 */
//			XSSchema s = (XSSchema)xsSchemaIter.next();
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
			Client client = dcf.createClient("C:\\SubventionService.wsdl", loader);
			client.getEndpoint().getService();
//			res = client.invoke("GetWeather", "Turkey", "Istanbul / Ataturk");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Echo response: " + res[0]);
	}
	
	@SuppressWarnings("unchecked")
	private Operation getOperation(String operationName, Definition definition) {
		Map<QName, PortType> portTypes = (Map<QName, PortType>) definition.getPortTypes();
		Set<QName> ptKeySet = portTypes.keySet();
		Iterator<QName> ptIter = ptKeySet.iterator();
		QName ptKey = null;
		PortType pt = null;
		while (ptIter.hasNext()) {
			ptKey = (QName) ptIter.next();
			pt = portTypes.get(ptKey);
			for (Operation op : (List<Operation>)pt.getOperations()) {
				if (op.getName().trim().equals(operationName)) {
					return op;
				}
			}
		}
		return null;
	}
	
	private Map<String, String> getOperationInputParams(Operation op, Definition definition) {
		Map<String, String> paramsAndTypes = new HashMap<String, String>();
		try {
			String partKey = null;
			Set<String> partKeySet = null;
			Iterator<String> partIter = null;
			Part part = null;
			String partType = null;
			Map<String, Part> parts = (Map<String, Part>) op.getInput().getMessage().getParts();
			partKeySet = parts.keySet();
			partIter = partKeySet.iterator();
			while (partIter.hasNext()) {
				partKey = (String) partIter.next();
				part = parts.get(partKey);
				partType = part.getElementName().getLocalPart();
			}
			Types types = definition.getTypes();
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
			XSElementDecl xsElementDecl = null;
			if (schema != null) {
				Map<String, Vector<SchemaImportImpl>> imports = (Map<String, Vector<SchemaImportImpl>>) schema.getImports();
			    Set<String> keySet = imports.keySet();
			    Iterator<String> iter = keySet.iterator();
			    String key;
			    while (iter.hasNext()) {
			    	key = (String) iter.next();
			    	System.out.println("Import Schema Location " + imports.get(key).firstElement().getSchemaLocationURI());
			    	XSOMParser parser = new XSOMParser();
			    	parser.parse(new File(imports.get(key).firstElement().getSchemaLocationURI()));
			    	sset = parser.getResult();
			    	xsSchemaIter = sset.iterateSchema();
					while(xsSchemaIter.hasNext()) {
						xsSchema = (XSSchema) xsSchemaIter.next();
						xsElementDecl = xsSchema.getElementDecl(partType);
						if (xsElementDecl != null) {
							getElement(xsSchema, xsElementDecl, paramsAndTypes);
							break;
						}
					}
			    }
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramsAndTypes;
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getAllOperationNames(Definition definition) {
		List<String> operationNames = new ArrayList<>();
		Map<QName, PortType> portTypes = (Map<QName, PortType>) definition.getPortTypes();
		Set<QName> ptKeySet = portTypes.keySet();
		Iterator<QName> ptIter = ptKeySet.iterator();
		QName ptKey = null;
		PortType pt = null;
		while (ptIter.hasNext()) {
			ptKey = (QName) ptIter.next();
			pt = portTypes.get(ptKey);
			for (Operation op : (List<Operation>)pt.getOperations()) {
				operationNames.add(op.getName());
			}
		}
		return operationNames;
	}
	
	private void getElement(XSSchema xsSchema, XSElementDecl xsElementDecl, Map<String, String> paramsAndTypes) {
		try {
			XSComplexType xsComplexType = null;
			XSParticle xsParticle = null;
			XSTerm term = null;
			XSElementDecl elementDecl = null;
//			XSSimpleType xsSimpleType = null;
			if (xsElementDecl.getType().isComplexType()) {
//				System.out.println(xsElementDecl.getName() + " Element Complex Type is : " + xsElementDecl.getType().getName());
				xsComplexType = xsSchema.getComplexType(xsElementDecl.getType().getName());
//				if (xsComplexType == null) {
//					System.out.println(xsElementDecl.getName() + " xsComplexType Is NUll!!! " + xsElementDecl.getType().getName());
//				} else 
				if (xsComplexType.getContentType() != null) {
					xsParticle = xsComplexType.getContentType().asParticle();
					if (xsParticle != null) {
						term = xsParticle.getTerm();
						if (term.isModelGroup()) {
							printModalGroup(xsSchema, term.asModelGroup(), paramsAndTypes);
						} else if (term.isElementDecl()) {
							elementDecl = term.asElementDecl();
							getElement(xsSchema, elementDecl, paramsAndTypes);
						} else if (term.isModelGroupDecl()) {
							System.out.println(term.asModelGroupDecl().getName() + "is a modal group desc!!!");
						} else if (term.isWildcard()) {
							System.out.println(term.asWildcard() + "is a wildcard!!!");
						}
//					} else {
//						xsSimpleType = xsComplexType.getContentType().asSimpleType();
//						if (xsSimpleType != null) {
//							System.out.println(xsComplexType.getName() + " Is Simple Type " + xsSimpleType.getName());
//						}
					}
//				} else {
//					System.out.println(xsElementDecl.getName() + " Element Type is : " + xsComplexType.getName() + " Content Type Is NUll");
				}
			} else if (xsElementDecl.getType().isSimpleType()) {
				paramsAndTypes.put(xsElementDecl.getName(), xsElementDecl.getType().getName());
//				System.out.println(xsElementDecl.getName() + "  Element Simple Type is : " + xsElementDecl.getType().getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void printModalGroup(XSSchema xsSchema, XSModelGroup xsModelGroup, Map<String, String> paramsAndTypes) {
		try {
			XSParticle[] particles = xsModelGroup.getChildren();
			XSElementDecl xsElementDecl = null;
			for (XSParticle p : particles) {
				XSTerm term = p.getTerm();
				if (term.isElementDecl()) {
					xsElementDecl = term.asElementDecl();
					getElement(xsSchema, xsElementDecl, paramsAndTypes);
				} else if (term.isModelGroup()) {
//					System.out.println("Recuring printModalGroup");
					printModalGroup(xsSchema, term.asModelGroup(), paramsAndTypes);
				} else if (term.isModelGroupDecl()) {
					System.out.println(term.asModelGroupDecl().getName() + "is a modal group desc!!!");
				} else if (term.isWildcard()) {
					System.out.println(term.asWildcard() + "is a wildcard!!!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private HashMap mappings = new HashMap();

	public void generate(String url) throws Exception {

		XSOMParser parser = new XSOMParser();

		parser.parse(new URL(url));
		XSSchemaSet sset = parser.getResult();

		// =========================================================
		// types namepace
		XSSchema gtypesSchema = sset.getSchema("http://subvention.endpoints.service.sfa.crm.turkcell.com/");
		Iterator<XSComplexType> ctiter = gtypesSchema.iterateComplexTypes();
		while (ctiter.hasNext()) {
			XSComplexType ct = (XSComplexType) ctiter.next();
			String typeName = ct.getName();
			// these are extensions so look at the base type to see what it is
			String baseTypeName = ct.getBaseType().getName();
			System.out.println(typeName + " is a " + baseTypeName);
		}

		// =========================================================
		// global namespace
		XSSchema globalSchema = sset.getSchema("");
		// local definitions of enums are in complex types
		ctiter = globalSchema.iterateComplexTypes();
		while (ctiter.hasNext()) {
			XSComplexType ct = (XSComplexType) ctiter.next();
			String typeName = ct.getName();
			String baseTypeName = ct.getBaseType().getName();
			System.out.println(typeName + " is a " + baseTypeName);
		}

		// =========================================================
		// the main entity of this file is in the Elements
		// there should only be one!
		if (globalSchema.getElementDecls().size() != 1) {
			throw new Exception("Should be only elment type per file.");
		}

		XSElementDecl ed = globalSchema.getElementDecls().values()
				.toArray(new XSElementDecl[0])[0];
		String entityType = ed.getName();
		XSContentType xsContentType = ed.getType().asComplexType()
				.getContentType();
		XSParticle particle = xsContentType.asParticle();
		if (particle != null) {

			XSTerm term = particle.getTerm();
			if (term.isModelGroup()) {
				XSModelGroup xsModelGroup = term.asModelGroup();
				XSParticle[] particles = xsModelGroup.getChildren();
				String propertyName = null;
				String propertyType = null;
				XSParticle pp = particles[0];
				for (XSParticle p : particles) {
					XSTerm pterm = p.getTerm();
					if (pterm.isElementDecl()) {
						propertyName = pterm.asElementDecl().getName();
						if (pterm.asElementDecl().getType().getName() == null) {
							propertyType = pterm.asElementDecl().getType()
									.getBaseType().getName();
						} else {
							propertyType = pterm.asElementDecl().getType()
									.getName();
						}
						System.out.println(propertyName + " is a "
								+ propertyType);
					}
				}
			}
		}
		return;
	  }
}
