package com.beyond.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * create the log
 * @author Dylan
 * @time ионГ9:30:41
 */
public class LogProducer{

	private LogProducer(){}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> Logger getLogger(Class<T> clazz){
		
		return LoggerFactory.getLogger(clazz);
	}
	
}
