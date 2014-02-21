package com.ns.deneme.neo4j.domain.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public enum FieldType {
	
	STRING("String", String.class),
	CHAR("Char", Character.class),
	INTEGER("Integer", Integer.class),
	LONG("Long", Long.class),
	DOUBLE("Double", Double.class),
	FLOAT("Float", Float.class),
	BOOLEAN("Boolean", Boolean.class),
	DATE("Date", Date.class),
	DATETIME("DateTime", Calendar.class);
	
	public String name;
	
	public Class<?> typeClass;

	private FieldType(String name, Class<?> typeClass) {
		this.name = name;
		this.typeClass = typeClass;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public static FieldType getValue(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		for (FieldType fieldType : values()) {
			if (fieldType.name.equals(name.trim())) {
				return fieldType;
			}
		}
		return null;
	}

}
