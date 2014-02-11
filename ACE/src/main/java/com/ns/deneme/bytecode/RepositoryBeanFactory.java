package com.ns.deneme.bytecode;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

import com.ns.deneme.bytecode.impl.RepositoryBeanFactoryI;
import com.ns.deneme.bytecode.util.APIOperationType;
import com.ns.deneme.neo4j.api.ITemplateAPI;
import com.ns.deneme.neo4j.api.impl.TemplateAPI;
import com.ns.deneme.neo4j.domain.TemplateNodeEntity;
import com.ns.deneme.neo4j.repository.TemplateRepository;

@Component
public class RepositoryBeanFactory implements RepositoryBeanFactoryI {
	
	private static Logger logger = LoggerFactory.getLogger(RepositoryBeanFactory.class);

	private static ClassPool pool = ClassPool.getDefault();
	
	private static final String graphRepositoryName = GraphRepository.class.getName();
	
	private static String defaultClassLoader = "target/classes/";
	
	public CtClass cloneNode(String node, List<NodeProperty> nodeProperties) {
		CtClass cc = null;
		try {
			cc = pool.getAndRename(TemplateNodeEntity.class.getName(), node);
			ClassFile ccFile = cc.getClassFile();
			ConstPool constpool = ccFile.getConstPool();
			
			List<NodeAnnotationProperty> annotProperties;
			CtField ctField;
			AnnotationsAttribute anotAttr;
			Annotation annot;
			List<AnnotationMember> annotationMembers;
			AnnotationMemberMethod[] javassistMemberMethods;
			Object javassistMemberObject;
			Method javassistMemberMethod;
			// Add Node Properties
			for (NodeProperty nodeProperty : nodeProperties) {
				ctField = new CtField(ClassPool.getDefault().get(nodeProperty.getPropertyType().getName()), nodeProperty.getPropertyName(), cc);
				ctField.setModifiers(Modifier.PRIVATE);
				
				// Add Field Annnotations
				annotProperties = nodeProperty.getAnnotations();
				for (NodeAnnotationProperty nodeAnnotationProperty : annotProperties) {
					anotAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
					annot = new Annotation(nodeAnnotationProperty.getAnnotationInterface().getName(), constpool);
					anotAttr.addAnnotation(annot);

					// Add Annotation Members
					annotationMembers = nodeAnnotationProperty.getAnnotationMembers();
					for (AnnotationMember annotationMember : annotationMembers) {
						// TODO: Check ArrayMemberValue
						javassistMemberObject = annotationMember.getMemberType().javassistClass.newInstance();
						javassistMemberMethods = annotationMember.getMemberType().methods;
						for (AnnotationMemberMethod method : javassistMemberMethods) {
							javassistMemberMethod = javassistMemberObject.getClass().getMethod(method.getMethodName(), method.getMethodParamClass());
							if (method.getMethodName().equals("setValue")) {
								javassistMemberMethod.invoke(javassistMemberObject, annotationMember.getMemberValue());
							} else if (method.getMethodName().equals("setType")) {
								javassistMemberMethod.invoke(javassistMemberObject, annotationMember.getMemberValue().getClass().getName());
							}
						}
					}
					ctField.getFieldInfo().addAttribute(anotAttr);
				}
				
				// Field Getter And Setter Methods
				CtMethod getterMethod;
				if (nodeProperty.getClass().isAssignableFrom(Boolean.class)) {
					getterMethod = CtNewMethod.getter("is" + StringUtils.capitalize(nodeProperty.getPropertyName()), ctField);
				} else {
					getterMethod = CtNewMethod.getter("get" + StringUtils.capitalize(nodeProperty.getPropertyName()), ctField);
				}
				cc.addMethod(getterMethod);
				
				CtMethod setterMethod = CtNewMethod.setter("set" + StringUtils.capitalize(nodeProperty.getPropertyName()), ctField);
				cc.addMethod(setterMethod);
				
				cc.addField(ctField);
			}
			cc.writeFile(defaultClassLoader);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return cc;
	}
	
	public CtClass cloneRepository(String repositoryInterface, String node) {
		CtClass cc = null;
		try {
			cc = pool.getAndRename(TemplateRepository.class.getName(), repositoryInterface);
			
			StringBuffer genericSignature = new StringBuffer();
			genericSignature.append("Ljava/lang/Object;L").
							 append(graphRepositoryName.replaceAll("[.]", "/")).
							 append("<L").append(node.replaceAll("[.]", "/")).append(";>;");
			cc.setGenericSignature(genericSignature.toString());
			cc.writeFile(defaultClassLoader);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return cc;
	}
	
	public void cloneRepositoryAPI(String repositoryAPI, final CtClass nodeClass, CtClass repositoryClass) {
		try {
			String repositoryApiInterface = generateApiInterfaceNameFromApiName(repositoryAPI);
			CtClass interfaceClass = cloneRepositoryApiInterface(repositoryApiInterface, nodeClass);
			interfaceClass.defrost();
			CtClass cc = pool.getAndRename(TemplateAPI.class.getName(), repositoryAPI);
			cc.setInterfaces(new CtClass[]{interfaceClass});
			
			CtField[] fields = cc.getFields();
			for (CtField ctField : fields) {
				if (ctField.getType().getName().equals(TemplateRepository.class.getName())) {
					ctField.setType(repositoryClass);
				}
			}
			CtMethod[] methods = cc.getDeclaredMethods();
			String templateNodeDescriptor = "L" + TemplateNodeEntity.class.getName().replaceAll("[.]", "/");
			String newNodeDescriptor = "L" + nodeClass.getName().replaceAll("[.]", "/");
			String methodDescriptor;
			String genericSignature;
			String returnType;
			StringBuffer body;
			String localVar;
			for (CtMethod ctMethod : methods) {
				genericSignature = ctMethod.getGenericSignature();
				if (!StringUtils.isEmpty(genericSignature) && genericSignature.contains(templateNodeDescriptor)) {
					ctMethod.setGenericSignature(genericSignature.replaceAll(templateNodeDescriptor, newNodeDescriptor));
				}
				methodDescriptor = ctMethod.getMethodInfo().getDescriptor();
				if (!StringUtils.isEmpty(methodDescriptor) && methodDescriptor.contains(templateNodeDescriptor)) {
					ctMethod.getMethodInfo().setDescriptor(methodDescriptor.replaceAll(templateNodeDescriptor, newNodeDescriptor));
				}
				returnType = ctMethod.getReturnType().getName();
				body = new StringBuffer();
				APIOperationType operationType = APIOperationType.getValue(ctMethod.getName());
				if (operationType != null) {
					if (!StringUtils.isEmpty(returnType) && !returnType.equals("void")) {
						localVar = "local" + StringUtils.substringAfterLast(ctMethod.getReturnType().getName(), ".");
						body.append("{ ").append(ctMethod.getReturnType().getName()).append(" ").append(localVar).append("2;");
						body.append(" try { ");
						body.append(ctMethod.getReturnType().getName()).append(" ").append(localVar).append("1;");
						body.append(" try { ");
						body.append(AnnotationTransactionAspect.class.getName()).append(".aspectOf().ajc$before$org_springframework_transaction_aspectj_AbstractTransactionAspect$1$2a73e96c(this, this.").append(operationType.aspectJVariableName).append("); ");
						if (APIOperationType.FINDBYPROPERTYVALUE.equals(operationType)) {
							body.append(localVar).append("1 = (").append(nodeClass.getName()).append(") repository.").append(operationType.value).append("($1.trim(), $2.trim()); ");
						} else if (APIOperationType.FINDONE.equals(operationType)) {
							body.append(localVar).append("1 = (").append(nodeClass.getName()).append(") repository.").append(operationType.value).append("($1); ");
						} else if (APIOperationType.FINDALL.equals(operationType)) {
							body.append(List.class.getName()).append(" resultList = new ").append(ArrayList.class.getName()).append("(); ");
							body.append(EndResult.class.getName()).append(" resultSet = repository.").append(operationType.value).append("(); ");
							body.append(Iterator.class.getName()).append(" iter = resultSet.iterator(); ");
							body.append("while (iter.hasNext()) { ");
							body.append("resultList.add((").append(nodeClass.getName()).append(") iter.next());");
							body.append(" } ");
							body.append(localVar).append("1 = resultList;");
						}
						body.append(" } catch (").append(Throwable.class.getName()).append(" localThrowable) { ");
						body.append(AnnotationTransactionAspect.class.getName()).append(".aspectOf().ajc$afterThrowing$org_springframework_transaction_aspectj_AbstractTransactionAspect$2$2a73e96c(this, localThrowable);");
						body.append(" throw localThrowable;");
						body.append(" } ");
						body.append(AnnotationTransactionAspect.class.getName()).append(".aspectOf().ajc$afterReturning$org_springframework_transaction_aspectj_AbstractTransactionAspect$3$2a73e96c(this);");
						body.append(localVar).append("2 = ").append(localVar).append("1;");
						body.append(" } catch (").append(Throwable.class.getName()).append(" localThrowable1) { ");
						body.append(AnnotationTransactionAspect.class.getName()).append(".aspectOf().ajc$after$org_springframework_transaction_aspectj_AbstractTransactionAspect$4$2a73e96c(this);");
						body.append(" throw localThrowable1;");
						body.append(" } ");
						body.append(AnnotationTransactionAspect.class.getName()).append(".aspectOf().ajc$after$org_springframework_transaction_aspectj_AbstractTransactionAspect$4$2a73e96c(this);");
						body.append(" return ").append(localVar).append("2; }");
					} else {
						body.append("{ try { ");
						body.append("try { ");
						body.append(AnnotationTransactionAspect.class.getName()).append(".aspectOf().ajc$before$org_springframework_transaction_aspectj_AbstractTransactionAspect$1$2a73e96c(this, this.").append(operationType.aspectJVariableName).append("); ");
						if (APIOperationType.SAVE.equals(operationType)) {
							body.append("repository.save($1);");
						} else if (APIOperationType.DELETE.equals(operationType)) {
							body.append("repository.delete(repository.findOne($1.getId()));");
						} else if (APIOperationType.DELETEBYID.equals(operationType)) {
							body.append("repository.delete(repository.findOne($1));");
						}
						body.append(" } catch (").append(Throwable.class.getName()).append(" localThrowable) { ");
						body.append(AnnotationTransactionAspect.class.getName()).append(".aspectOf().ajc$afterThrowing$org_springframework_transaction_aspectj_AbstractTransactionAspect$2$2a73e96c(this, localThrowable);");
						body.append("throw localThrowable;");
						body.append(" } ");
						body.append(AnnotationTransactionAspect.class.getName()).append(".aspectOf().ajc$afterReturning$org_springframework_transaction_aspectj_AbstractTransactionAspect$3$2a73e96c(this);");
						body.append(" } catch (").append(Throwable.class.getName()).append(" localThrowable1) { ");
						body.append(AnnotationTransactionAspect.class.getName()).append(".aspectOf().ajc$after$org_springframework_transaction_aspectj_AbstractTransactionAspect$4$2a73e96c(this);");
						body.append("throw localThrowable1;");
						body.append(" } ");
						body.append(AnnotationTransactionAspect.class.getName()).append(".aspectOf().ajc$after$org_springframework_transaction_aspectj_AbstractTransactionAspect$4$2a73e96c(this);");
						body.append(" }");
					}
					ctMethod.setBody(body.toString());
				}
			}
			cc.writeFile(defaultClassLoader);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public CtClass cloneRepositoryApiInterface(String repositoryApiInterface, CtClass nodeClass) {
		CtClass cc = null;
		try {
			cc = pool.getAndRename(ITemplateAPI.class.getName(), repositoryApiInterface);
			CtMethod[] declaredMethods = cc.getDeclaredMethods();
			String genericSignature;
			String templateNodeDescriptor = "L" + TemplateNodeEntity.class.getName().replaceAll("[.]", "/");
			String newNodeDescriptor = "L" + nodeClass.getName().replaceAll("[.]", "/");
			String methodDescriptor;
			for (CtMethod repositoryMethod : declaredMethods) {
				genericSignature = repositoryMethod.getGenericSignature();
				if (!StringUtils.isEmpty(genericSignature) && genericSignature.contains(templateNodeDescriptor)) {
					repositoryMethod.setGenericSignature(genericSignature.replaceAll(templateNodeDescriptor, newNodeDescriptor));
				}
				methodDescriptor = repositoryMethod.getMethodInfo().getDescriptor();
				if (!StringUtils.isEmpty(methodDescriptor) && methodDescriptor.contains(templateNodeDescriptor)) {
					repositoryMethod.getMethodInfo().setDescriptor(methodDescriptor.replaceAll(templateNodeDescriptor, newNodeDescriptor));
				}
			}
			cc.writeFile(defaultClassLoader);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return cc;
	}
	
	public void createRepository(String repositoryInterface, String node) {
		try {
			CtClass cc = pool.makeInterface(repositoryInterface);
			cc.setSuperclass(pool.get(graphRepositoryName));
			
			StringBuffer genericSignature = new StringBuffer();
			genericSignature.append("Ljava/lang/Object;L").
							 append(graphRepositoryName.replaceAll("[.]", "/")).
							 append("<L").append(node.replaceAll("[.]", "/")).append(";>;");
			cc.setGenericSignature(genericSignature.toString());
			cc.writeFile(defaultClassLoader);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void createNode(String node) {
		try {
			CtClass cc = pool.makeClass(node);
			cc.addInterface(pool.get(Serializable.class.getName()));
			
			// Add Annotation to Class
			ClassFile ccFile = cc.getClassFile();
			ConstPool constpool = ccFile.getConstPool();
			constpool.addClassInfo(NodeEntity.class.getName());
			
			AnnotationsAttribute anotAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			Annotation annot = new Annotation(NodeEntity.class.getName(), constpool);
			anotAttr.addAnnotation(annot);
			ccFile.addAttribute(anotAttr);
			
			// Add constant field with setter and getter methods to The New Class
			// ID Field
			CtField id = new CtField(ClassPool.getDefault().get(Long.class.getName()), "id", cc);
			id.setModifiers(Modifier.PRIVATE);
			
			// Annotation GraphId
			anotAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			annot = new Annotation(GraphId.class.getName(), constpool);
			anotAttr.addAnnotation(annot);
			id.getFieldInfo().addAttribute(anotAttr);
			
			// Getter And Setter Methods
			CtMethod getterMethod = CtNewMethod.getter("getId", id);
			CtMethod setterMethod = CtNewMethod.setter("setId", id);
			cc.addMethod(getterMethod);
			cc.addMethod(setterMethod);
			
			cc.addField(id);
			
			// Version Field
			CtField version = new CtField(ClassPool.getDefault().get(Integer.class.getName()), "version", cc);
			version.setModifiers(Modifier.PRIVATE);
			
			// Annotation GraphId
			anotAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			annot = new Annotation(Version.class.getName(), constpool);
			anotAttr.addAnnotation(annot);
			version.getFieldInfo().addAttribute(anotAttr);
			
			// Getter And Setter Methods
			getterMethod = CtNewMethod.getter("getVersion", version);
			setterMethod = CtNewMethod.setter("setVersion", version);
			cc.addMethod(getterMethod);
			cc.addMethod(setterMethod);
			
			cc.addField(version);
			
			// Name Field
			CtField name = new CtField(ClassPool.getDefault().get(String.class.getName()), "name", cc);
			name.setModifiers(Modifier.PRIVATE);
			
			// Annotation Indexed
			anotAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			annot = new Annotation(Indexed.class.getName(), constpool);
			anotAttr.addAnnotation(annot);
			name.getFieldInfo().addAttribute(anotAttr);
			
			// Getter And Setter Methods
			getterMethod = CtNewMethod.getter("getName", name);
			setterMethod = CtNewMethod.setter("setName", name);
			cc.addMethod(getterMethod);
			cc.addMethod(setterMethod);
			
			cc.addField(name);
			
			// Create Node Class
			cc.writeFile(defaultClassLoader);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void createRepositoryAPI(String repositoryAPI) {
		
		try {
			String repositoryApiInterface = generateApiInterfaceNameFromApiName(repositoryAPI);
			CtClass interfaceClass = createRepositoryApiInterface(repositoryApiInterface);
			
			CtClass cc = pool.makeClass(repositoryAPI);
			cc.addInterface(interfaceClass);
			
			// Add Annotation to Class
			ClassFile ccFile = cc.getClassFile();
			ConstPool constpool = ccFile.getConstPool();

			// Component Annotation
			AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
			Annotation component = new Annotation(Component.class.getName(), constpool);
			attr.addAnnotation(component);
			ccFile.addAttribute(attr);
			
			// Begin Transactional Annotation 
			Annotation transactional = new Annotation(Transactional.class.getName(), constpool);
			
			BooleanMemberValue readOnly = new BooleanMemberValue(constpool);
			readOnly.setValue(false);
			transactional.addMemberValue("readOnly", readOnly);
			
			EnumMemberValue propagation = new EnumMemberValue(constpool);
			propagation.setType(Propagation.class.getName());
			propagation.setValue(Propagation.REQUIRED.name());
			transactional.addMemberValue("propagation", propagation);
			
			ClassMemberValue emptyResultDataAccessException = new ClassMemberValue(EmptyResultDataAccessException.class.getName(), constpool);
			ArrayMemberValue noRollbackFor = new ArrayMemberValue(constpool);
			noRollbackFor.setValue(new MemberValue[]{emptyResultDataAccessException});
			transactional.addMemberValue("noRollbackFor", noRollbackFor);
			
			ClassMemberValue exception = new ClassMemberValue(Exception.class.getName(), constpool);
			ArrayMemberValue rollbackFor = new ArrayMemberValue(constpool);
			rollbackFor.setValue(new MemberValue[]{exception});
			transactional.addMemberValue("rollbackFor", rollbackFor);
			
			attr.addAnnotation(transactional);
			ccFile.addAttribute(attr);
			// End Of Transactional Annotation 
			
			// Add Repository Field
			String nodeName = generateNodeNameFromRepositoryAPI(repositoryAPI);
			String repositoryName = generateRepositoryNameFromApiInterfaceName(repositoryApiInterface);
			String repositoryFeildName = generateCRUDMethodInputParamName(repositoryName);
			CtField repository = new CtField(ClassPool.getDefault().get(repositoryName), repositoryFeildName, cc);
			repository.setModifiers(Modifier.PRIVATE);
			
//			AnnotationsAttribute attrAutowired = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
//			Annotation autowired = new Annotation(Autowired.class.getName(), constpool);
//			attrAutowired.addAnnotation(autowired);
//			repository.getFieldInfo().addAttribute(attrAutowired);
			
			cc.addField(repository);
			
			// CRUD Methods
			// SAVE
			CtMethod save = CtNewMethod.make(generateMethodSignature(APIOperationType.SAVE.value, repositoryApiInterface), cc);
			save.setBody(repositoryFeildName + "." + APIOperationType.SAVE.value + "($1);");
			cc.addMethod(save);
			
			// DELETE by Object
			CtMethod delete = CtNewMethod.make(generateMethodSignature(APIOperationType.DELETE.value, repositoryApiInterface), cc);
			delete.setBody(repositoryFeildName + "." + APIOperationType.DELETE.value + "($1);");
			cc.addMethod(delete);
			
			/*
			// FINDONE
			CtMethod findOne = CtNewMethod.make(generateMethodSignature(APIOperationType.FINDONE.value, repositoryApiInterface), cc);
			findOne.setBody("return " + repositoryFeildName + "." + APIOperationType.FINDONE.value + "($1);");
			cc.addMethod(findOne);
			
			// FINDBYPROPERTYVALUE
			CtMethod findByPropertyValue = CtNewMethod.make(generateMethodSignature(APIOperationType.FINDBYPROPERTYVALUE.value, repositoryApiInterface), cc);
			findByPropertyValue.setBody("return " + repositoryFeildName + "." + APIOperationType.FINDBYPROPERTYVALUE.value + "(\"name\", $1.trim());");
			cc.addMethod(findByPropertyValue);
			 */
			
			// FINDALL
			String genericMethodSignature = "()Ljava/util/List<L" + nodeName.replace(".", "/") + ";>;";
			StringBuffer body = new StringBuffer();
			body.append("{").append(List.class.getName()).append(" resultList = new ").append(ArrayList.class.getName()).append("();").
				 append(EndResult.class.getName()).append(" resultSet = ").
				 append(repositoryFeildName).append(".").append(APIOperationType.FINDALL.value).append("();").
				 append(Iterator.class.getName()).append(" iter = resultSet.iterator();").
				 append("while (iter.hasNext()) { resultList.add((").append(nodeName).append(") iter.next()); } return resultList; }");
			CtMethod findAll = CtNewMethod.make(generateMethodSignature(APIOperationType.FINDALL.value, repositoryApiInterface), cc);
			findAll.setGenericSignature(genericMethodSignature);
			findAll.setBody(body.toString());
			cc.addMethod(findAll);
			cc.writeFile(defaultClassLoader);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private CtClass createRepositoryApiInterface(String repositoryApiInterface) {
		CtClass cc = null;
		try {
			cc = pool.makeInterface(repositoryApiInterface);
			
			CtMethod save = CtNewMethod.make(generateMethodSignature(APIOperationType.SAVE.value, repositoryApiInterface), cc);
			cc.addMethod(save);
			
			CtMethod delete = CtNewMethod.make(generateMethodSignature(APIOperationType.DELETE.value, repositoryApiInterface), cc);
			cc.addMethod(delete);
			
			/*
			CtMethod findOne = CtNewMethod.make(generateMethodSignature(APIOperationType.FINDONE.value, repositoryApiInterface), cc);
			cc.addMethod(findOne);
			
			CtMethod findByIndexedPropertyValue = CtNewMethod.make(generateMethodSignature(APIOperationType.FINDBYPROPERTYVALUE.value, repositoryApiInterface), cc);
			cc.addMethod(findByIndexedPropertyValue);
			 */
			
			String nodeName = generateNodeNameFromRepositoryAPI(repositoryApiInterface);
			String genericMethodSignature = "()Ljava/util/List<L" + nodeName.replace(".", "/") + ";>;";
			CtMethod findAll = CtNewMethod.make(generateMethodSignature(APIOperationType.FINDALL.value, repositoryApiInterface), cc);
			findAll.setGenericSignature(genericMethodSignature);
			cc.addMethod(findAll);
			
			cc.writeFile(defaultClassLoader);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return cc;
	}
	
	private String generateMethodSignature(String operatinName, String className) {
		StringBuffer str;
		String nodeName;
		if (APIOperationType.FINDONE.value.equals(operatinName)) {
			str = new StringBuffer();
			nodeName = generateNodeNameFromRepositoryAPI(className);
			str.append("public ").append(nodeName).append(" ").append(APIOperationType.FINDONE.value).append("(").
				append(Long.class.getName()).append(" ").append("id").append(");");
		} else if (APIOperationType.FINDALL.value.equals(operatinName)) {
			str = new StringBuffer();
			nodeName = generateNodeNameFromRepositoryAPI(className);
			str.append("public ").append(List.class.getName()).append(" ").append(APIOperationType.FINDALL.value).append("();");
		} else if (APIOperationType.FINDBYPROPERTYVALUE.value.equals(operatinName)) {
			str = new StringBuffer();
			nodeName = generateNodeNameFromRepositoryAPI(className);
			str.append("public ").append(nodeName).append(" ").append(APIOperationType.FINDBYPROPERTYVALUE.value).
				append("(").append(String.class.getName()).append(" name);");
		} else if (APIOperationType.SAVE.value.equals(operatinName) || APIOperationType.DELETE.value.equals(operatinName)) {
			str = new StringBuffer();
			nodeName = generateNodeNameFromRepositoryAPI(className);
			str.append("public void ").append(generateMethodName(operatinName, className)).append("(").
				append(nodeName).append(" ").append(generateCRUDMethodInputParamName(nodeName)).append(");");
		} else {
			return null;
		}
		return str.toString();
	}
	
	private String generateMethodName(String operatinName, String className) {
		String simpleClassName = StringUtils.substringAfterLast(className, ".").replace("API", "");
		if (isClassNameInterface(simpleClassName)) {
			return operatinName + simpleClassName.substring(1);
		} 
		return operatinName + simpleClassName;
	}
	
	private boolean isClassNameInterface(String simpleClassName) {
		if (simpleClassName.startsWith("I") && Character.isUpperCase(simpleClassName.charAt(1))) {
			return true;
		}
		return false;
	}
	
	private String generateNodeNameFromRepositoryAPI(String repositoryApiInterface) {
		String simpleClassName = StringUtils.substringAfterLast(repositoryApiInterface, ".");
		if (isClassNameInterface(simpleClassName)) {
			return StringUtils.substringBefore(repositoryApiInterface, simpleClassName).replace(".api.",".domain.") + simpleClassName.substring(1, simpleClassName.indexOf("API"));
		}
		return StringUtils.substringBefore(repositoryApiInterface, simpleClassName).replace(".api.impl.",".domain.") + simpleClassName.substring(0, simpleClassName.indexOf("API"));
	}
	
	private String generateCRUDMethodInputParamName(String nodeName) {
		return StringUtils.uncapitalize(StringUtils.substringAfterLast(nodeName, "."));
	}
	
	private String generateApiInterfaceNameFromApiName(String repositoryApiName) {
		String simpleClassName = StringUtils.substringAfterLast(repositoryApiName, ".");
		return StringUtils.substringBefore(repositoryApiName, simpleClassName).replace(".api.impl.", ".api.") + "I" + simpleClassName;
	}
	
	private String generateRepositoryNameFromApiInterfaceName(String repositoryApiInterface) {
		return generateNodeNameFromRepositoryAPI(repositoryApiInterface).replace(".domain.", ".repository.") + "Repository";
	}

}
