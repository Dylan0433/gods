package com.beyond.core.spring;

/**
 * 
 * @author Dylan
 * @time 下午4:48:21
 */
public class SpringBeanHolder extends ApplicationContextHolder {

	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> T getBean(Class<T> clazz){
		return getContext().getBean(clazz);
	}
	/**
	 * 
	 * @param beanName
	 * @return
	 */
	public Object getBean(String beanName){
		return getContext().getBean(beanName);
	}
	
}
