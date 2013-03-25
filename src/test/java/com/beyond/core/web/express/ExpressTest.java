/**
 * ExpressTest.java
 */
package com.beyond.core.web.express;

import java.io.IOException;

import org.junit.Test;

/**
 * @author Dylan
 * @time 2013-3-17
 */
public class ExpressTest {

	private String key = "3b47ce87f03b5d6b";
	
	@Test
	public void test() throws IOException{
		RequestParams param = new RequestParams();
		param.setId(key).setCom("yunda").setNu("1900346240374").setMuti("1").setShow("2").setOrder("desc");
		Express express = new Express();
		String info = express.getExpressInfo(param);
		System.out.println(info);
		
	}
	
	@Test
	public void test2() throws IOException{
		Express express = new Express();
		String info = express.getExpressInfo("yunda","1900346240374");
		System.out.println(info);
		
	}
}
