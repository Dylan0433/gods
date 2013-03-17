package com.beyond.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.type.Alias;
import org.junit.Test;
import org.slf4j.Logger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class DocumentConverterTest {

	private final static Logger LOG = LogFactory.createLogger(DocumentConverterTest.class);
	@Test
	public void test(){
		
		Father u1 = Father.createUser("张三", "1234");
		
		String xml = DocumentConverter.toXML(u1);
		
		Father u2 = Father.createUser("李四", "5678");
		
		String json = DocumentConverter.toJson(u2);
		
	//	String x = "{\"User\":{\"name\":\"张三\",\"password\":1234}}";
		
		u2 = DocumentConverter.xmlToEntity(xml, Father.class);
		
		u1 = DocumentConverter.jsonToEntity(json, Father.class);
		
		LOG.info(xml);
		
		LOG.info(json);
		
		
	}
	
	@Test
	public void test2(){
		
		Father f = new Father("张三", "123");
		
		Son s1 = new Son("张四", "456");
		Son s2 = new Son("张五", "789");
		List<Son> sons = new ArrayList<Son>();
		sons.add(s2);
		sons.add(s1);
		f.setSons(sons);
		
		String xml = DocumentConverter.toXML(f);
		System.out.println(xml);
		String json = DocumentConverter.toJson(f);
		System.out.println(json);
		Father f1 = DocumentConverter.jsonToEntity(json, Father.class);
		Father f2 = DocumentConverter.xmlToEntity(xml, Father.class);
	}
	
}


/**
 * inner class
 * @author Dylan
 * @time ����5:04:44
 */
@XStreamAlias("father")
class Father{
	
	private String name;
	
	private String password;
	
	private List<Son> sons;
	
	public Father(String name,String password){
		this.name =name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public static Father createUser(String name,String password){
		
		return new Father(name,password);
	}

	public List<Son> getSons() {
		return sons;
	}

	public void setSons(List<Son> sons) {
		this.sons = sons;
	}
	
	
}
@XStreamAlias("son")
class Son {

	private String name;
	private String password;
	
	
	/**
	 * @param name
	 * @param password
	 */
	public Son(String name, String password) {
		this.name = name;
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
