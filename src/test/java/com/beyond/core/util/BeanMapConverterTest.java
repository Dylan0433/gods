/**
 * BeanMapConverterTest.java
 */
package com.beyond.core.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author Dylan
 * @time 2013-3-16
 */
public class BeanMapConverterTest {

	
	@Test
	public void bean2map(){
		
		Persion p = new Persion();
		p.setAge(33);
		p.setName("张三");
		Map<String,Object> result = BeanMapConverter.beanToMap(p);
		
		for(String key : result.keySet()){
			System.out.println(result.get(key));
		}
	}
	
	@Test
	public void map2bean(){
		
		Map<String,Object> properties = new HashMap<String, Object>();
		properties.put("name", "张三");
		properties.put("age", 23);
		Persion p = BeanMapConverter.mapToBean(properties, Persion.class);
		
		System.out.println(p.getName() + " : " + p.getAge());
	}
	
}


