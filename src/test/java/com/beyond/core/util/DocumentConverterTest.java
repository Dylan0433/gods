package com.beyond.core.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;

import com.beyond.core.web.express.Data;
import com.beyond.core.web.express.ResponseBody;
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
	
	@Test
	public void test3(){
		Data d1  = new Data();
		d1.setContext("aa");
		d1.setTime("2013-02-06 11:38:00");
		Data d2  = new Data();
		d2.setContext("bb");
		d2.setTime("2013-03-06 11:38:00");
		List<Data> data = new ArrayList<Data>();
		data.add(d1);
		data.add(d2);
		/*Map<String,String> m1 = new HashMap<String,String>();
		m1.put("context", "aa");
		m1.put("time", "2013-03-06 11:38:00");
		Map<String,String> m2 = new HashMap<String,String>();
		m2.put("context", "bb");
		m2.put("time", "2013-01-06 11:38:00");
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		data.add(m2);
		data.add(m1);*/
		ResponseBody body = new ResponseBody();
		body.setCom("ems");
		body.setCondition("F00");
		body.setData(data);
		body.setIscheck(1);
		body.setMessage("ok");
		body.setNu("1311412421");
		body.setState(1);
		body.setStatus(3);
		
		String xml = DocumentConverter.toXML(body);
		System.out.println(xml);
		String json = DocumentConverter.toJson(body);
		System.out.println(json);
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
