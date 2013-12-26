package com.ns.deneme.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ns.deneme.vo.WsField;
import com.ns.deneme.vo.WsResponse;
import com.ns.deneme.vo.WsRunParams;
import com.ns.deneme.ws.WebServiceManagerI;
import com.ns.deneme.ws.WsdlOperations;

@Controller
@RequestMapping(value = "/ws", method = RequestMethod.POST)
public class WebServiceController {
	
	private static Logger logger = LoggerFactory.getLogger(WebServiceController.class);
	
	@Autowired
	private WebServiceManagerI webServiceManager;
	
	@RequestMapping(value = "/runWS", consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Map<String, Object> runWebService(@RequestBody WsRunParams wsRunParams, Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
	   	logger.debug("Running Web Service...");
	   	try {
	   		Definition definition = webServiceManager.getWsdlDefinition(wsRunParams.getWsdlUrl());
			Operation operation = webServiceManager.getOperation(wsRunParams.getOperation(), definition);
			Map<String, String> inputParamAndTypes = webServiceManager.getOperationInputParams(operation, definition);
			Map<String, String> outputParamAndTypes = webServiceManager.getOperationOutputParams(operation, definition);
		   	Object[] wsResponse = webServiceManager.callWsOperation(wsRunParams.getWsdlUrl(), wsRunParams.getOperation(), 
		   								definition.getTargetNamespace(), inputParamAndTypes.get(wsRunParams.getOperation()), 
		   								outputParamAndTypes.get(wsRunParams.getOperation()), wsRunParams.getParamNameAndparamValue());
		   	WsResponse response = null;
		   	for (Object resObj : wsResponse) {
		   		response = prepareWsResponse(resObj);
			}
	   		data.put("status", "1");
			data.put("message", "Web Service Run Successfully.");
			data.put("wsResponse", response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Running Web Service Was Failed!!!");
		} finally {
			logger.debug("Running Web Service is Ended");
		}
	 	return data;
	}
	
	private WsResponse prepareWsResponse(Object obj) throws Exception {
	  	WsResponse response = null;
	   	WsField wsField;
	   	List<WsField> fields;
	   	fields = new ArrayList<WsField>();
	   	response = new WsResponse();
	   	response.setClassName(obj.getClass().getSimpleName());
	   	for (Field field : obj.getClass().getDeclaredFields()) {
	   		wsField = new WsField();
   			wsField.setFieldName(field.getName());
	   		if (field.getType().getName().startsWith("java") 
	   				&& !field.getType().isArray()
	   				&& !Collection.class.isAssignableFrom(field.getType())
	   				&& !Map.class.isAssignableFrom(field.getType())) {
	   			wsField.setFieldValue(callGetterMethod(obj, field.getName(), field.getType(), new Object[]{}));
	   			fields.add(wsField);
			} else if (field.getType().isArray()) {
				
			} else if (Collection.class.isAssignableFrom(field.getType())) {
				
			} else if (Map.class.isAssignableFrom(field.getType())) {
				
			} else {
				wsField.setFieldValue(prepareWsResponse(callGetterMethod(obj, field.getName(), field.getType(), new Object[]{})));
			}
		}
	   	for (Field field : obj.getClass().getSuperclass().getDeclaredFields()) {
	   		wsField = new WsField();
   			wsField.setFieldName(field.getName());
	   		if (field.getType().getName().startsWith("java") 
	   				&& !field.getType().isArray()
	   				&& !Collection.class.isAssignableFrom(field.getType())
	   				&& !Map.class.isAssignableFrom(field.getType())) {
	   			wsField.setFieldValue(callGetterMethod(obj, field.getName(), field.getType(), new Object[]{}));
	   			fields.add(wsField);
			} else if (field.getType().isArray()) {
				
			} else if (Collection.class.isAssignableFrom(field.getType())) {
				
			} else if (Map.class.isAssignableFrom(field.getType())) {
				
			} else {
				wsField.setFieldValue(prepareWsResponse(callGetterMethod(obj, field.getName(), field.getType(), new Object[]{})));
			}
	   	}
	   	response.setFields(fields);
	   	return response;
	}
	 
	@RequestMapping(value = "/readWsdl")
	public @ResponseBody Map<String, Object> readWsdl(@RequestParam(value="wsdlUrl") String wsdlUrl, Model model) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			WsdlOperations wsdlOperations = webServiceManager.readWsdlAndGetAllOperations(wsdlUrl);
			data.put("status", "1");
			data.put("message", "Read WSDL success.");
			data.put("operations", wsdlOperations.getOperationNames());
			
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Read WSDL Failed!!!");
		}
		return data;
	}
	 
