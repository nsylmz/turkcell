package com.ns.deneme.bytecode;

import java.lang.reflect.Method;

import javassist.ClassClassPath;
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
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.MemberValue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ns.deneme.appContext.AppContext;
import com.ns.deneme.neo4j.domain.HtmlElementCategory;
import com.ns.deneme.neo4j.domain.TemplateEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-config.xml")
public class ByteCodeAPITest {
	
	private static Logger logger = LoggerFactory.getLogger(ByteCodeAPITest.class);
	
	private ClassPool pool;
	
	@Before
	public void before() {
		try {
			pool = ClassPool.getDefault();
			pool.insertClassPath(new ClassClassPath(this.getClass()));
			prepareInterface();
			prepareAPI();
//			prepareAPIFromTemplate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void test() {
		try {
//			registry.registerBeanDefinition("testAPI", beanDefinition);
			
			
			BeanDefinitionRegistry registry = ((BeanDefinitionRegistry) AppContext.getFactory());  
			
			GenericBeanDefinition beanDefinition = new GenericBeanDefinition();  
			beanDefinition.setBeanClassName(pool.get("com.ns.deneme.neo4j.api.impl.TestAPI").getName());  
			beanDefinition.setLazyInit(false);  
			beanDefinition.setAbstract(false);  
			beanDefinition.setAutowireCandidate(true);  
			beanDefinition.setScope("singleton");  
			
			String beanName = "testAPI";
			
			BeanComponentDefinition definition = new BeanComponentDefinition(beanDefinition, beanName);
			BeanDefinitionReaderUtils.registerBeanDefinition(definition, registry);
			
			Object obj = AppContext.getApplicationContext().getBean(beanName);
			
			Class<?>[] parametersClasses = new Class[]{HtmlElementCategory.class};
			Method save = obj.getClass().getMethod("saveHtmlElementCategory", parametersClasses);
			HtmlElementCategory category = new HtmlElementCategory();
			category.setCategoryName("dsfgdfgfdhgfujgh");
			save.invoke(obj, category);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void prepareInterface() {
		try {
			CtClass cc = pool.makeInterface("com.ns.deneme.neo4j.api.ITestAPI");
			CtMethod saveHtmlElementCategory = CtNewMethod.make("public void saveHtmlElementCategory(com.ns.deneme.neo4j.domain.HtmlElementCategory htmlElementCategory);", cc);
			cc.addMethod(saveHtmlElementCategory);
			
			cc.writeFile("target/classes/");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void prepareAPI() {
		try {
			CtClass cc = pool.makeClass("com.ns.deneme.neo4j.api.impl.TestAPI");
			cc.addInterface(pool.get("com.ns.deneme.neo4j.api.ITestAPI"));
			// Add Annotation to Class
			ClassFile ccFile = cc.getClassFile();
			ConstPool constpool = ccFile.getConstPool();
			constpool.addClassInfo(NodeEntity.class.getName());
			
			AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			Annotation component = new Annotation(Component.class.getName(), constpool);
			attr.addAnnotation(component);
			ccFile.addAttribute(attr);
			
			Annotation transactional = new Annotation(Transactional.class.getName(), constpool);
			
			BooleanMemberValue readOnly = new BooleanMemberValue(constpool);
			readOnly.setValue(false);
			transactional.addMemberValue("readOnly", readOnly);
			
			EnumMemberValue propagation = new EnumMemberValue(constpool);
			propagation.setType("org.springframework.transaction.annotation.Propagation");
			propagation.setValue("REQUIRED");
			transactional.addMemberValue("propagation", propagation);
			
			ClassMemberValue emptyResultDataAccessException = new ClassMemberValue("org.springframework.dao.EmptyResultDataAccessException", constpool);
			ArrayMemberValue noRollbackFor = new ArrayMemberValue(constpool);
			noRollbackFor.setValue(new MemberValue[]{emptyResultDataAccessException});
			transactional.addMemberValue("noRollbackFor", noRollbackFor);
			
			ClassMemberValue exception = new ClassMemberValue("java.lang.Exception", constpool);
			ArrayMemberValue rollbackFor = new ArrayMemberValue(constpool);
			rollbackFor.setValue(new MemberValue[]{exception});
			transactional.addMemberValue("rollbackFor", rollbackFor);
			
			attr.addAnnotation(transactional);
			ccFile.addAttribute(attr);
			
			CtField htmlElementCategoryRepository = new CtField(ClassPool.getDefault().get("com.ns.deneme.neo4j.repository.HtmlElementCategoryRepository"), "htmlElementCategoryRepository", cc);
			htmlElementCategoryRepository.setModifiers(Modifier.PRIVATE);
			
			AnnotationsAttribute attrAutowired = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			Annotation autowired = new Annotation(Autowired.class.getName(), constpool);
			attrAutowired.addAnnotation(autowired);
			htmlElementCategoryRepository.getFieldInfo().addAttribute(attrAutowired);
			
			cc.addField(htmlElementCategoryRepository);
			
			CtMethod saveHtmlElementCategory = CtNewMethod.make("public void saveHtmlElementCategory(com.ns.deneme.neo4j.domain.HtmlElementCategory htmlElementCategory);", cc);
			saveHtmlElementCategory.setBody("htmlElementCategoryRepository.save($1);");
			
			AnnotationsAttribute attrOverride = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			Annotation override = new Annotation(Override.class.getName(), constpool);
			attrOverride.addAnnotation(override);
			saveHtmlElementCategory.getMethodInfo().addAttribute(attrOverride);
			
			cc.addMethod(saveHtmlElementCategory);
			
			cc.writeFile("target/classes/");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void prepareAspectNodeFromTemplate() {
		try {
			CtClass cc = pool.getAndRename(TemplateEntity.class.getName(), "com.ns.deneme.neo4j.domain.Address");
			CtField city = new CtField(ClassPool.getDefault().get("java.lang.String"), "city", cc);
			cc.addField(city);
			city.setModifiers(Modifier.PRIVATE);
			// Add setter and getter method to Class
			CtMethod getterCity = CtNewMethod.getter("getCity", city);
//			ILineNumberAttribute attribute = (ILineNumberAttribute) getterCity.getMethodInfo().getCodeAttribute().getAttribute(LineNumberAttribute.tag);
			CtMethod setterCity = CtNewMethod.setter("setCity", city);
			cc.addMethod(getterCity);
			cc.addMethod(setterCity);
			cc.writeFile("target/classes/");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}
	
}
