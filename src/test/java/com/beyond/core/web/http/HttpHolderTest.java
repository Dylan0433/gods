/**
 * HttpHolderTest.java
 */
package com.beyond.core.web.http;

import static com.beyond.core.web.http.HttpHolder.Method.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpMethod;
import org.junit.Test;

/**
 * @author Dylan
 * @time 2013-3-16
 */
public class HttpHolderTest {

//	private String url = "http://www.google.cn";
	private String url = "http://localhost:8080/cache/test-controller/test";
	@Test
	public void test(){
		
		HttpHolder http = HttpHolder.instance();
		
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Connection", "Keep-Alive");
		headers.put("Accept-Language", "zh-CN");
		headers.put("Content-Type", "text/html; charset=UTF-8");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("name", "张三");
		params.put("age", "15");
	//	params.put(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
	//	HttpMethod response = http.executeGet(url,headers,params);
	//	HttpMethod response = http.executePost(url, headers, params);
	/*	try {
			System.out.println(response.getResponseBodyAsString());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	//	User user = http.executeGet(url, User.class);
	//	System.out.println(user.getName() + " : " + user.getPassword());
		User user = http.executePost(url,headers,params, User.class);
		System.out.println(user.getName() + " : " + user.getPassword());
		
	}
	
	@Test
	public void test2() throws IOException{
		HttpHolder client = HttpHolder.instance();
		/*Cookie cookie = new Cookie();
		cookie.setDomain("localhost");
		cookie.setPath("/");
		cookie.setName("name");
		cookie.setValue("张三");
		client.addCookie(cookie);*/
		HttpMethod result = client.executePost(url);
		System.out.println(result.getResponseBodyAsString());
		Cookie [] cookies = client.getCookie();
		for(Cookie c : cookies){
			System.out.println(c.getName() + " : " + c.getValue());
		}
		client.addCookie(cookies[0]);
	}
	
	@Test
	public void test3() throws IOException{
		HttpHolder client = HttpHolder.instance();
		HttpMethod response = client.execute(GET, "http://www.baidu.com", new HashMap<String,String>(), new HashMap<String,Object>(), new HashMap<String,String>());
		System.out.println(response.getResponseBodyAsString());
		
	}
	class User{
		
		private String name;
		private String password;
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
}
