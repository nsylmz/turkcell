package com.ns.deneme.bytecode;

import com.ns.deneme.bytecode.util.AnnotationMemberType;

public class AnnotationMember {
	
	private String memberName;
	
	private AnnotationMemberType memberType;
	
	private Object memberValue;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public AnnotationMemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(AnnotationMemberType memberType) {
		this.memberType = memberType;
	}

	public Object getMemberValue() {
		return memberValue;
	}

	public void setMemberValue(Object memberValue) {
		this.memberValue = memberValue;
	}
	
}
