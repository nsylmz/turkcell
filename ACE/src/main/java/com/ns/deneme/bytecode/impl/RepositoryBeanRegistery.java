package com.ns.deneme.bytecode.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.data.neo4j.support.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.support.mapping.Neo4jPersistentEntityImpl;
import org.springframework.data.repository.config.RepositoryBeanNameGenerator;
import org.springframework.stereotype.Component;

import com.ns.deneme.appContext.AppContext;
import com.ns.deneme.bytecode.RepositoryBeanDefinitionBuilder;
import com.ns.deneme.bytecode.RepositoryBeanRegisteryI;

@Component
public class RepositoryBeanRegistery implements RepositoryBeanRegisteryI {
	
	@Autowired
	private Neo4jMappingContext mappingContext;
	
	@Override
	public Neo4jPersistentEntityImpl<?>  registerNode(String nodeClassName) throws ClassNotFoundException {
		Class<?> nodeClass = Class.forName(nodeClassName);
		return mappingContext.getPersistentEntity(nodeClass);
	}
	
	@Override
	public void registerRepository(String repositoryInterface) {
		
		BeanDefinitionRegistry registry = ((BeanDefinitionRegistry) AppContext.getFactory());  
		RepositoryBeanDefinitionBuilder definitionBuilder = new RepositoryBeanDefinitionBuilder(repositoryInterface);
		
		BeanDefinitionBuilder builder = definitionBuilder.build(registry, AppContext.getApplicationContext());
		
		AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
		beanDefinition.setSource(null);

		RepositoryBeanNameGenerator generator = new RepositoryBeanNameGenerator();
		generator.setBeanClassLoader(null);
		String beanName = generator.generateBeanName(beanDefinition, registry);
		
		BeanComponentDefinition definition = new BeanComponentDefinition(beanDefinition, beanName);
		BeanDefinitionReaderUtils.registerBeanDefinition(definition, registry);
	}
	
	@Override
	public void registerRepositoryAPI(String repositoryAPI) {
		
		BeanDefinitionRegistry registry = ((BeanDefinitionRegistry) AppContext.getFactory());  
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();  
		beanDefinition.setBeanClassName(repositoryAPI);  
		beanDefinition.setLazyInit(false);  
		beanDefinition.setAbstract(false);  
		beanDefinition.setAutowireCandidate(true);  
		beanDefinition.setScope("singleton");  
		
		RepositoryBeanNameGenerator generator = new RepositoryBeanNameGenerator();
		generator.setBeanClassLoader(null);
		String beanName = StringUtils.uncapitalize(StringUtils.substringAfterLast(repositoryAPI, "."));
		
		BeanComponentDefinition definition = new BeanComponentDefinition(beanDefinition, beanName);
		BeanDefinitionReaderUtils.registerBeanDefinition(definition, registry);
	}

}
