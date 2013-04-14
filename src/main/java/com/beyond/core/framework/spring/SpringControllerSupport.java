/**
 * SpringControllerSupport.java
 */
package com.beyond.core.framework.spring;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @author Dylan
 * @time 2013-3-31
 */
public abstract class SpringControllerSupport extends MultiActionController {

	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.setIgnoreInvalidFields(true);
		binder.setIgnoreUnknownFields(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), false));
	}
}
