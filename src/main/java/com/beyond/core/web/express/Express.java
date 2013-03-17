/**
 * Express.java
 */
package com.beyond.core.web.express;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;

import com.beyond.core.util.BeanMapConverter;
import com.beyond.core.web.http.HttpHolder;

/**
 * @author Dylan
 * @time 2013-3-17
 */
public class Express {

	private String url = "http://api.kuaidi100.com/api";
	
	
	@SuppressWarnings("unchecked")
	public String getExpressInfo(RequestParams param){
		
		Map<String, String> params = BeanMapConverter.beanToMap(param);
		HttpHolder http = new HttpHolder();
		HttpMethod method = http.executeGet(url, new HashMap<String, String>(), params);
		try {
			return method.getResponseBodyAsString();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
