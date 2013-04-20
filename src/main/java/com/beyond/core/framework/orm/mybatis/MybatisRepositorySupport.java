/**
 * MybatisRepositorySupport.java
 */
package com.beyond.core.framework.orm.mybatis;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.beyond.core.exception.DatabaseException;
import com.beyond.core.framework.orm.BaseObject;
import com.beyond.core.framework.orm.Page;
import com.beyond.core.util.GodHands;

/**
 * this class offer some common crud function to operate mybatis for us. but
 * first ,we must offer a sqlSessionFactory bean for it;we can configurate <bean
 * id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
 * <property name="dataSource" ref="dataSource" /> </bean>
 * 
 * @author Dylan
 * @time 2013-3-30
 */
public abstract class MybatisRepositorySupport<ID, T extends BaseObject<ID>> extends SqlSessionDaoSupport {


	public MybatisRepositorySupport() {

	}

	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		Validate.notNull(sqlSessionFactory, "sqlSessionFactory is required,please config it in xml");
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	/**
	 * get the result by id
	 * 
	 * @param id
	 * @return
	 */
	protected T get(ID id) {
		Validate.notNull(id, "id must not be null");

		try {
			return getSqlSession().selectOne(getNamespace().concat(".getById"), id);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	protected T get(T entity){
		
		Validate.notNull(entity, "id must not be null");

		try {
			return getSqlSession().selectOne(getNamespace().concat(".get"), entity);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * if exit,we update,or not,we insert
	 * 
	 * @param entity
	 */
	protected int save(T entity) {

		Validate.notNull(entity, "entity must not be null");

		if (null != entity.getId()) {
			return insert(entity);
		}
		return update(entity);
	}

	/**
	 * save a list to database
	 * 
	 * @param entities
	 * @return
	 */
	protected int saveBatch(List<T> entities) {
		int result = 0;

		for (T entity : entities) {
			result += save(entity);
		}

		return result;
	}

	/**
	 * insert the entity to database,if exit,will be exception
	 * 
	 * @param entity
	 * @return
	 */
	protected int insert(T entity) {
		Validate.notNull(entity, "entity must not be null");
		try {
			return getSqlSession().insert(getNamespace().concat(".save"), entity);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * update the entity to database
	 * 
	 * @param entity
	 * @return
	 */
	protected int update(T entity) {

		Validate.notNull(entity, "entity must not be null");
		try {
			return getSqlSession().update(getNamespace().concat(".update"), entity);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	/**
	 * update for batch
	 * @param entities
	 * @return
	 */
	protected int updateBatch(List<T> entities){
		
		int i = 0;
		
		for(T entity : entities){
			i += update(entity);
		}
		return i;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	protected int delete(T entity){
		
		Validate.notNull(entity,"entity must not be null");
		
		return getSqlSession().delete(getNamespace().concat(".delete"), entity);
	}
	
	/**
	 * 
	 * @param entities
	 * @return
	 */
	protected int deleteBatch(List<T> entities){
		
		int i = 0;
		for(T entity : entities){
			i += delete(entity);
		}
		return i;
	}

	/**
	 * query all
	 * 
	 * @param entity
	 * @return
	 */
	protected List<T> query(T entity) {

		Validate.notNull(entity, "entity must not be null");

		try {
			return getSqlSession().selectList(getNamespace().concat(".query"), entity);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * query for page
	 * 
	 * @param page
	 * @return
	 */
	protected Page<T> queryPage(Page<T> page) {

		Validate.notNull(page, "page entity must not be null");

		try {
			List<T> results = getSqlSession().selectList(getNamespace().concat(".queryPage"), page);
			page.setResult(results);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
		return page;
	}

	protected String getNamespace() {
		return GodHands.genericsTypes(getClass())[1].getName();
	}

}
