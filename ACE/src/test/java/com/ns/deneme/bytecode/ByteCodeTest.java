package com.ns.deneme.bytecode;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

import javax.persistence.Transient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.aspects.core.NodeBacked;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.support.mapping.Neo4jMappingContext;
import org.springframework.data.repository.config.RepositoryBeanNameGenerator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ns.deneme.appContext.AppContext;
import com.ns.deneme.neo4j.domain.AbstractEntity;
import com.ns.deneme.neo4j.domain.TemplateEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-config.xml")
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
@TransactionConfiguration(defaultRollback=false)
public class ByteCodeTest {
	
	private static Logger logger = LoggerFactory.getLogger(ByteCodeTest.class);
	
	private ClassPool pool;
	
	@Before
	public void before() {
		try {
			pool = ClassPool.getDefault();
			pool.insertClassPath(new ClassClassPath(this.getClass()));
//			prepareNode();
			prepareSimpleNode();
			prepareRepository();
//			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(((BeanDefinitionRegistry) AppContext.getFactory()));
//			reader.loadBeanDefinitions(new UrlResource("file:/C:/Users/ext0183504/git/turkcell/ACE/target/classes/spring/application-config.xml"));
//			prepareFromTemplate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void test() {
		try {
//			GenericBeanDefinition beanDefinition = new GenericBeanDefinition();  
//			beanDefinition.setBeanClassName(pool.get("com.ns.deneme.neo4j.repository.AddressRepository").getName());  
//			beanDefinition.setLazyInit(false);  
//			beanDefinition.setAbstract(false);  
//			beanDefinition.setAutowireCandidate(true);  
//			beanDefinition.setScope("singleton");  
//			registry.registerBeanDefinition("addressRepository", beanDefinition);
			
			Class nodeClass = Class.forName("com.ns.deneme.neo4j.domain.Address");
			Neo4jMappingContext nodeFactory = (Neo4jMappingContext) AppContext.getApplicationContext().getBean(Neo4jMappingContext.class);
			nodeFactory.getPersistentEntity(nodeClass);
			
			BeanDefinitionRegistry registry = ((BeanDefinitionRegistry) AppContext.getFactory());  
			RepositoryBeanDefinitionBuilder definitionBuilder = new RepositoryBeanDefinitionBuilder();
			
			BeanDefinitionBuilder builder = definitionBuilder.build(registry, AppContext.getApplicationContext());
			
			AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
			beanDefinition.setSource(null);

			RepositoryBeanNameGenerator generator = new RepositoryBeanNameGenerator();
			generator.setBeanClassLoader(null);

			String beanName = generator.generateBeanName(beanDefinition, registry);
			
			BeanComponentDefinition definition = new BeanComponentDefinition(beanDefinition, beanName);
			BeanDefinitionReaderUtils.registerBeanDefinition(definition, registry);
			
			GraphRepository entityRep = (GraphRepository) AppContext.getApplicationContext().getBean("addressRepository");
			Class clazz = Class.forName("com.ns.deneme.neo4j.domain.Address");
			Object obj = clazz.newInstance();
			
			Class[] parametersClasses = new Class[]{String.class};
			Method setterMethod = obj.getClass().getMethod("setName", parametersClasses);
			setterMethod.invoke(obj, "deneme");
			
			entityRep.save(obj);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void prepareRepository() { 	
		try {
			CtClass cc = pool.makeInterface("com.ns.deneme.neo4j.repository.AddressRepository");
			cc.setSuperclass(pool.get("org.springframework.data.neo4j.repository.GraphRepository"));
			cc.setGenericSignature("Ljava/lang/Object;Lorg/springframework/data/neo4j/repository/GraphRepository<Lcom/ns/deneme/neo4j/domain/Address;>;");
			cc.writeFile("target/classes/");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void prepareSimpleNode() {
		try {
			CtClass cc = pool.makeClass("com.ns.deneme.neo4j.domain.Address");
			cc.addInterface(pool.get(Serializable.class.getName()));
			cc.setSuperclass(pool.get(AbstractEntity.class.getName()));
			// Add Annotation to Class
			ClassFile ccFile = cc.getClassFile();
			ConstPool constpool = ccFile.getConstPool();
			constpool.addClassInfo(NodeEntity.class.getName());
			
			AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			Annotation annot = new Annotation(NodeEntity.class.getName(), constpool);
			attr.addAnnotation(annot);
			ccFile.addAttribute(attr);
			
			//Add constant field to Class
			// ID
//			CtField id = new CtField(ClassPool.getDefault().get("java.lang.Long"), "id", cc);
//			cc.addField(id);
//			id.setModifiers(Modifier.PRIVATE);
//			
//			// Annotation GraphId
//			AnnotationsAttribute attrId = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
//			annot = new Annotation(GraphId.class.getName(), constpool);
//			attrId.addAnnotation(annot);
//			id.getFieldInfo().addAttribute(attrId);
//			
//			CtMethod getterId = CtNewMethod.getter("getId", id);
//			CtMethod setterId = CtNewMethod.setter("setId", id);
//			cc.addMethod(getterId);
//			cc.addMethod(setterId);
			
			CtField name = new CtField(ClassPool.getDefault().get("java.lang.String"), "name", cc);
			name.setModifiers(Modifier.PRIVATE);
			cc.addField(name);
			
			// Annotation GraphId
			AnnotationsAttribute attrIndex = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			annot = new Annotation(Indexed.class.getName(), constpool);
			attrIndex.addAnnotation(annot);
			name.getFieldInfo().addAttribute(attrIndex);
			
			CtMethod getterName = CtNewMethod.getter("getName", name);
			CtMethod setterName = CtNewMethod.setter("setName", name);
			cc.addMethod(getterName);
			cc.addMethod(setterName);
			
			cc.writeFile("target/classes/");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void prepareAspectNode() {
		try {
			CtClass cc = pool.makeClass("com.ns.deneme.neo4j.domain.Address");
//			CtConstructor ctc = new CtConstructor(new CtClass[0], cc);
//			cc.addConstructor(ctc);
			cc.addInterface(pool.get(Serializable.class.getName()));
			cc.addInterface(pool.get(NodeBacked.class.getName()));
			// Add Annotation to Class
			ClassFile ccFile = cc.getClassFile();
			ConstPool constpool = ccFile.getConstPool();
			constpool.addClassInfo(NodeEntity.class.getName());
			
			AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			Annotation annot = new Annotation(NodeEntity.class.getName(), constpool);
			attr.addAnnotation(annot);
			ccFile.addAttribute(attr);
			
//			annot = new Annotation(Table.class.getName(), constpool);
//			annot.addMemberValue("name", new StringMemberValue("user",ccFile.getConstPool()));
//			attr.addAnnotation(annot);
//			ccFile.addAttribute(attr);
			
			//Add constant field to Class
//			Map<String, Integer[]> mapMethodLineNumbers = new HashMap<>();
			// ID
			CtField id = new CtField(ClassPool.getDefault().get("java.lang.Long"), "id", cc);
			cc.addField(id);
			id.setModifiers(Modifier.PRIVATE);
			
			// Annotation GraphId
			AnnotationsAttribute attrId = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			annot = new Annotation(GraphId.class.getName(), constpool);
			attrId.addAnnotation(annot);
			id.getFieldInfo().addAttribute(attrId);
			
			CtField name = new CtField(ClassPool.getDefault().get("java.lang.String"), "name", cc);
			name.setModifiers(Modifier.PRIVATE);
			cc.addField(name);
			
			// Annotation GraphId
			AnnotationsAttribute attrIndex = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			annot = new Annotation(Indexed.class.getName(), constpool);
			attrIndex.addAnnotation(annot);
			name.getFieldInfo().addAttribute(attrIndex);
			
			// Add setter and getter method to Class
//			CtMethod setterId = CtNewMethod.setter("setId", id);
//			CtMethod getterId = CtNewMethod.getter("getId", id);
//			cc.addMethod(getterId);
//			cc.addMethod(setterId);
//			mapMethodLineNumbers.put(id.getName(), new Integer[]{getterId.getMethodInfo().getLineNumber(0), setterId.getMethodInfo().getLineNumber(0)});
			
			/*
			// City
			CtField city = new CtField(ClassPool.getDefault().get("java.lang.String"), "city", cc);
			cc.addField(city);
			city.setModifiers(Modifier.PRIVATE);
			
			// Add setter and getter method to Class
			CtMethod setterCity = CtNewMethod.setter("setCity", city);
			CtMethod getterCity = CtNewMethod.getter("getCity", city);
			cc.addMethod(getterCity);
			cc.addMethod(setterCity);
			mapMethodLineNumbers.put(city.getName(), new Integer[]{getterCity.getMethodInfo().getLineNumber(0), setterCity.getMethodInfo().getLineNumber(0)});
			
			CtField country = new CtField(ClassPool.getDefault().get("java.lang.String"), "country", cc);
			cc.addField(country);
			country.setModifiers(Modifier.PRIVATE);
			
			// Add setter and getter method to Class
			CtMethod setterCountry = CtNewMethod.setter("setCountry", country);
			CtMethod getterCountry = CtNewMethod.getter("getCountry", country);
			cc.addMethod(getterCountry);
			cc.addMethod(setterCountry);
			mapMethodLineNumbers.put(country.getName(), new Integer[]{getterCountry.getMethodInfo().getLineNumber(0), setterCountry.getMethodInfo().getLineNumber(0)});
			
			CtField street = new CtField(ClassPool.getDefault().get("java.lang.String"), "street", cc);
			cc.addField(street);
			street.setModifiers(Modifier.PRIVATE);
			
			// Add setter and getter method to Class
			CtMethod setterStreet = CtNewMethod.setter("setStreet", street);
			CtMethod getterStreet = CtNewMethod.getter("getStreet", street);
			cc.addMethod(getterStreet);
			cc.addMethod(setterStreet);
			mapMethodLineNumbers.put(street.getName(), new Integer[]{getterStreet.getMethodInfo().getLineNumber(0), setterStreet.getMethodInfo().getLineNumber(0)});
			
			CtField postCode = new CtField(CtClass.intType, "postCode", cc);
			cc.addField(postCode);
			postCode.setModifiers(Modifier.PRIVATE);
			
			// Add setter and getter method to Class
			CtMethod setterPostCode = CtNewMethod.setter("setPostCode", postCode);
			CtMethod getterPostCode = CtNewMethod.getter("getPostCode", postCode);
			cc.addMethod(getterPostCode);
			cc.addMethod(setterPostCode);
			mapMethodLineNumbers.put(postCode.getName(), new Integer[]{getterPostCode.getMethodInfo().getLineNumber(0), setterPostCode.getMethodInfo().getLineNumber(0)});
			*/
			// Add Compiled Aspectj Codes
			CtMethod preClinit = CtNewMethod.make("private static void ajc$preClinit();", cc);
			String classJavaName = "Address.java";
			String className = "com.ns.deneme.neo4j.domain.Address.class";
			
			// Create to aspectj fields for each constant filed and prepare aspectj factory string
			CtField[] fields = cc.getDeclaredFields();
			StringBuilder str = new StringBuilder();
			CtField ajc;
			String tempAjcMethodName = "ajc$tjp_";
			int ajcMethodNumber = 0;
			String methodName;
			Map<Integer, String> fieldMethodMap = new HashMap<Integer, String>();
			fieldMethodMap.put(0, "field-get");
			fieldMethodMap.put(1, "field-set");
			for (CtField ctField : fields) {
				for (int i = 0; i < 2; i++) {
					methodName = tempAjcMethodName + ajcMethodNumber;
					ajc = new CtField(ClassPool.getDefault().get("org.aspectj.lang.JoinPoint$StaticPart"), methodName, cc);
					ajc.setModifiers(Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL);
					cc.addField(ajc);
					str.append(methodName).append(" = localFactory.makeSJP(\"").
						append(fieldMethodMap.get(i)).append("\", localFactory.makeFieldSig(\"").append(Modifier.PRIVATE).
						append("\", \"").append(ctField.getName()).append("\", \"").append(cc.getName()).append("\", \"").
						append(ctField.getType().getName()).append("\"), ").append(-1).append("); ");
					ajcMethodNumber++;
				}
			}
			preClinit.setBody("{ org.aspectj.runtime.reflect.Factory localFactory = new org.aspectj.runtime.reflect.Factory(\"" + classJavaName + "\", " + className +  "); " + str.toString() + " }");
			cc.addMethod(preClinit);
			
			CtField entityState = new CtField(ClassPool.getDefault().get("org.springframework.data.neo4j.core.EntityState"), "entityState", cc);
			entityState.setModifiers(Modifier.PUBLIC | Modifier.TRANSIENT);
			entityState.setGenericSignature("Lorg/springframework/data/neo4j/core/EntityState<Lorg/neo4j/graphdb/Node;>;");
			
			AnnotationsAttribute attrTransient = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			annot = new Annotation(Transient.class.getName(), constpool);
			attrTransient.addAnnotation(annot);
			entityState.getFieldInfo().addAttribute(attrTransient);
			cc.addField(entityState);
			
			CtMethod getNodeId = CtNewMethod.make("public Long getNodeId();", cc);
			getNodeId.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$getNodeId(this); }");
			cc.addMethod(getNodeId);
			
			CtMethod setPersistentState = CtNewMethod.make("public void setPersistentState(org.neo4j.graphdb.Node paramNode);", cc);
			setPersistentState.setBody("{ org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$setPersistentState(this, $1); }");
			cc.addMethod(setPersistentState);
			
			CtMethod getPersistentState = CtNewMethod.make("public org.neo4j.graphdb.Node getPersistentState();", cc);
			getPersistentState.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$getPersistentState(this); }");
			cc.addMethod(getPersistentState);
			
			CtMethod getRelationshipTo = CtNewMethod.make("public org.springframework.data.neo4j.aspects.core.RelationshipBacked getRelationshipTo(org.springframework.data.neo4j.aspects.core.NodeBacked paramNodeBacked, Class paramClass, String paramString);", cc);
			getRelationshipTo.setGenericSignature("<R::Lorg/springframework/data/neo4j/aspects/core/RelationshipBacked;>(Lorg/springframework/data/neo4j/aspects/core/NodeBacked;Ljava/lang/Class<TR;>;Ljava/lang/String;)TR;");
			getRelationshipTo.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$getRelationshipTo(this, $1, $2, $3); }");
			cc.addMethod(getRelationshipTo);
			
			CtMethod getRelationshipToRelationship = CtNewMethod.make("public org.neo4j.graphdb.Relationship getRelationshipTo(org.springframework.data.neo4j.aspects.core.NodeBacked paramNodeBacked, String paramString);", cc);
			getRelationshipToRelationship.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$getRelationshipTo(this, $1, $2); }");
			cc.addMethod(getRelationshipToRelationship);
			
			CtMethod getTemplate = CtNewMethod.make("public org.springframework.data.neo4j.support.Neo4jTemplate getTemplate();", cc);
			getTemplate.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$getTemplate(this); }");
			cc.addMethod(getTemplate);
			
			CtMethod hasPersistentState = CtNewMethod.make("public boolean hasPersistentState();", cc);
			hasPersistentState.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$hasPersistentState(this); }");
			cc.addMethod(hasPersistentState);
			
			CtMethod equals = CtNewMethod.make("public boolean equals(Object paramObject);", cc);
			equals.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$equals(this, $1); }");
			cc.addMethod(equals);
			
			CtMethod hashCode = CtNewMethod.make("public int hashCode();", cc);
			hashCode.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$hashCode(this); }");
			cc.addMethod(hashCode);

			CtMethod persist = CtNewMethod.make("public org.springframework.data.neo4j.aspects.core.NodeBacked persist();", cc);
			persist.setGenericSignature("<T::Lorg/springframework/data/neo4j/aspects/core/NodeBacked;>()TT;");
			persist.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$persist(this); }");
			cc.addMethod(persist);
			
