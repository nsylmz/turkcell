package com.ns.deneme.ws.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.schema.Schema;
import javax.xml.namespace.QName;

import org.apache.cxf.BusException;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.wsdl11.WSDLManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.ibm.wsdl.extensions.schema.SchemaImportImpl;
import com.ns.deneme.ws.WSRequestParameter;
import com.ns.deneme.ws.WebServiceManagerI;
import com.ns.deneme.ws.WsdlOperations;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSTerm;
import com.sun.xml.xsom.parser.XSOMParser;

@Component
public class WebServiceManagerImpl implements WebServiceManagerI {
	
	private static Logger logger = LoggerFactory.getLogger(WebServiceManagerImpl.class);
	
	
	private static Map<String, String> BUILTIN_DATATYPES_MAP = new HashMap<String, String>(); 
	private static Map<String, Class<?>> HOLDER_TYPES_MAP = new HashMap<String, Class<?>>();
	
	static {
		BUILTIN_DATATYPES_MAP.put("string", "java.lang.String");
		BUILTIN_DATATYPES_MAP.put("integer", "java.math.BigInteger");
		BUILTIN_DATATYPES_MAP.put("int", "int");
		BUILTIN_DATATYPES_MAP.put("long", "long");
		BUILTIN_DATATYPES_MAP.put("short", "short");
		BUILTIN_DATATYPES_MAP.put("decimal", "java.math.BigDecimal");
		BUILTIN_DATATYPES_MAP.put("float", "float");
		BUILTIN_DATATYPES_MAP.put("double", "double");
		BUILTIN_DATATYPES_MAP.put("boolean", "boolean");
		BUILTIN_DATATYPES_MAP.put("byte", "byte");
		BUILTIN_DATATYPES_MAP.put("QName", "javax.xml.namespace.QName");
		BUILTIN_DATATYPES_MAP.put("dateTime", "javax.xml.datatype.XMLGregorianCalendar");
		BUILTIN_DATATYPES_MAP.put("base64Binary", "byte[]");
		BUILTIN_DATATYPES_MAP.put("hexBinary", "byte[]");
		BUILTIN_DATATYPES_MAP.put("unsignedInt", "long");
		BUILTIN_DATATYPES_MAP.put("unsignedShort", "short");
		BUILTIN_DATATYPES_MAP.put("unsignedByte", "byte");
		BUILTIN_DATATYPES_MAP.put("time", "javax.xml.datatype.XMLGregorianCalendar");
		BUILTIN_DATATYPES_MAP.put("date", "javax.xml.datatype.XMLGregorianCalendar");
		BUILTIN_DATATYPES_MAP.put("gYear", "javax.xml.datatype.XMLGregorianCalendar");
		BUILTIN_DATATYPES_MAP.put("gYearMonth", "javax.xml.datatype.XMLGregorianCalendar");
		BUILTIN_DATATYPES_MAP.put("gMonth", "javax.xml.datatype.XMLGregorianCalendar");
		BUILTIN_DATATYPES_MAP.put("gMonthDay", "javax.xml.datatype.XMLGregorianCalendar");
		BUILTIN_DATATYPES_MAP.put("gDay", "javax.xml.datatype.XMLGregorianCalendar");
		BUILTIN_DATATYPES_MAP.put("duration", "javax.xml.datatype.Duration");
		BUILTIN_DATATYPES_MAP.put("NOTATION", "javax.xml.namespace.QName");
		
		HOLDER_TYPES_MAP.put("int", java.lang.Integer.class);
		HOLDER_TYPES_MAP.put("long", java.lang.Long.class);
		HOLDER_TYPES_MAP.put("short", java.lang.Short.class);
		HOLDER_TYPES_MAP.put("float", java.lang.Float.class);
		HOLDER_TYPES_MAP.put("double", java.lang.Double.class);
		HOLDER_TYPES_MAP.put("boolean", java.lang.Boolean.class);
		HOLDER_TYPES_MAP.put("byte", java.lang.Byte.class);
	}
	
