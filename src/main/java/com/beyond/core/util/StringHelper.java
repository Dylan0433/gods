package com.beyond.core.util;

/**
 * 
 * @author Dylan
 * @time 下午1:34:16
 */
public class StringHelper {

	private StringHelper(){}
	
	
	/**
	 * 
	 * @param message
	 * @param values
	 * @return
	 */
	public static String format(String message,String... values){
		StringBuffer buffer = new StringBuffer(message);
		for(int i=0;i<=buffer.lastIndexOf("{");){
			int begin = buffer.indexOf("{", i);
			int end = buffer.indexOf("}", begin);
			String beReplace = buffer.substring(begin+1, end);
			String Replaced = values[Integer.valueOf(beReplace)];
			buffer.replace(begin+1, end, Replaced);
			i=++begin;
			
		}
		return buffer.toString().replace('{', '【').replace('}', '】');
	}
}
