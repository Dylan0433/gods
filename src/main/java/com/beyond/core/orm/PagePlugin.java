package com.beyond.core.orm;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;

import com.beyond.core.orm.dialect.Dialect;
import com.beyond.core.util.LogFactory;
import com.beyond.core.util.ReflectionHelper;

/**
 * 
 * @author Dylan
 * @time 下午4:00:37
 */
public class PagePlugin implements Interceptor {
	
	protected final static Logger LOG = LogFactory.getLogger(PagePlugin.class);
	
	protected Dialect DIALECT = null;
	
	protected String SQL_REGULAR="";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		if(SQL_REGULAR.matches(statement.getId())){
			if(LOG.isDebugEnabled()){
				LOG.info("page is begin...");
			}
			
			Object parameter = invocation.getArgs()[1];
			
			BoundSql boundSql = statement.getBoundSql(parameter);
			
			String sql = boundSql.getSql().trim();
			if(StringUtils.isBlank(sql)){
				return null;
			}
			Object parameterObj = boundSql.getParameterObject();
			if(!isPage(parameterObj)){
				throw new IllegalStateException("parameter must be page instance");
			}
				
			
		}
		
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
		String dialectName = properties.getProperty("DIALECT");
		if(StringUtils.isBlank(dialectName)){
			LOG.error("the 'DIALECT' property of plugin must be declare");
			throw new IllegalStateException("the 'DIALECT' property of plugin must be declare");
		}
		
		Dialect dialect = (Dialect) ReflectionHelper.newInstance(dialectName);
		if(null == dialect){
			LOG.error("the Dialect init fail,please check the dialect class exist or not");
			throw new IllegalStateException("the Dialect init fail,please check the dialect class exist or not");
		}
		this.DIALECT = dialect;
		
		SQL_REGULAR = properties.getProperty("SQL_REGULAR");
		if(StringUtils.isBlank(SQL_REGULAR)){
			LOG.error("the 'SQL_REGULAR' property of plugin must be declare");
			throw new IllegalStateException("the 'SQL_REGULAR' property of plugin must be declare");
		}
		
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	protected boolean isPage(Object obj){
		if(obj instanceof Page){
			return true;
		}
		return false;
	}

}