	@RequestMapping(value = "/getOpDetail")
	public @ResponseBody Map<String, Object> getOperationDetail(@RequestParam(value="wsdlUrl") String wsdlUrl, 
			@RequestParam(value="operationName") String operationName, Model model) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Definition definition = webServiceManager.getWsdlDefinition(wsdlUrl);
			Operation operation = webServiceManager.getOperation(operationName, definition);
			Map<String, String> inputParamAndTypes = webServiceManager.getOperationInputParams(operation, definition);
			data.put("status", "1");
			data.put("message", "Operation Detail Is Successfully Retrieved.");
			data.put("inputParams", inputParamAndTypes);
		} catch (Exception e) {
			data.put("status", "-1");
			data.put("message", "Get Operation Detail Was Failed!!!");
		}
		return data;
	}
	
	/**
	 * castValueMethod casts the given object to given castClazz.  
	 *  
	 * @param input <code>Object</code> 
	 * @param castClazz <code>Class</code>
	 * 
	 * @return Object that is casted value of input object according to castClazz
	 * 
	 * @throws SpringBatchException
	 */
	public static Object castValueMethod(Object input, Class<?> castClazz, String fieldName) throws Exception {
		Object castValue = null;
		String[] unscaleValue = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			if (input == null) {
				logger.debug("castValueMethod run with null object <<<");
			} else if(castClazz != null && castClazz.isInstance(input)) {
				logger.debug("castValueMethod run with same class types <<<");
				if (String.class.isInstance(input)) {
					input = input.toString().trim();
				}
				return input;
			} else {
				logger.debug("castValueMethod(Input Class Name = " + input.getClass() + ", castClass = " + castClazz.getSimpleName() + ") <<<");
				if (BigDecimal.class.isInstance(input)) {
					if(Long.class.isAssignableFrom(castClazz)) {
						castValue = BigDecimal.class.cast(input).unscaledValue().longValue();
					} else if (Integer.class.isAssignableFrom(castClazz)) {
						castValue = BigDecimal.class.cast(input).unscaledValue().intValue();
					} else if (String.class.isAssignableFrom(castClazz)) {
						castValue = BigDecimal.class.cast(input).unscaledValue().toString();
					}
				} else if (String.class.isInstance(input)) {
					if (Date.class.isAssignableFrom(castClazz)) {
						castValue = new Date(java.sql.Date.valueOf(input.toString().trim()).getTime());
					} else if (java.sql.Date.class.isAssignableFrom(castClazz)) {
						castValue = java.sql.Date.valueOf(input.toString().trim());
					} else if (BigDecimal.class.isAssignableFrom(castClazz)) {
						if (input.toString().contains(".")) {
							unscaleValue = input.toString().trim().split("[.]");
							if (unscaleValue.length > 2) {
								throw new Exception("CastValueMethod's input is an illegal Argument. String to BigDecimal cast can not compilited!!! The String value is " + unscaleValue); 
							}
							castValue = BigDecimal.valueOf(Long.valueOf(unscaleValue[0] + unscaleValue[1]), unscaleValue[1].length());
						} else {
							castValue = BigDecimal.valueOf(Long.valueOf(input.toString().trim()), 2);
						}
					} else if (Long.class.isAssignableFrom(castClazz)) {
						castValue = Long.valueOf(input.toString().trim());
					} else if (Integer.class.isAssignableFrom(castClazz)) {
						castValue = Integer.valueOf(input.toString().trim());
					} else if (Boolean.class.isAssignableFrom(castClazz)) {
						castValue = Boolean.valueOf(input.toString().trim());
					}
				} else if (Date.class.isInstance(input) && String.class.isAssignableFrom(castClazz)) {
					castValue = sdf.format(Date.class.cast(input));
				} else if (Long.class.isInstance(input)) {
					if (BigDecimal.class.isAssignableFrom(castClazz)) {
						castValue = BigDecimal.valueOf(Long.class.cast(input), 2);
					} else if (Integer.class.isAssignableFrom(castClazz)) {
						castValue = Long.class.cast(input).intValue();
					} else if (String.class.isAssignableFrom(castClazz)) {
						castValue = input.toString();
					}
				} else if (Integer.class.isInstance(input)) {
					if (BigDecimal.class.isAssignableFrom(castClazz)) {
						castValue = BigDecimal.valueOf(Integer.class.cast(input).longValue(), 2);
					} else if (Long.class.isAssignableFrom(castClazz)) {
						castValue = Integer.class.cast(input).longValue();
					} else if (String.class.isAssignableFrom(castClazz)) {
						castValue = input.toString();
					}
				} else if (Float.class.isInstance(input)) {
					if (BigDecimal.class.isAssignableFrom(castClazz)) {
						castValue = new BigDecimal(Float.toString(123.4f));
					}
				} else {
					throw new Exception("CastValueMethod's input is an illegal Argument. Cast operation is not supported from " + input.getClass() + " to " + castClazz);
				}
			}
		} catch (Exception e) {
			logger.info("CastValueMethod's input is an illegal Argument. Check the job configuration for field " + fieldName + ". Problem at cast operation from " + input.getClass() + " to " + castClazz + ". And input value is " + input.toString(), e);
			throw new Exception(e);
		} finally {
			logger.debug("castValueMethod >>>");
		}
		return castValue;
	}
	
	/**
	 * callGetterMethod calls the getter method of given object, field name, fieldType and parameters.
	 *  
	 * @param object <code>Object</code> 
	 * @param fieldName <code>String</code>
	 * @param fieldType <code>Class</code>
	 * @param parameters <code>Object[]</code>
	 * 
	 * @return Object which is result of getter method
	 * 
	 * @throws Exception
	 */
	public static Object callGetterMethod(Object object, String fieldName,
			Class<?> fieldType, Object... parameters) throws Exception {
		logger.debug("callGetterMethod(Input Object = " + object.getClass().getName() 
										  + ", Field Name = " + fieldName 
										  + ", Field Type = " + fieldType.getName()
										  + ") <<<");
		Object result = null;
		String methodName = "";
		StringBuilder parameterValues = new StringBuilder();
		StringBuilder parameterClassValues = new StringBuilder();
		try {
			methodName = Character.toUpperCase(fieldName.charAt(0)) 
													 + fieldName.substring(1).trim();
			Class<?>[] parametersClasses = null;
			parameterValues.append("parameters[");
			parameterClassValues.append("parameterClasses[");
			if (parameters != null && parameters.length > 0) {
				parametersClasses = new Class[parameters.length];
				for (int i = 0; i < parameters.length; i++) {
					parametersClasses[i] = parameters[i].getClass();
					if (i != parameters.length - 1) {
						parameterValues.append(parameters[i] + ", ");
						parameterClassValues.append(parameters[i].getClass() + ", ");
					} else {
						parameterValues.append(parameters[i] + "]");
						parameterClassValues.append(parameters[i].getClass() + "]");
					}
				}
			}
			logger.debug(parameterValues.toString());
			logger.debug(parameterClassValues.toString());

			Method getterMethod = null;
			if (Boolean.class.isAssignableFrom(fieldType)) {
				getterMethod = object.getClass().getMethod("is" + methodName , parametersClasses);
			} else {
				getterMethod = object.getClass().getMethod("get" + methodName, parametersClasses);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Method Name = " + getterMethod.getName());
			}
			result = getterMethod.invoke(object, parameters);
		} catch (Exception e) {
			throw new Exception("Check the job configuration for getter method name : " + methodName 
										 + " and " + parameterValues.toString()
										 + ". Probably, one of field's type is wrong in the jobproperties file. "
										 + e.getCause(), e);
		} finally {
			if (result != null) {
				logger.debug("callGetterMethod result = " + result + " >>>");
			} else {
				logger.debug("callGetterMethod result is empty >>>");
			}
		}
		return result;
	}

}