	public Object[] callWsOperation(String wsdlUrl, String operation, String operationSchemaName, String operationRequestType, 
		String operationResponseType, Map<String, WSRequestParameter> paramNameAndparamValue) {
		Object[] response = null;
		try {
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Client client = dcf.createClient(wsdlUrl, loader);
			
			Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(getClassloaderPackage(operationSchemaName) 
							   + Character.toUpperCase(operationRequestType.charAt(0)) + operationRequestType.substring(1));
			Object request = clazz.newInstance();
			Method[] allMethods = request.getClass().getDeclaredMethods();
			Method[] superAllMethods =request.getClass().getSuperclass().getDeclaredMethods();
			
			prepareRequest(request, superAllMethods, paramNameAndparamValue);
			prepareRequest(request, allMethods, paramNameAndparamValue);
			response = client.invoke(operation, request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	private void prepareRequest(Object object, Method[] methods, Map<String, WSRequestParameter> paramNameAndparamValue) {
		try {
			String methodName = null;
			String paramValue = null;
			String paramType = null;
			Class<?> paramClass = null;
			Object param = null;
			for (Method method : methods) {
				methodName = method.getName().replace("set", "");
				methodName = Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
				if (paramNameAndparamValue.get(methodName) != null) {
					paramType = paramNameAndparamValue.get(methodName).getParamType();
					paramValue = paramNameAndparamValue.get(methodName).getParamValue();
					if (HOLDER_TYPES_MAP.get(paramType) != null) {
						paramClass = HOLDER_TYPES_MAP.get(paramType);
					} else if (BUILTIN_DATATYPES_MAP.get(paramType) != null) {
						paramClass = Thread.currentThread().getContextClassLoader().loadClass(BUILTIN_DATATYPES_MAP.get(paramType));
					} else {
						// TODO : Throw Exception
					}
					param = prepareParam(paramClass, paramValue);
					method.invoke(object, param);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private String getClassloaderPackage(String operationSchemaName) {
		operationSchemaName = operationSchemaName.replace("http://", "").replace("/", "");
		String[] folders = operationSchemaName.split("[.]");
		StringBuilder str = new StringBuilder();
		for (int i = folders.length-1; i > -1; i--) {
			str.append(folders[i]).append(".");
		}
		return str.toString();
	}
	
	private Object prepareParam(Class<?> paramClass, String paramValue) {
		if (paramClass.isAssignableFrom(String.class)) {
			return paramValue;
		} else if (paramClass.isAssignableFrom(Integer.class)) {
			return new Integer(paramValue);
		} else if (paramClass.isAssignableFrom(Date.class)) {
			// TODO : Handle java.util.Date
		} else if (paramClass.isAssignableFrom(Long.class)) {
			return new Long(paramValue);
		} else if (paramClass.isAssignableFrom(BigDecimal.class)) {
			return new BigDecimal(paramValue);
		} else if (paramClass.isAssignableFrom(Boolean.class)) {
			return new Boolean(paramValue);
		} else if (paramClass.isAssignableFrom(Short.class)) {
			return new Short(paramValue);
		} else if (paramClass.isAssignableFrom(Byte.class)) {
			return new Byte(paramValue);
		} else if (paramClass.isAssignableFrom(Double.class)) {
			return new Double(paramValue);
		} else if (paramClass.isAssignableFrom(Float.class)) {
			return new Float(paramValue);
		}
		return null;
	}
	
	public WsdlOperations generateClientAndGetAllOperations(String wsdlUrl) {
		WsdlOperations wsdlOperations = new WsdlOperations();
		try {
			WSDLManagerImpl wsdlManager = new WSDLManagerImpl();
			wsdlOperations.setDefinition(wsdlManager.getDefinition(wsdlUrl));
			wsdlOperations.setOperationNames(getAllOperationNames(wsdlOperations.getDefinition()));
		} catch (BusException e) {
			logger.error(e.getMessage(), e);
		} catch (WSDLException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return wsdlOperations;
	}
	
	
	@SuppressWarnings("unchecked")
	public Operation getOperation(String operationName, Definition definition) {
		Map<QName, PortType> portTypes = (Map<QName, PortType>) definition.getPortTypes();
		Set<QName> ptKeySet = portTypes.keySet();
		Iterator<QName> ptIter = ptKeySet.iterator();
		QName ptKey = null;
		PortType pt = null;
		while (ptIter.hasNext()) {
			ptKey = ptIter.next();
			pt = portTypes.get(ptKey);
			for (Operation op : (List<Operation>)pt.getOperations()) {
				if (op.getName().trim().equals(operationName)) {
					return op;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String getOperationInputName(Operation op, Definition definition) {
		String partKey = null;
		Set<String> partKeySet = null;
		Iterator<String> partIter = null;
		Part part = null;
		String partType = null;
		Map<String, Part> parts = (Map<String, Part>) op.getInput().getMessage().getParts();
		partKeySet = parts.keySet();
		partIter = partKeySet.iterator();
		while (partIter.hasNext()) {
			partKey = partIter.next();
			part = parts.get(partKey);
			partType = part.getElementName().getLocalPart();
		}
		return partType;
	}
	
	@SuppressWarnings("unchecked")
	public String getOperationOutputName(Operation op, Definition definition) {
		String partKey = null;
		Set<String> partKeySet = null;
		Iterator<String> partIter = null;
		Part part = null;
		String partType = null;
		Map<String, Part> parts = (Map<String, Part>) op.getOutput().getMessage().getParts();
		partKeySet = parts.keySet();
		partIter = partKeySet.iterator();
		while (partIter.hasNext()) {
			partKey = partIter.next();
			part = parts.get(partKey);
			partType = part.getElementName().getLocalPart();
		}
		return partType;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getOperationInputParams(Operation op, Definition definition) {
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
				partKey = partIter.next();
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
			    	key = iter.next();
			    	System.out.println("Import Schema Location " + imports.get(key).firstElement().getSchemaLocationURI());
			    	XSOMParser parser = new XSOMParser();
			    	parser.parse(new URL(imports.get(key).firstElement().getSchemaLocationURI()));
			    	sset = parser.getResult();
			    	xsSchemaIter = sset.iterateSchema();
					while(xsSchemaIter.hasNext()) {
						xsSchema = xsSchemaIter.next();
						xsElementDecl = xsSchema.getElementDecl(partType);
						if (xsElementDecl != null) {
							getElement(xsSchema, xsElementDecl, paramsAndTypes);
							break;
						}
					}
			    }
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		} catch (SAXException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return paramsAndTypes;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getOperationOutputParams(Operation op, Definition definition) {
		Map<String, String> paramsAndTypes = new HashMap<String, String>();
		try {
			String partKey = null;
			Set<String> partKeySet = null;
			Iterator<String> partIter = null;
			Part part = null;
			String partType = null;
			Map<String, Part> parts = (Map<String, Part>) op.getOutput().getMessage().getParts();
			partKeySet = parts.keySet();
			partIter = partKeySet.iterator();
			while (partIter.hasNext()) {
				partKey = partIter.next();
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
			    	key = iter.next();
			    	System.out.println("Import Schema Location " + imports.get(key).firstElement().getSchemaLocationURI());
			    	XSOMParser parser = new XSOMParser();
			    	parser.parse(new URL(imports.get(key).firstElement().getSchemaLocationURI()));
			    	sset = parser.getResult();
			    	xsSchemaIter = sset.iterateSchema();
					while(xsSchemaIter.hasNext()) {
						xsSchema = xsSchemaIter.next();
						xsElementDecl = xsSchema.getElementDecl(partType);
						if (xsElementDecl != null) {
							getElement(xsSchema, xsElementDecl, paramsAndTypes);
							break;
						}
					}
			    }
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		} catch (SAXException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return paramsAndTypes;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllOperationNames(Definition definition) {
		List<String> operationNames = new ArrayList<>();
		Map<QName, PortType> portTypes = (Map<QName, PortType>) definition.getPortTypes();
		Set<QName> ptKeySet = portTypes.keySet();
		Iterator<QName> ptIter = ptKeySet.iterator();
		QName ptKey = null;
		PortType pt = null;
		while (ptIter.hasNext()) {
			ptKey = ptIter.next();
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
			XSTerm explicitTerm = null;
			XSParticle explicitParticle = null;
			XSElementDecl elementDecl = null;
			if (xsElementDecl.getType().isComplexType()) {
				xsComplexType = xsSchema.getComplexType(xsElementDecl.getType().getName());
				if (xsComplexType.getExplicitContent() != null) {
					explicitParticle = xsComplexType.getExplicitContent().asParticle();
					if (explicitParticle != null) {
						explicitTerm = explicitParticle.getTerm();
						if (explicitTerm.isModelGroup()) {
							inspectModalGroup(xsSchema, explicitTerm.asModelGroup(), xsComplexType, paramsAndTypes);
						} else if (explicitTerm.isElementDecl()) {
							elementDecl = explicitTerm.asElementDecl();
							getElement(xsSchema, elementDecl, paramsAndTypes);
						} else if (explicitTerm.isModelGroupDecl()) {
							System.out.println(explicitTerm.asModelGroupDecl().getName() + "is a modal group desc!!!");
						} else if (explicitTerm.isWildcard()) {
							System.out.println(explicitTerm.asWildcard() + "is a wildcard!!!");
						}
					}
				}
				if (xsComplexType.getContentType() != null) {
					xsParticle = xsComplexType.getContentType().asParticle();
					if (xsParticle != null) {
						term = xsParticle.getTerm();
						if (term.isModelGroup()) {
							/*
							if (paramsAndTypes.get(xsElementDecl.getName()) == null
									&& !xsElementDecl.getName().equals(xsElementDecl.getType().getName())) {
								paramsAndTypes.put(xsElementDecl.getName(), xsElementDecl.getType().getName());
							}
							*/
							inspectModalGroup(xsSchema, term.asModelGroup(), xsComplexType, paramsAndTypes);
						} else if (term.isElementDecl()) {
							elementDecl = term.asElementDecl();
							getElement(xsSchema, elementDecl, paramsAndTypes);
						} else if (term.isModelGroupDecl()) {
							System.out.println(term.asModelGroupDecl().getName() + "is a modal group desc!!!");
						} else if (term.isWildcard()) {
							System.out.println(term.asWildcard() + "is a wildcard!!!");
						}
					}
				}
			} else if (xsElementDecl.getType().isSimpleType()) {
				paramsAndTypes.put(xsElementDecl.getName(), xsElementDecl.getType().getName());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void inspectModalGroup(XSSchema xsSchema, XSModelGroup xsModelGroup, XSComplexType xsComplexType, Map<String, String> paramsAndTypes) {
		try {
			XSParticle[] particles = xsModelGroup.getChildren();
			XSElementDecl xsElementDecl = null;
			for (XSParticle p : particles) {
				XSTerm term = p.getTerm();
				if (term.isElementDecl()) {
					xsElementDecl = term.asElementDecl();
					if (paramsAndTypes.get(xsComplexType.getName()) == null
							&& !xsElementDecl.getName().equals(xsElementDecl.getType().getName())
							&& xsElementDecl.getType().isComplexType()) {
						paramsAndTypes.put(xsComplexType.getName(), xsElementDecl.getType().getName());
					}
					getElement(xsSchema, xsElementDecl, paramsAndTypes);
				} else if (term.isModelGroup()) {
					inspectModalGroup(xsSchema, term.asModelGroup(), xsComplexType, paramsAndTypes);
				} else if (term.isModelGroupDecl()) {
					System.out.println(term.asModelGroupDecl().getName() + "is a modal group desc!!!");
				} else if (term.isWildcard()) {
					System.out.println(term.asWildcard() + "is a wildcard!!!");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
