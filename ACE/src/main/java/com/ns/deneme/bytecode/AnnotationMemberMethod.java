package com.ns.deneme.bytecode;


public class AnnotationMemberMethod {

	private String methodName;

	private Class<?> methodParamClass;
	
	public AnnotationMemberMethod() {
		super();
	}
	
	public AnnotationMemberMethod(String methodName, Class<?> methodParamClass) {
		this.methodName = methodName;
		this.methodParamClass = methodParamClass;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?> getMethodParamClass() {
		return methodParamClass;
	}

	public void setMethodParamClass(Class<?> methodParamClass) {
		this.methodParamClass = methodParamClass;
	}

}
