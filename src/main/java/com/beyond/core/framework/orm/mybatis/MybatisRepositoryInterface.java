/**
 * MybatisRepositoryInterface.java
 */
package com.beyond.core.framework.orm.mybatis;

import java.util.List;

import com.beyond.core.framework.orm.BaseObject;
import com.beyond.core.framework.orm.Page;

/**
 * @author Dylan
 * @time 2013-4-17
 */
public interface MybatisRepositoryInterface<ID, T extends BaseObject<ID>> {

	/**
	 * get the result by id
	 * 
	 * @param id
	 * @return
	 */
	T get(ID id);

	/**
	 * if exit,we update,or not,we insert
	 * 
	 * @param entity
	 */
	int save(T entity);

	/**
	 * save a list to database
	 * 
	 * @param entities
	 * @return
	 */
	int saveBatch(List<T> entities);
	
    int saveBatchTransaction(List<T> entities);
	
	/**
	 * insert the entity to database,if exit,will be exception
	 * 
	 * @param entity
	 * @return
	 */
	int insert(T entity);

	/**
	 * update the entity to database
	 * 
	 * @param entity
	 * @return
	 */
	int update(T entity);

	/**
	 * query all
	 * 
	 * @param entity
	 * @return
	 */
	List<T> query(T entity);

	/**
	 * query for page
	 * 
	 * @param page
	 * @return
	 */
	Page<T> queryPage(Page<T> page);
}
