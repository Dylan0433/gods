package com.beyond.core.util;

import org.junit.Test;
import org.slf4j.Logger;

public class DocumentConverterTest {

	private final static Logger LOG = LogFactory.createLogger(DocumentConverterTest.class);
	@Test
	public void test(){
		
		User u = User.createUser("����", "1234");
		
		String xml = DocumentConverter.toXML(u);
		
		String json = DocumentConverter.toJson(u);
		
		String x = "{\"User\":{\"name\":\"����\",\"password\":1234}}";
		
		User user = DocumentConverter.xmlToEntity(x, User.class);
		
		LOG.info(xml);
		
		LOG.info(json);
		
		LOG.info("name �� " + user.getName() + " password : " + user.getPassword());
		
		
	}
	
	
	/**
	 * inner class
	 * @author Dylan
	 * @time ����5:04:44
	 */
	static class User{
		
		private String name;
		
		private String password;
		
		public User(String name,String password){
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
		
		public static User createUser(String name,String password){
			
			return new User(name,password);
		}
		
		
	}
}
