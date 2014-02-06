package com.ns.deneme.bytecode;

import java.io.Serializable;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class Deneme {

	public static void main(String[] args) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		pool.importPackage("javax.presistence");
		CtClass cc = pool.makeClass("Deneme1");
		cc.setSuperclass(pool.get("java.lang.Object"));
		cc.addInterface(pool.get(Serializable.class.getName()));
		
		// Add Annotation to Class
		ClassFile ccFile = cc.getClassFile();
		ConstPool constpool = ccFile.getConstPool();
		constpool.addClassInfo(Entity.class.getName());
		
		AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
		Annotation annot = new Annotation(Entity.class.getName(), constpool);
		attr.addAnnotation(annot);
		ccFile.addAttribute(attr);
		
		annot = new Annotation(Table.class.getName(), constpool);
		annot.addMemberValue("name", new StringMemberValue("user",ccFile.getConstPool()));
		attr.addAnnotation(annot);
		ccFile.addAttribute(attr);
		
		//Add constant field to Class
		CtField id = new CtField(CtClass.intType, "id", cc);
		cc.addField(id);
		id.setModifiers(Modifier.PRIVATE);
		
		AnnotationsAttribute attrId = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
		annot = new Annotation(Id.class.getName(), constpool);
		attrId.addAnnotation(annot);
		id.getFieldInfo().addAttribute(attrId);
		
		// Add method to Class
		CtMethod setterId = CtNewMethod.setter("setId", id);
		CtMethod getterId = CtNewMethod.getter("getId", id);
		cc.addMethod(setterId);
		cc.addMethod(getterId);
		
		
		cc.writeFile(".");
	}

}
