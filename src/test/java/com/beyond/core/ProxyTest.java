/**
 * ProxyTest.java
 */
package com.beyond.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

import com.beyond.core.framework.orm.BaseObject;
import com.beyond.core.framework.orm.mybatis.MybatisRepositorySupport;


/**
 * @author Dylan
 * @time 2013-4-17
 */
public class ProxyTest {

	public static void main(String[] args) {
		
		/*TestObject to = new TestObject();
		to.add("aa");
		Test t = (Test) GodHands.newInstance(Test.class);
		t.add();*/
	}
	
	interface Test{
		
		public void add();
		
		public void delete();
		
	}
	static class TestObject implements Test{

		/* (non-Javadoc)
		 * @see com.beyond.core.ProxyTest.Test#add()
		 */
		@Override
		public void add() {

			System.out.println("add method");
		}

		/* (non-Javadoc)
		 * @see com.beyond.core.ProxyTest.Test#delete()
		 */
		@Override
		public void delete() {

			System.out.println("delete method");
		}
		
		public void add(String str){
			Test to = (Test) getProxy();
			to.add();
		}
		
		public Object getProxy(){
			return Proxy.newProxyInstance(TestObject.class.getClassLoader(), new Class[]{Test.class}, new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					System.out.println("method start");
					method.invoke(Class.forName(TestObject.class.getName()).newInstance(), args);
					System.out.println("method end");
					return proxy;
				}
			});
		}
		
	}
}
