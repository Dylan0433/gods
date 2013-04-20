/**
 * BaseObject.java
 */
package com.beyond.core.framework.orm;

import java.io.Serializable;

/**
 * @author Dylan
 * @time 2013-4-17
 */
public abstract class BaseObject<ID extends Object> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected ID id;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	

}
