package com.beyond.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * create the log
 * @author Dylan
 * @time ����9:30:41
 */
public class LogFactory{

	private LogFactory(){}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> Logger createLogger(Class<T> clazz){
		
		return LoggerFactory.getLogger(clazz);
	}
	
}
