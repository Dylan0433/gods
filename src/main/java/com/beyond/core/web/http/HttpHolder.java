/**
 * HttpClient.java
 */
package com.beyond.core.web.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.Validate;

import com.beyond.core.exception.HttpMethodExecuteException;
import com.beyond.core.exception.IOCommunicateException;
import com.beyond.core.util.DocumentConverter;

/**http客户端
 * @author Dylan
 * @time 2013-3-16
 */
public class HttpHolder {

	public static HttpClient client = new HttpClient();
	

	/**
	 * 
	 * @param hostConfiguration
	 */
	public void setHostConfiguration(HostConfiguration hostConfiguration){
		client.setHostConfiguration(hostConfiguration);
	}
	
	/**
	 * 
	 * @param httpConnectionManager
	 */
	public void setHttpConnectionManage(HttpConnectionManager httpConnectionManager){
		client.setHttpConnectionManager(httpConnectionManager);
	}
	
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 */
	public HttpMethod executeGet(String url,Map<String,String> headers,Map<String,Object> params){
		Validate.notEmpty(url);
		GetMethod get = new GetMethod(url);
		initHeader(get, headers);
		get.setParams(createParams(params));
		return execute(get);
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public HttpMethod executeGet(String url){
		return executeGet(url, new HashMap<String, String>(), new HashMap<String, Object>());
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @param clazz
	 * @return
	 */
	public <T> T executeGet(String url,Map<String,String> headers,Map<String,Object> params,Class<T> clazz){
		GetMethod get = new GetMethod(url);
		initHeader(get, headers);
		get.setParams(createParams(params));
		return execute(get, clazz);
	}
	
	/**
	 * 
	 * @param url
	 * @param clazz
	 * @return
	 */
	public <T> T executeGet(String url,Class<T> clazz){
		return executeGet(url, new HashMap<String, String>(), new HashMap<String, Object>(), clazz);
	}
	/**
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 */
	public HttpMethod executePost(String url,Map<String,String> headers,Map<String,Object> params){
		
		PostMethod post = new PostMethod(url);
		initHeader(post, headers);
		post.setParams(createParams(params));
		return execute(post);
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public HttpMethod executePost(String url){
		return executePost(url, new HashMap<String, String>(), new HashMap<String, Object>());
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @param clazz
	 * @return
	 */
	public <T> T executePost(String url,Map<String,String> headers,Map<String,Object> params,Class<T> clazz){
		PostMethod post = new PostMethod(url);
		initHeader(post, headers);
		post.setParams(createParams(params));
		return execute(post,clazz);
	}
	
	/**
	 * 
	 * @param url
	 * @param clazz
	 * @return
	 */
	public <T> T executePost(String url,Class<T> clazz){
		return executePost(url,new HashMap<String, String>(),new HashMap<String, Object>(),clazz);
	}
	/**
	 * 执行http请求
	 * @param method 请求方法
	 * @return
	 */
	public HttpMethod execute(HttpMethod method){
		try {
			client.executeMethod(method);
		} catch (HttpException e) {
			throw new HttpMethodExecuteException(e.getMessage());
		} catch (IOException e) {
			throw new IOCommunicateException(e.getMessage());
		}
		return method;
	}
	
	
	/**
	 * 执行http讲求，并把返回转换成所有希望的对象
	 * @param method 讲求方法
	 * @param clazz  要把response转换成的对象类型
	 * @return
	 */
	public <T> T execute(HttpMethod method,Class<T> clazz){
		
		HttpMethod response = execute(method);
		String json = "";
		try {
			json = response.getResponseBodyAsString();
		} catch (IOException e) {
			throw new IOCommunicateException(e.getMessage());
		}
		return DocumentConverter.jsonToEntity(json, clazz);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public HttpMethodParams createParams(Map<String,Object> params){
		HttpMethodParams methodParams = new HttpMethodParams();
		methodParams.setContentCharset("utf-8");
	//	methodParams.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		for(String key : params.keySet()){
			methodParams.setParameter(key, params.get(key));
		}
		return methodParams;
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public NameValuePair[] createNameValuePair(Map<String,String> params){
		
		Validate.notNull(params);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		
		for(String key : params.keySet()){
			NameValuePair pair = new NameValuePair();
			pair.setName(key);
			pair.setValue(params.get(key));
			pairs.add(pair);
		}
		return pairs.toArray(new NameValuePair[]{});
	}
	
	/**
	 * 
	 * @param method
	 * @param headers
	 */
	protected void initHeader(HttpMethod method,Map<String,String> headers){
		Validate.notNull(method);
		Validate.notNull(headers);
		for(String key : headers.keySet()){
			method.addRequestHeader(key, headers.get(key));
		}
	}
	
	public static HttpHolder instance(){
		return new HttpHolder();
	}
}
