/**
 * TransactionProxy.java
 */
package com.beyond.core.framework.spring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.ibatis.transaction.TransactionException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author Dylan
 * @time 2013-4-17
 */
public class TransactionProxy {

	private Object instance;
	
	private PlatformTransactionManager txManager;
	
	public TransactionProxy(Object _instance,PlatformTransactionManager _txManager){
		this.instance = _instance;
		this.txManager = _txManager;
	}
	
	public Object getProxy(){
		return Proxy.newProxyInstance(instance.getClass().getClassLoader(), instance.getClass().getInterfaces(), new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object result = null;
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
				TransactionStatus status = txManager.getTransaction(def);
				try {
					result = method.invoke(Class.forName(instance.getClass().getName()).newInstance(), args);
				} catch (Exception e) {
					txManager.rollback(status);
					throw new TransactionException(e.getMessage());
				}
				txManager.commit(status);
				return result;
			}
		});
	}
}
