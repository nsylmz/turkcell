package com.ns.deneme.appContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

public class ApplicationContextProvider implements ApplicationContextAware {

        public void setApplicationContext(ApplicationContext ctx) throws BeansException {
                AppContext.setApplicationContext(ctx);  
        }

        public void refreshApplicationContext() throws BeansException {
                ((ConfigurableApplicationContext)AppContext.getApplicationContext()).refresh();  
        }
}