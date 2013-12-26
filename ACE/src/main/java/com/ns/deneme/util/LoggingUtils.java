package com.ns.deneme.util;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtils {
	
	private static Logger logger = LoggerFactory.getLogger(LoggingUtils.class);
    private static final String COREBANKING_PACKAGE_PREFIX = "com.isbank";
    private static final String CLASS = "class";

    /**
	 * The <code>printObject</code> method logs any type of object at debug level
	 * 
	 * @param obj <code>Object</code>
	 * @throws <code>IllegalAccessException<code>
     * @throws <code>InvocationTargetException<code>
     * @throws <code>NoSuchMethodException<code>
	 */
	public static void printObject(Object obj) {
		if (logger.isDebugEnabled()) {
			printObjectInner(obj, "", "\t");
		}
	}
	
	private static void printObjectInner(Object obj, String tab, String innerTab) {
		try {
			String objectName = getBeanDescriptor(obj).getName();
			logger.debug(tab + "<" + objectName + ">");
//			System.out.println(tab + "<" + objectName + ">");
			PropertyDescriptor descriptors[] = null;
			descriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(obj);
			PropertyUtilsBean pub = new PropertyUtilsBean();
			for (int i = 0; i < descriptors.length; i++) {
				
				Class<?> currentClass = descriptors[i].getPropertyType();
			    String className = currentClass.getName();
				String key = descriptors[i].getName();
			    
				if (className != null && !key.equals(CLASS)) {
				    if (className.startsWith(COREBANKING_PACKAGE_PREFIX)) {
				    	logger.debug(innerTab + "<" + key + ">");
//				    	System.out.println(innerTab + "<" + key + ">");
				    	
				    	Object origNestedObject = pub.getNestedProperty(obj, key);
				    	if (origNestedObject != null) {
							printObjectInner(origNestedObject, tab + "\t", innerTab + "\t");
						}
				    	
						logger.debug(innerTab + "</" + key + ">");
//						System.out.println(innerTab + "</" + key + ">");
				    } else if (descriptors[i].getPropertyType().isArray()) {
				    	logger.debug(innerTab + "<" + key + ">");
//				    	System.out.println(innerTab + "</" + key + ">");
						if (className.contains("java.lang") || ((className.length()-1) - className.lastIndexOf("[")) == 1) {
							printArray(pub.getNestedProperty(obj, key), 0, innerTab+"\t", "");
						} else if (pub.getNestedProperty(obj, key) != null) {
							int size = Array.getLength(pub.getNestedProperty(obj, key));
							for (int j = 0; j < size; j++) {
								Object origNestedObject = pub.getNestedProperty(obj, key+ "[" + j + "]");
								if (origNestedObject != null) {
									printObjectInner(origNestedObject, tab + "\t\t", innerTab + "\t\t");
								}
							}
						}
						logger.debug(innerTab + "</" + key + ">");
//						System.out.println(innerTab + "</" + key + ">");
					} else {
				    	logger.debug(innerTab + "<" + key + "> " 
				    				  + BeanUtilsBean.getInstance().getProperty(obj, key) 
				    			      + " </" + key + ">");
//				    	System.out.println(innerTab + "<" + key + "> " 
//			    				  + BeanUtilsBean.getInstance().getProperty(obj, key) 
//			    			      + " </" + key + ">");
					}
				}
			}
			logger.debug(tab + "<" + objectName + ">");
//			System.out.println(tab + "<" + objectName + ">");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private static BeanDescriptor getBeanDescriptor(Object bean) {
		if (bean == null) {

			throw new IllegalArgumentException("No bean specified");
		}
		return getBeanDescriptor(bean.getClass());
	}
	
	private static BeanDescriptor getBeanDescriptor(Class<?> beanClass) {
		if (beanClass == null) {
			throw new IllegalArgumentException("No bean class specified");
		}
		BeanDescriptor beanDescriptor = null;
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		} catch (IntrospectionException e) {
			logger.error(e.getMessage(), e);
		}
		beanDescriptor = beanInfo.getBeanDescriptor();
		return beanDescriptor;
	}
	
	public static <E> void printAnyTypeArray(E[] inputArray) {
		// display array elements
		for (E element : inputArray)
			System.out.printf("%s ", element);

	}
	

	/**
	 * The <code>printArray</code> method logs an array that is primitive type, at debug level
	 * 
	 * @param obj <code>Object</code>
	 * @param tab <code>String</code>
	 * @param level <code>int</code>
	 * @param printString <code>String</code>
	 */
	private static void printArray(Object obj, int level, String tab, String printString) {
		// if obj is not an array return
		if (obj == null || !obj.getClass().isArray())
			return;
		
		int length = Array.getLength(obj); // get the length of the array
		// adding extra tabs depending on the dimension we are trying to print

//		String tab = "";
//		String del = "\t";
//		for (int i = 0; i < level; i++) {
//			tab += del;
//		}

		int i = 0;
//		System.out.println(tab + "Array " + level);

		if (i < length) {
			Object o = Array.get(obj, i); // get the ith element
			if (o == null || !o.getClass().isArray()) {
//				System.out.print(tab + o + " "); // print call with tabs
				printString = printString + tab + o;
			} else {
				printArray(o, level + 1, tab, printString);
			}
		}

		for (i = 1; i < length - 1; i++) {
			Object o = Array.get(obj, i);
			if (o == null || !o.getClass().isArray()) {
//				System.out.print(o + " "); // print call without tabs
				printString = printString + o + " ";
			} else {
				printArray(o, level + 1, tab, printString);
			}
		}

		if (i < length) {
			Object o = Array.get(obj, i);
			if (o == null || !o.getClass().isArray()) {
//				System.out.println(o); // println call for changing lines
				logger.debug(printString);
			} else {
				printArray(o, level + 1, tab, printString);
			}
		}
	}
	
//	public static void main(String[] args) {
//		Asd test = new Asd();
//		Set<Boolean> asd = new HashSet<Boolean>();
////		asd[0][0] = true;
////		asd[0][1] = true;
////		asd[1][0] = true;
////		asd[1][1] = true;
//		asd.add(true);
//		asd.add(false);
//		asd.add(true);
//		asd.add(true);
//		asd.add(true);
//		test.setAbc(asd);
//		printObjectInner(test, "", "\t");
////		printArray(asd, 0, "\t");
//	}

}