			CtMethod projectTo = CtNewMethod.make("public org.springframework.data.neo4j.aspects.core.NodeBacked projectTo(Class paramClass);", cc);
			projectTo.setGenericSignature("<T::Lorg/springframework/data/neo4j/aspects/core/NodeBacked;>(Ljava/lang/Class<TT;>;)TT;");
			projectTo.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$projectTo(this, $1); }");
			cc.addMethod(projectTo);
			
			CtMethod removeRelationshipTo = CtNewMethod.make("public void removeRelationshipTo(org.springframework.data.neo4j.aspects.core.NodeBacked paramNodeBacked, String paramString);", cc);
			removeRelationshipTo.setBody("{ org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$removeRelationshipTo(this, $1, $2); }");
			cc.addMethod(removeRelationshipTo);
			
			CtMethod interFieldGet = CtNewMethod.make("public org.springframework.data.neo4j.core.EntityState ajc$interFieldGet$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$entityState();", cc);
			interFieldGet.setBody("{ return this.entityState; }");
			cc.addMethod(interFieldGet);
			
			CtMethod interFieldSet = CtNewMethod.make("public void ajc$interFieldSet$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$entityState(org.springframework.data.neo4j.core.EntityState paramEntityState);", cc);
			interFieldSet.setBody("{ this.entityState = $1; }");
			cc.addMethod(interFieldSet);
			
			CtMethod remove = CtNewMethod.make("public void remove();", cc);
			remove.setBody("{ org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$remove(this); }");
			cc.addMethod(remove);
			
			CtMethod relateTo1 = CtNewMethod.make("public org.neo4j.graphdb.Relationship relateTo(org.springframework.data.neo4j.aspects.core.NodeBacked paramNodeBacked, String paramString, boolean paramBoolean);", cc);
			relateTo1.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$relateTo(this, $1, $2, $3); }");
			cc.addMethod(relateTo1);
			
			CtMethod relateTo2 = CtNewMethod.make("public org.neo4j.graphdb.Relationship relateTo(org.springframework.data.neo4j.aspects.core.NodeBacked paramNodeBacked, String paramString);", cc);
			relateTo2.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$relateTo(this, $1, $2); }");
			cc.addMethod(relateTo2);
			
			CtMethod relateTo3 = CtNewMethod.make("public org.springframework.data.neo4j.aspects.core.RelationshipBacked relateTo(org.springframework.data.neo4j.aspects.core.NodeBacked paramN, Class paramClass, String paramString, boolean paramBoolean);", cc);
			relateTo3.setGenericSignature("<R::Lorg/springframework/data/neo4j/aspects/core/RelationshipBacked;N::Lorg/springframework/data/neo4j/aspects/core/NodeBacked;>(TN;Ljava/lang/Class<TR;>;Ljava/lang/String;Z)TR;");
			relateTo3.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$relateTo(this, $1, $2, $3, $4); }");
			cc.addMethod(relateTo3);
			
			CtMethod relateTo4 = CtNewMethod.make("public org.springframework.data.neo4j.aspects.core.RelationshipBacked relateTo(org.springframework.data.neo4j.aspects.core.NodeBacked paramN, Class paramClass, String paramString);", cc);
			relateTo4.setGenericSignature("<R::Lorg/springframework/data/neo4j/aspects/core/RelationshipBacked;N::Lorg/springframework/data/neo4j/aspects/core/NodeBacked;>(TN;Ljava/lang/Class<TR;>;Ljava/lang/String;)TR;");
			relateTo4.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$relateTo(this, $1, $2, $3); }");
			cc.addMethod(relateTo4);
			
			CtMethod findAllByQuery1 = CtNewMethod.make("public java.lang.Iterable findAllByQuery(String paramString, Class paramClass, java.util.Map paramMap);", cc);
			findAllByQuery1.setGenericSignature("<T:Ljava/lang/Object;>(Ljava/lang/String;Ljava/lang/Class<TT;>;Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;)Ljava/lang/Iterable<TT;>;");
			findAllByQuery1.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$findAllByQuery(this, $1, $2, $3); }");
			cc.addMethod(findAllByQuery1);
			
			CtMethod findAllByQuery2 = CtNewMethod.make("public java.lang.Iterable findAllByQuery(String paramString, java.util.Map paramMap);", cc);
			findAllByQuery2.setGenericSignature("(Ljava/lang/String;Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;)Ljava/lang/Iterable<Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;>;");
			findAllByQuery2.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$findAllByQuery(this, $1, $2); }");
			cc.addMethod(findAllByQuery2);
			
			CtMethod findAllByTraversal = CtNewMethod.make("public java.lang.Iterable findAllByTraversal(Class paramClass, org.neo4j.graphdb.traversal.TraversalDescription paramTraversalDescription);", cc);
			findAllByTraversal.setGenericSignature("<T:Ljava/lang/Object;>(Ljava/lang/Class<TT;>;Lorg/neo4j/graphdb/traversal/TraversalDescription;)Ljava/lang/Iterable<TT;>;");
			findAllByTraversal.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$findAllByTraversal(this, $1, $2); }");
			cc.addMethod(findAllByTraversal);
			
			CtMethod findAllPathsByTraversal = CtNewMethod.make("public java.lang.Iterable findAllPathsByTraversal(org.neo4j.graphdb.traversal.TraversalDescription paramTraversalDescription);", cc);
			findAllPathsByTraversal.setGenericSignature("<S::Lorg/springframework/data/neo4j/aspects/core/NodeBacked;E::Lorg/springframework/data/neo4j/aspects/core/NodeBacked;>(Lorg/neo4j/graphdb/traversal/TraversalDescription;)Ljava/lang/Iterable<Lorg/springframework/data/neo4j/core/EntityPath<TS;TE;>;>;");
			findAllPathsByTraversal.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$findAllPathsByTraversal(this, $1); }");
			cc.addMethod(findAllPathsByTraversal);
			
			CtMethod findByQuery = CtNewMethod.make("public java.lang.Object findByQuery(String paramString, Class paramClass, java.util.Map paramMap);", cc);
			findByQuery.setGenericSignature("<T:Ljava/lang/Object;>(Ljava/lang/String;Ljava/lang/Class<TT;>;Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;)TT;");
			findByQuery.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$findByQuery(this, $1, $2, $3); }");
			cc.addMethod(findByQuery);
			
			CtMethod getEntityState = CtNewMethod.make("public org.springframework.data.neo4j.core.EntityState getEntityState();", cc);
			getEntityState.setGenericSignature("()Lorg/springframework/data/neo4j/core/EntityState<Lorg/neo4j/graphdb/Node;>;");
			getEntityState.setBody("{ return org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$getEntityState(this); }");
			cc.addMethod(getEntityState);
			
			CtMethod id_aroundBody0 = CtNewMethod.make("private static final java.lang.Long id_aroundBody0(com.ns.deneme.neo4j.domain.Address paramAddress1, com.ns.deneme.neo4j.domain.Address paramAddress2, org.aspectj.lang.JoinPoint paramJoinPoint);", cc);
			id_aroundBody0.setBody("{ return $2.id; }");
			cc.addMethod(id_aroundBody0);
			
			CtMethod id_aroundBody2 = CtNewMethod.make("private static final void id_aroundBody2(com.ns.deneme.neo4j.domain.Address paramAddress1, com.ns.deneme.neo4j.domain.Address paramAddress2, java.lang.Long paramLong, org.aspectj.lang.JoinPoint paramJoinPoint);", cc);
			id_aroundBody2.setBody("{ $2.id = $3; }");
			cc.addMethod(id_aroundBody2);
			
			CtMethod id_aroundBody1 = CtNewMethod.make("private static final Object id_aroundBody1$advice(com.ns.deneme.neo4j.domain.Address ajc$this, " +		//1
																										 "com.ns.deneme.neo4j.domain.Address target, " +		//2
																										 "org.aspectj.lang.JoinPoint id, " +					//3
																										 "org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking ajc$aspectInstance, " + //4
																										 "org.springframework.data.neo4j.aspects.core.NodeBacked entity, " +						   //5
																										 "org.aspectj.runtime.internal.AroundClosure ajc$aroundClosure, " +							   //6 
																										 "org.aspectj.lang.JoinPoint thisJoinPoint);", cc);											   //7
			id_aroundBody1.setBody("{ if (org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interFieldGetDispatch$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$entityState($5) == null) { " +
										  "org.aspectj.runtime.internal.AroundClosure localAroundClosure1 = $6;" +
										  "org.springframework.data.neo4j.aspects.core.NodeBacked localNodeBacked1 = $5;" +
										  "return id_aroundBody0((com.ns.deneme.neo4j.domain.Address)localNodeBacked1, $2, $3);" +
			    					 "}" +
			    					 "java.lang.Object result = org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interFieldGetDispatch$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$entityState($5).getValue(org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$inlineAccessMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$field($4, $7), null);" +
			    					 "if ((result instanceof org.springframework.data.neo4j.support.DoReturn)) { " +
			    						  "return org.springframework.data.neo4j.support.DoReturn.unwrap(result);" +
			    					 "}" +
			    					 "org.aspectj.runtime.internal.AroundClosure localAroundClosure2 = $6;" +
			    					 "org.springframework.data.neo4j.aspects.core.NodeBacked localNodeBacked2 = $5;" +
			    					 "return id_aroundBody0((com.ns.deneme.neo4j.domain.Address)localNodeBacked2, $2, $3);" +
			  					   "}");
			cc.addMethod(id_aroundBody1);
			
			
			CtMethod id_aroundBody3 = CtNewMethod.make("private static final java.lang.Object id_aroundBody3$advice(com.ns.deneme.neo4j.domain.Address ajc$this, " +												//1
																												   "com.ns.deneme.neo4j.domain.Address target, " +													//2
																												   "java.lang.Long id, " +																			//3
																												   "org.aspectj.lang.JoinPoint thisJoinPoint, " +													//4
																												   "org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking ajc$aspectInstance, " +	//5
																												   "org.springframework.data.neo4j.aspects.core.NodeBacked entity, " +								//6
																												   "java.lang.Object newVal, " +																	//7
																												   "org.aspectj.runtime.internal.AroundClosure ajc$aroundClosure, " +								//8
																												   "org.aspectj.lang.JoinPoint thisJoinPoint);", cc);												//9
			id_aroundBody3.setBody("{ if (org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interFieldGetDispatch$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$entityState($6) == null) { " +
										 "org.aspectj.runtime.internal.AroundClosure localAroundClosure1 = $8;" +
										 "java.lang.Object localObject1 = $7;" +
										 "org.springframework.data.neo4j.aspects.core.NodeBacked localNodeBacked1 = $6;" +
										 "id_aroundBody2((com.ns.deneme.neo4j.domain.Address)localNodeBacked1, $2, (java.lang.Long)localObject1, $9);" +
										 "return null;" +
			    					 "}" +
			    					 "java.lang.Object result = org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interFieldGetDispatch$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$entityState($6).setValue(org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$inlineAccessMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$field($5, $9), $7, null);" +
			    					 "if ((result instanceof org.springframework.data.neo4j.support.DoReturn)) {" +
			    					 	"return org.springframework.data.neo4j.support.DoReturn.unwrap(result);" +
			    					 "}" +
			    					 "org.aspectj.runtime.internal.AroundClosure localAroundClosure2 = $8;" +
			    					 "java.lang.Object localObject2 = result;" +
			    					 "org.springframework.data.neo4j.aspects.core.NodeBacked localNodeBacked2 = $6;" +
			    					 "id_aroundBody2((com.ns.deneme.neo4j.domain.Address)localNodeBacked2,  $2, (java.lang.Long)localObject2, $9);" +
			    					 "return null;" +
								  "}");
			cc.addMethod(id_aroundBody3);
			
			CtMethod getId = CtNewMethod.make("public java.lang.Long getId();", cc);
			getId.setBody("{ com.ns.deneme.neo4j.domain.Address localAddress = this;" +
					"org.aspectj.lang.JoinPoint localJoinPoint = org.aspectj.runtime.reflect.Factory.makeJP(ajc$tjp_0, this, localAddress);" +
					"return (java.lang.Long)id_aroundBody1$advice(this, localAddress, localJoinPoint, org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.aspectOf(), this, null, localJoinPoint); }");
			cc.addMethod(getId);
			
			CtMethod setId = CtNewMethod.make("public void setId(java.lang.Long id);", cc);
			setId.setBody("{ java.lang.Long localLong = id;" +
					"com.ns.deneme.neo4j.domain.Address localAddress = this;" +
					"org.aspectj.lang.JoinPoint localJoinPoint = org.aspectj.runtime.reflect.Factory.makeJP(ajc$tjp_1, this, localAddress, localLong);" +
					"id_aroundBody3$advice(this, localAddress, localLong, localJoinPoint, org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.aspectOf(), this, localLong, null, localJoinPoint); }");
			cc.addMethod(setId);
			
			
			
			
			
			CtMethod name_aroundBody0 = CtNewMethod.make("private static final java.lang.String name_aroundBody0(com.ns.deneme.neo4j.domain.Address paramAddress1, com.ns.deneme.neo4j.domain.Address paramAddress2, org.aspectj.lang.JoinPoint paramJoinPoint);", cc);
			name_aroundBody0.setBody("{ return $2.name; }");
			cc.addMethod(name_aroundBody0);
			
			CtMethod name_aroundBody2 = CtNewMethod.make("private static final void name_aroundBody2(com.ns.deneme.neo4j.domain.Address paramAddress1, com.ns.deneme.neo4j.domain.Address paramAddress2, java.lang.String paramString, org.aspectj.lang.JoinPoint paramJoinPoint);", cc);
			name_aroundBody2.setBody("{ $2.name = $3; }");
			cc.addMethod(name_aroundBody2);
			
			CtMethod name_aroundBody1 = CtNewMethod.make("private static final Object name_aroundBody1$advice(com.ns.deneme.neo4j.domain.Address ajc$this, " +		//1
																										 "com.ns.deneme.neo4j.domain.Address target, " +		//2
																										 "org.aspectj.lang.JoinPoint name, " +					//3
																										 "org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking ajc$aspectInstance, " + //4
																										 "org.springframework.data.neo4j.aspects.core.NodeBacked entity, " +						   //5
																										 "org.aspectj.runtime.internal.AroundClosure ajc$aroundClosure, " +							   //6 
																										 "org.aspectj.lang.JoinPoint thisJoinPoint);", cc);											   //7
			name_aroundBody1.setBody("{ if (org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interFieldGetDispatch$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$entityState($5) == null) { " +
										  "org.aspectj.runtime.internal.AroundClosure localAroundClosure1 = $6;" +
										  "org.springframework.data.neo4j.aspects.core.NodeBacked localNodeBacked1 = $5;" +
										  "return name_aroundBody0((com.ns.deneme.neo4j.domain.Address)localNodeBacked1, $2, $3);" +
			    					 "}" +
			    					 "java.lang.Object result = org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interFieldGetDispatch$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$entityState($5).getValue(org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$inlineAccessMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$field($4, $7), null);" +
			    					 "if ((result instanceof org.springframework.data.neo4j.support.DoReturn)) { " +
			    						  "return org.springframework.data.neo4j.support.DoReturn.unwrap(result);" +
			    					 "}" +
			    					 "org.aspectj.runtime.internal.AroundClosure localAroundClosure2 = $6;" +
			    					 "org.springframework.data.neo4j.aspects.core.NodeBacked localNodeBacked2 = $5;" +
			    					 "return name_aroundBody0((com.ns.deneme.neo4j.domain.Address)localNodeBacked2, $2, $3);" +
			  					   "}");
			cc.addMethod(name_aroundBody1);
			
			
			CtMethod name_aroundBody3 = CtNewMethod.make("private static final java.lang.Object name_aroundBody3$advice(com.ns.deneme.neo4j.domain.Address ajc$this, " +												//1
																												   "com.ns.deneme.neo4j.domain.Address target, " +													//2
																												   "java.lang.String name, " +																			//3
																												   "org.aspectj.lang.JoinPoint thisJoinPoint, " +													//4
																												   "org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking ajc$aspectInstance, " +	//5
																												   "org.springframework.data.neo4j.aspects.core.NodeBacked entity, " +								//6
																												   "java.lang.Object newVal, " +																	//7
																												   "org.aspectj.runtime.internal.AroundClosure ajc$aroundClosure, " +								//8
																												   "org.aspectj.lang.JoinPoint thisJoinPoint);", cc);												//9
			name_aroundBody3.setBody("{ if (org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interFieldGetDispatch$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$entityState($6) == null) { " +
										 "org.aspectj.runtime.internal.AroundClosure localAroundClosure1 = $8;" +
										 "java.lang.Object localObject1 = $7;" +
										 "org.springframework.data.neo4j.aspects.core.NodeBacked localNodeBacked1 = $6;" +
										 "name_aroundBody2((com.ns.deneme.neo4j.domain.Address)localNodeBacked1, $2, (java.lang.String)localObject1, $9);" +
										 "return null;" +
			    					 "}" +
			    					 "java.lang.Object result = org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interFieldGetDispatch$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$entityState($6).setValue(org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$inlineAccessMethod$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$field($5, $9), $7, null);" +
			    					 "if ((result instanceof org.springframework.data.neo4j.support.DoReturn)) {" +
			    					 	"return org.springframework.data.neo4j.support.DoReturn.unwrap(result);" +
			    					 "}" +
			    					 "org.aspectj.runtime.internal.AroundClosure localAroundClosure2 = $8;" +
			    					 "java.lang.Object localObject2 = result;" +
			    					 "org.springframework.data.neo4j.aspects.core.NodeBacked localNodeBacked2 = $6;" +
			    					 "name_aroundBody2((com.ns.deneme.neo4j.domain.Address)localNodeBacked2,  $2, (java.lang.String)localObject2, $9);" +
			    					 "return null;" +
								  "}");
			cc.addMethod(name_aroundBody3);
			
			CtMethod getName = CtNewMethod.make("public java.lang.String getName();", cc);
			getName.setBody("{ com.ns.deneme.neo4j.domain.Address localAddress = this;" +
					"org.aspectj.lang.JoinPoint localJoinPoint = org.aspectj.runtime.reflect.Factory.makeJP(ajc$tjp_2, this, localAddress);" +
					"return (java.lang.String)name_aroundBody1$advice(this, localAddress, localJoinPoint, org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.aspectOf(), this, null, localJoinPoint); }");
			cc.addMethod(getName);
			
			CtMethod setName = CtNewMethod.make("public void setName(java.lang.String name);", cc);
			setName.setBody("{ java.lang.String localString = $1;" +
					"com.ns.deneme.neo4j.domain.Address localAddress = this;" +
					"org.aspectj.lang.JoinPoint localJoinPoint = org.aspectj.runtime.reflect.Factory.makeJP(ajc$tjp_3, this, localAddress, localString);" +
					"name_aroundBody3$advice(this, localAddress, localString, localJoinPoint, org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.aspectOf(), this, localString, null, localJoinPoint); }");
			cc.addMethod(setName);
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			CtConstructor constructor = new CtConstructor(new CtClass[]{}, cc);
			constructor.setBody("{ org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$interFieldInit$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$org_springframework_data_neo4j_aspects_core_NodeBacked$entityState(this);" +
								"  if (!org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.ajc$cflowCounter$0.isValid()) { " +
									  "org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking.aspectOf().ajc$before$org_springframework_data_neo4j_aspects_support_node_Neo4jNodeBacking$1$74591ff9(this);" +
								"  } " +
								"}");
			cc.addConstructor(constructor);
			
			cc.writeFile("target/classes/");
			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(((BeanDefinitionRegistry) AppContext.getFactory()));
			reader.loadBeanDefinitions(new UrlResource("file:/C:/Users/ext0183504/git/turkcell/ACE/target/classes/spring/application-config.xml"));
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
