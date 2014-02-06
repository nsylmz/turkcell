package com.ns.deneme.bytecode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.data.neo4j.repository.GraphRepositoryFactoryBean;
import org.springframework.data.repository.config.NamedQueriesBeanDefinitionBuilder;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class RepositoryBeanDefinitionBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryBeanDefinitionBuilder.class);

	private String repositoryInterface;
	
	private static final QueryLookupStrategy.Key queryLookupStrategyKey = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND;
	
	private String implementationBeanName;
	
	private String implementationClassName;
	
	private String[] basePackages = new String[]{};
	
	private static final String defaultNamedQueryLocation = "classpath*:META-INF/neo4j-named-queries.properties";
	
	private String namedQueriesLocation = null;
	
	private static final String neo4jTemplate = "neo4jTemplate";
	
	private static final String neo4jMappingContext = "neo4jMappingContext";
	
	private static final String factoryBeanName = GraphRepositoryFactoryBean.class.getName();
	
	public RepositoryBeanDefinitionBuilder(String repositoryInterface) {
		this.repositoryInterface = repositoryInterface;
		String[] repositoryInterfaceParts = repositoryInterface.split("[.]");
		String repositoryInterfaceName = repositoryInterfaceParts[repositoryInterfaceParts.length-1];
		this.implementationBeanName = StringUtils.uncapitalize(repositoryInterfaceName);
		this.implementationClassName = repositoryInterfaceName;
		this.basePackages = new String[]{repositoryInterface.substring(0, repositoryInterface.lastIndexOf("neo4j"))};
	}
	
	public RepositoryBeanDefinitionBuilder(String repositoryInterface, String namedQueriesLocation) {
		this.repositoryInterface = repositoryInterface;
		String[] repositoryInterfaceParts = repositoryInterface.split("[.]");
		String repositoryInterfaceName = repositoryInterfaceParts[repositoryInterfaceParts.length-1];
		this.implementationBeanName = StringUtils.uncapitalize(repositoryInterfaceName);
		this.implementationClassName = repositoryInterfaceName;
		this.basePackages = new String[]{repositoryInterface.substring(0, repositoryInterface.lastIndexOf("neo4j"))};
		this.namedQueriesLocation = namedQueriesLocation;
	}
	
	/**
	 * Builds a new {@link BeanDefinitionBuilder} from the given {@link BeanDefinitionRegistry} and {@link ResourceLoader}
	 * .
	 * 
	 * @param registry must not be {@literal null}.
	 * @param resourceLoader must not be {@literal null}.
	 * @return
	 */
	public BeanDefinitionBuilder build(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {

		Assert.notNull(registry, "BeanDefinitionRegistry must not be null!");
		Assert.notNull(resourceLoader, "ResourceLoader must not be null!");

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(factoryBeanName);

		builder.getRawBeanDefinition().setSource(null);
		builder.addPropertyValue("repositoryInterface", repositoryInterface);
		builder.addPropertyValue("queryLookupStrategyKey", queryLookupStrategyKey);
		builder.addPropertyReference("neo4jTemplate", neo4jTemplate);
		builder.addPropertyReference("neo4jMappingContext", neo4jMappingContext);

		NamedQueriesBeanDefinitionBuilder definitionBuilder = new NamedQueriesBeanDefinitionBuilder(defaultNamedQueryLocation);

		if (StringUtils.hasText(namedQueriesLocation)) {
			definitionBuilder.setLocations(namedQueriesLocation);
		}

		builder.addPropertyValue("namedQueries", definitionBuilder.build(null));

		String customImplementationBeanName = registerCustomImplementation(registry, resourceLoader);

		if (customImplementationBeanName != null) {
			builder.addPropertyReference("customImplementation", customImplementationBeanName);
			builder.addDependsOn(customImplementationBeanName);
		}

		return builder;
	}

	private String registerCustomImplementation(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {

		String beanName = implementationBeanName;

		// Already a bean configured?
		if (registry.containsBeanDefinition(beanName)) {
			return beanName;
		}

		AbstractBeanDefinition beanDefinition = detectCustomImplementation(registry, resourceLoader);

		if (null == beanDefinition) {
			return null;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Registering custom repository implementation: " + implementationBeanName + " "
					+ beanDefinition.getBeanClassName());
		}

		beanDefinition.setSource(null);

		registry.registerBeanDefinition(beanName, beanDefinition);

		return beanName;
	}

	/**
	 * Tries to detect a custom implementation for a repository bean by classpath scanning.
	 * 
	 * @param config
	 * @param parser
	 * @return the {@code AbstractBeanDefinition} of the custom implementation or {@literal null} if none found
	 */
	private AbstractBeanDefinition detectCustomImplementation(BeanDefinitionRegistry registry, ResourceLoader loader) {

		// Build pattern to lookup implementation class
		Pattern pattern = Pattern.compile(".*\\." + implementationClassName);

		// Build classpath scanner and lookup bean definition
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.setResourceLoader(loader);
		provider.addIncludeFilter(new RegexPatternTypeFilter(pattern));

		Set<BeanDefinition> definitions = new HashSet<BeanDefinition>();

		for (String basePackage : basePackages) {
			definitions.addAll(provider.findCandidateComponents(basePackage));
		}

		if (definitions.isEmpty()) {
			return null;
		}

		if (definitions.size() == 1) {
			return (AbstractBeanDefinition) definitions.iterator().next();
		}

		List<String> implementationClassNames = new ArrayList<String>();
		for (BeanDefinition bean : definitions) {
			implementationClassNames.add(bean.getBeanClassName());
		}

		throw new IllegalStateException(String.format(
				"Ambiguous custom implementations detected! Found %s but expected a single implementation!",
				StringUtils.collectionToCommaDelimitedString(implementationClassNames)));
	}
}
