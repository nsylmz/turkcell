package com.ns.deneme.bytecode.util;

import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.CharMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.DoubleMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.FloatMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.ShortMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import org.apache.commons.lang.StringUtils;

import com.ns.deneme.bytecode.AnnotationMemberMethod;

public enum AnnotationMemberType {
	
	ANNOTATION("annotation", AnnotationMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", Annotation.class)}),
	BOOLEAN("boolean", BooleanMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", Boolean.class)}), 
	STRING("string", StringMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", String.class)}), 
	SHORT("short", ShortMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", Short.class)}),
	INTEGER("integer", IntegerMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", Integer.class)}), 
	LONG("long", LongMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", Long.class)}), 
	FLOAT("float", FloatMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", Float.class)}),
	DOUBLE("double", DoubleMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", Double.class)}), 
	CHAR("char", CharMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", Character.class)}),
	CLASS("class", ClassMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", Class.class)}),
	ENUM("char", EnumMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", String.class), new AnnotationMemberMethod("setType", String.class)}),
	ARRAY("array", ArrayMemberValue.class, new AnnotationMemberMethod[]{new AnnotationMemberMethod("setValue", Annotation.class)});
	
	public String name;
	
	public Class<? extends MemberValue> javassistClass;
	
	public AnnotationMemberMethod[] methods;

	private AnnotationMemberType(String name, Class<? extends MemberValue> javassistClass, AnnotationMemberMethod[] methods) {
		this.name = name;
		this.javassistClass = javassistClass;
		this.methods = methods;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public static AnnotationMemberType getValue(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		for (AnnotationMemberType annotType : values()) {
			if (annotType.name.equals(name.trim())) {
				return annotType;
			}
		}
		return null;
	}

}
