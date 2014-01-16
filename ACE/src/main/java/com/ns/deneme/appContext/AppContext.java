package com.ns.deneme.appContext;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;

public class AppContext {

	private static ApplicationContext ctx;
	
	private static ConfigurableListableBeanFactory factory;

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ctx = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	public static ConfigurableListableBeanFactory getFactory() {
		return factory;
	}

	public static void setFactory(ConfigurableListableBeanFactory factory) {
		AppContext.factory = factory;
	}
	
	
}