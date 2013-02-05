package com.beyond.core.monitor;

import org.junit.Test;
import org.slf4j.Logger;

import com.beyond.core.util.LogFactory;

/**
 * 
 * @author Dylan
 * @time ����9:18:54
 */
public class OSMonitorTest {

	private final static Logger LOG = LogFactory.createLogger(OSMonitorTest.class);
	private final static OSMonitor monitor  = new OSMonitor();
	
	
	
	@Test
	public void getSystemNameTest(){
		LOG.info(monitor.getSystemName());
	}
}
