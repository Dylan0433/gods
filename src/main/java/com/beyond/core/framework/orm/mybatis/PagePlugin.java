package com.beyond.core.framework.orm.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;

import com.beyond.core.exception.DatabaseException;
import com.beyond.core.framework.orm.Page;
import com.beyond.core.framework.orm.mybatis.dialect.Dialect;
import com.beyond.core.util.LogFactory;

/**
 * 
 * @author Dylan
 * @time 下午4:00:37
 */
@Intercepts({@Signature(type = Executor.class, method = "query",
args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PagePlugin implements Interceptor {
	
	protected final static Logger LOG = LogFactory.createLogger(PagePlugin.class);
	
	protected Dialect DIALECT = null;
	
	protected String SQL_REGULAR="";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		if(statement.getId().matches(SQL_REGULAR)){
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
			
			Connection connection = MybatisHelper.getConnection(statement);
			
			int totalCount = getTotalCount(sql,connection,statement,parameterObj,boundSql);
				
			Page<?> page = (Page<?>) parameterObj;
			page.setTotalCount(totalCount);
			
			String pageSql = MybatisHelper.buildPageSql(sql,page,DIALECT);
			invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
			final BoundSql bSql = MybatisHelper.newBoundSql(statement,pageSql,boundSql);
			statement = MybatisHelper.copyMappedStatement(statement,new SqlSource(){

				@Override
				public BoundSql getBoundSql(Object parameterObject) {
					return bSql;
				}
				
			});
			invocation.getArgs()[0] = statement;
			
		}
		
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
		this.DIALECT = MybatisHelper.getDialect(properties);
		
		this.SQL_REGULAR = MybatisHelper.getSqlRegular(properties);
		
	}
	

	/**
	 * get all count peple search
	 * @param sql
	 * @param connection
	 * @param statement
	 * @param parameterObj
	 * @param boundSql
	 * @return
	 */
	public int getTotalCount(final String sql,final Connection connection,
									final MappedStatement statement,
									final Object parameterObj,final BoundSql boundSql) {
		
		
		final String totalCountSql = "select count(1) from (" + sql + ") as total_count";
		
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		
		try {
			pStatement = connection.prepareStatement(totalCountSql);
			
			BoundSql bSql = new BoundSql(statement.getConfiguration(), 
										 totalCountSql, boundSql.getParameterMappings(), parameterObj);
			
			ParameterHandler handler = new DefaultParameterHandler(statement, parameterObj, bSql);
			
			handler.setParameters(pStatement);
			
			rs = pStatement.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}finally{
			MybatisHelper.freeResource(rs, pStatement);
		}
		
		return 0;
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