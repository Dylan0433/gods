package com.beyond.core.spring;

/**
 * 
 * @author Dylan
 * @time 下午5:44:55
 */
public class SpringBeanRegistry {

	/**
	 * 
	 */
	private ApplicationContextHolder contextHolder = null;

	public ApplicationContextHolder getContextHolder() {
		return contextHolder;
	}

	public void setContextHolder(ApplicationContextHolder contextHolder) {
		this.contextHolder = contextHolder;
	}
	
}
