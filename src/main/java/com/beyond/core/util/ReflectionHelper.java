package com.beyond.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

import org.apache.commons.lang3.Validate;

/**
 * through the class's name we can know,this class hope to help the peple who
 * use it to get all kinds of value.
 * 
 * @author Dylan
 * @time 上午10:36:14
 */
public class ReflectionHelper {

	private ReflectionHelper() {
	}

	/**
	 * 
	 * @param instance
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(final Object instance, final String fieldName) {

		Field field = getAccessibleField(instance, fieldName);
		Validate.notNull(field, StringHelper.format("{0}not be found", fieldName));
		try {
			return field.get(instance);
		} catch (IllegalAccessException e) {
			handleReflectionException(e);
		}
		return null;
	}

	/**
	 * 
	 * @param instance
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(final Object instance, final String fieldName, final Object value) {

		Field field = getAccessibleField(instance, fieldName);

		Validate.notNull(field, fieldName + StringHelper.format("{0}not be founded", fieldName));

		try {
			field.set(instance, value);
		} catch (IllegalAccessException e) {
			handleReflectionException(e);
		}

	}

	/**
	 * 
	 * @param instance
	 *            the target instance which we want to get
	 * @param fieldName
	 *            the name of field we want to get
	 * @return
	 */
	private static Field getAccessibleField(final Object instance, final String fieldName) {
		Validate.notNull(instance, "the parameter of instance can't be null");
		Validate.notBlank(fieldName, "fieldName can't be blank", new Object[0]);

		for (Class<?> superClass = instance.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {

			try {

				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;

			} catch (NoSuchFieldException e) {
				handleReflectionException(e);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param clazz
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method getAccessibleMethod(final Class<?> clazz, final String methodName, final Class<?>... parameterTypes) {

		for (Class<?> superClass = clazz; clazz != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);
				makeAccessible(method);
				return method;
			} catch (NoSuchMethodException e) {
				handleReflectionException(e);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param instance
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method getAccessibleMethod(final Object instance, final String methodName, final Class<?>... parameterTypes) {

		return getAccessibleMethod(instance.getClass(), methodName, parameterTypes);
	}

	/**
	 * 
	 * @param clazz
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> A getAccessibleAnnotation(final Class<?> clazz, final Class<A> annotationClass) {

		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				A annotation = superClass.getAnnotation(annotationClass);
				return annotation;
			} catch (Exception e) {
				handleReflectionException(e);
			}

		}

		return null;

	}

	/**
	 * 
	 * @param instance
	 * @param methodName
	 * @param parameterTypes
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Object instance, String methodName, Class<?> parameterTypes, Object... args) {

		Method method = getAccessibleMethod(instance, methodName, parameterTypes);
		Validate.notNull(method, StringHelper.format("method {0} not be found", methodName));
		try {
			return method.invoke(instance, args);
		} catch (Exception e) {
			handleReflectionException(e);
		}
		return null;
	}

	/**
	 * 
	 * @param field
	 */
	public static void makeAccessible(Field field) {
		field.setAccessible(true);
	}

	/**
	 * 
	 * @param method
	 */
	public static void makeAccessible(Method method) {
		method.setAccessible(true);
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T newInstance(Class<T> clazz) {

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			handleReflectionException(e);
		}
		return null;
	}

	/**
	 * 
	 * @param fullClassName
	 * @return
	 */
	public static Object newInstance(String fullClassName) {
		try {
			Class<?> clazz = Class.forName(fullClassName);
			return newInstance(clazz);
		} catch (ClassNotFoundException e) {
			handleReflectionException(e);
		}
		return null;
	}

	/**
	 * 
	 * @param e
	 */
	private static void handleReflectionException(Exception e) {

		if (e instanceof NoSuchMethodException) {
			throw new IllegalStateException("method not found " + e.getMessage());
		}
		if (e instanceof NoSuchFieldException) {
			throw new IllegalStateException("field not fount " + e.getMessage());
		}
		if (e instanceof IllegalArgumentException) {
			throw new IllegalStateException("args is illegal" + e.getMessage());
		}

		throw new UndeclaredThrowableException(e);

	}
}
