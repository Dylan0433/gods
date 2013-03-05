package com.beyond.core.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 
 * @author Dylan
 * @time 下午5:44:55
 */
public class SpringBeanRegistry extends ApplicationContextHolder{

	
	

	/**
	 * 
	 * @param clazz
	 */
	public <T> void register(Class<T> clazz){
		
		register(clazz.getSimpleName(), clazz);
	}
	
	/**
	 * 
	 * @param beanName
	 * @param clazz
	 */
	public <T> void register(String beanName,Class<T> clazz){
		
		register(beanName, clazz, "singleton");
	}
	
	
	/**
	 * 
	 * @param clazz
	 * @param propertyValue
	 */
	public <T> void register(Class<T> clazz,Map<String,Object> propertyValue){
		register(clazz.getSimpleName(), clazz, propertyValue);
	}
	
	/**
	 * 
	 * @param beanName
	 * @param clazz
	 * @param scop
	 */
	public <T> void register(String beanName,Class<T> clazz,String scop){
		
		register(beanName, clazz, scop, new HashMap<String, Object>());
	}
	
	
	/**
	 * 
	 * @param beanName
	 * @param clazz
	 * @param propertyValue
	 */
	public <T> void register(String beanName,Class<T> clazz,Map<String,Object> propertyValue){
		register(beanName, clazz, "singleton", propertyValue);
	}
	
	/**
	 * 
	 * @param beanName
	 * @param clazz
	 * @param scope
	 * @param propertyValue
	 */
	public <T> void register(String beanName,Class<T> clazz,String scope,Map<String,Object> propertyValue){
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) getContext().getAutowireCapableBeanFactory();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz).setScope(scope);
		for(String propertyName : propertyValue.keySet()){
			builder.addPropertyValue(propertyName, propertyValue.get(propertyName));
		}
		beanFactory.registerBeanDefinition(beanName, builder.getRawBeanDefinition());
	}
	
	
}
