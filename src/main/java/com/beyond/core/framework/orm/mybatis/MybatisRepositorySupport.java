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
import com.beyond.core.framework.orm.Page;
import com.beyond.core.util.GodHands;


/**this class offer some common crud function to  operate mybatis for us.
 * but first ,we must offer a sqlSessionFactory bean for it;we can configurate
 * <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
 *		<property name="dataSource" ref="dataSource" />
 *	</bean>
 * @author Dylan
 * @time 2013-3-30
 */
public abstract class MybatisRepositorySupport<ID,T> extends SqlSessionDaoSupport{

	
	public MybatisRepositorySupport(){
		
	}
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		Validate.notNull(sqlSessionFactory, "sqlSessionFactory is required,please config it in xml");
	    super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	protected T get(ID id){
		Validate.notNull(id,"id must not be null");
		
		try {
			return getSqlSession().selectOne(getNamespace().concat(".get"), id);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	/**
	 * 新增
	 * @param entity
	 */
	protected int save(T entity){
		
		Validate.notNull(entity,"entity must not be null");
		
		try {
			return getSqlSession().insert(getNamespace().concat(".save"), entity);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	/**
	 * 更新
	 * @param entity
	 * @return
	 */
	protected int update(T entity){
		
		Validate.notNull(entity,"entity must not be null");
		try {
			return getSqlSession().update(getNamespace().concat(".update"), entity);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	/**
	 * 查询所有
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> query(T entity){
		
		Validate.notNull(entity,"entity must not be null");
		
		try {
			return (List<T>) getSqlSession().selectList(getNamespace().concat(".query"), entity);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	protected Page<T> queryPage(Page<T> page){
		
		Validate.notNull(page,"page entity must not be null");
		
		try {
			List<T> results = getSqlSession().selectList(getNamespace().concat(".queryPage"), page);
			page.setResult(results);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
		return page;
	}
	
	protected String getNamespace(){
		return GodHands.genericsTypes(getClass())[1].getName();
	}
	
}
