/**
 * Express.java
 */
package com.beyond.core.web.express;

import static com.beyond.core.web.http.HttpHolder.Method.*;

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

	private String url = "http://api.kuaidi100.com/api?";
	private String key = "3b47ce87f03b5d6b";
	
	/**
	 * 得到快递信息
	 * @param express 快递公司代号
	 * @param nu 快递单号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getExpressInfo(String express,String nu){
		RequestParams param = new RequestParams();
		param.setId(key).setCom(express).setNu(nu).setMuti("1").setShow("2").setOrder("desc");
		Map<String, Object> params = BeanMapConverter.beanToMap(param);
		HttpHolder http = HttpHolder.instance();
		HttpMethod method = null;
		try {
			method = http.execute(GET,url, new HashMap<String, String>(), params, new HashMap<String, String>());
			return method.getResponseBodyAsString();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			http.releaseConnection(method);
		}
	}
	
	/**
	 * 得到快递信息
	 * @param param 请求参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getExpressInfo(RequestParams param){
		
		Map<String, Object> params = BeanMapConverter.beanToMap(param);
		HttpHolder http = HttpHolder.instance();
		HttpMethod method = null;
		try {
			 method = http.execute(GET,url, new HashMap<String, String>(), params, new HashMap<String, String>());
			return method.getResponseBodyAsString();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			http.releaseConnection(method);
		}
	}
}
