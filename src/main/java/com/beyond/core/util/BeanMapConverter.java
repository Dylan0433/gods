/**
 * BeanMapConverter.java
 */
package com.beyond.core.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.beyond.core.exception.TransformException;

/**map类型与javaBean之间的转换
 * @author Dylan
 * @time 2013-3-16
 */
public class BeanMapConverter {

	
	
	/**
	 * map类型转换成javaBean
	 * @param properties 保存bean属性和值的map
	 * @param beanClass  转换成bean的大型
	 * @return
	 */
	public static <T> T mapToBean(Map<String,Object> properties,Class<T> beanClass){
		T bean = null;
		try {
			bean = beanClass.newInstance();
			BeanUtils.populate(bean, properties);
		} catch (InstantiationException e) {
			throw new TransformException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new TransformException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new TransformException(e.getMessage());
		}
		return bean;
	}
	
	/**
	 * javaBean 转换成 map类型
	 * @param bean 实体bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> beanToMap(Object bean){
		try {
			return BeanUtils.describe(bean);
		} catch (Exception e) {
			throw new TransformException(e.getMessage());
		}
			
	}
}
