package com.ns.deneme.bytecode;

import java.util.List;


public class NodeAnnotationProperty {
	
	private Class<?> annotationInterface;
	
	private List<AnnotationMember> annotationMembers;

	public Class<?> getAnnotationInterface() {
		return annotationInterface;
	}

	public void setAnnotationInterface(Class<?> annotationInterface) {
		this.annotationInterface = annotationInterface;
	}

	public List<AnnotationMember> getAnnotationMembers() {
		return annotationMembers;
	}

	public void setAnnotationMembers(List<AnnotationMember> annotationMembers) {
		this.annotationMembers = annotationMembers;
	}

}
