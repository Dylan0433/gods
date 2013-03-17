package com.beyond.core.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * the document include json,xml and so on
 * @author Dylan
 * @time ����4:26:24
 */
public class DocumentConverter {

	/**
	 * 
	 */
	private DocumentConverter(){}
	
	/**
	 * from entity to xml
	 * @param clazz
	 * @return
	 */
	public static <T> String toXML(T obj){
		
		return createXmlStream(obj.getClass()).toXML(obj);
	}
	
	/**
	 * from entity to json
	 * @param obj
	 * @return
	 */
	public static <T> String toJson(T obj){
		
		return createJsonStream(obj.getClass()).toXML(obj);
	}
	
	/**
	 * 
	 * @param xml
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlToEntity(String xml,Class<T> clazz){
		
		return (T) createXmlStream(clazz).fromXML(xml);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T jsonToEntity(String json,Class<T> clazz){
		
		return (T) createJsonStream(clazz).fromXML(json);
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static <T> XStream createXmlStream(Class<T> clazz){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias(clazz.getSimpleName(), clazz);
		xStream.processAnnotations(clazz);
		return xStream;
	}
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static <T> XStream createJsonStream(Class<T> clazz){
		XStream xStream = new XStream(new JettisonMappedXmlDriver());
		xStream.alias(clazz.getSimpleName(), clazz);
		xStream.processAnnotations(clazz);
		return xStream;
	}
	
}
