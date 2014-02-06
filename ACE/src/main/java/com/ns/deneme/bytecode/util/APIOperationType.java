package com.ns.deneme.bytecode.util;

import org.apache.commons.lang.StringUtils;

public enum APIOperationType {
	
	SAVE("save", "ajc$tjp_0"), 
	DELETE("delete", "ajc$tjp_1"), 
	DELETEBYID("deleteById", "ajc$tjp_2"), 
	FINDBYPROPERTYVALUE("findByPropertyValue", "ajc$tjp_3"),
	FINDONE("findOne", "ajc$tjp_4"), 
	FINDALL("findAll", "ajc$tjp_5");
	
	public String value;
	
	public String aspectJVariableName;

	private APIOperationType(String value, String aspectJVariableName) {
		this.value = value;
		this.aspectJVariableName = aspectJVariableName;
	}
	
	@Override
	public String toString() {
		return value;
	}

	public static APIOperationType getValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		for (APIOperationType opType : values()) {
			if (opType.value.equals(value.trim())) {
				return opType;
			}
		}
		return null;
	}
}
