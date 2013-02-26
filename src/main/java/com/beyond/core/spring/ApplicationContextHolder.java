package com.beyond.core.spring;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * hand this bean to spring,and when spring start,auto set the context to this bean
 * @author Dylan
 * @time 下午4:36:08
 */
public class ApplicationContextHolder implements ApplicationContextAware {

	private ApplicationContext context = null;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		Validate.notNull(context);
		this.context = context;
	}

	public ApplicationContext getContext() {
		return context;
	}
	
}
