package com.beyond.core.util;


/**
 * 
 * @author Dylan
 * @time 下午1:59:29
 */
public class StringHelperTest {

	
	
	public static void main(String[] args) {
		String message = "你是{0},他是{1}，我是{2}";
		
		System.out.println(StringHelper.format(message, new String[]{"张三","李四","林华勇"}));
	}
}
