package com.beyond.core.spring;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 
 * @author Dylan
 * @time 下午5:36:46
 */
public class BeanFactoryHolder implements BeanFactoryAware {

	/**
	 * 
	 */
	private DefaultListableBeanFactory beanFactory = null;
	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		Validate.notNull(beanFactory);
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;

	}
	public DefaultListableBeanFactory getBeanFactory() {
		return beanFactory;
	}
	
}
