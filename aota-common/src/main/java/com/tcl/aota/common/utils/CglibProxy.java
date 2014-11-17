package com.tcl.aota.common.utils;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public abstract class CglibProxy<T> implements MethodInterceptor {

	private final static Logger LOG = Logger.getLogger(CglibProxy.class);
	
	private Object target;

	public CglibProxy(Object target) {
		this.target = target;
	}

	@SuppressWarnings("unchecked")
	public T proxyTarget() {
		Enhancer eh = new Enhancer();
		eh.setSuperclass(target.getClass());
		eh.setCallback(this);
		return (T) eh.create();
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] params,
			MethodProxy methodproxy) throws Throwable {
		Object o = null;
		try {
			before();
			o = method.invoke(target, params);
		} catch(Exception e) {
			whenException();
			LOG.error(e.getMessage(), e);
		} finally {
			after();
		}
		return o;
	}

	protected void before(){
		// do something
	};

	protected void after() {
		// do something
	}
	
	protected void whenException() {
		// do something
	}
}
