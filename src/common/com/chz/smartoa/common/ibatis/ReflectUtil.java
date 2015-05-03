package com.chz.smartoa.common.ibatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

/**
 * 修改私有属性值
 * 
 * @author huangdhui
 * 
 */
public class ReflectUtil {
	private static final Logger logger = Logger.getLogger(ReflectUtil.class);	
	
	@SuppressWarnings("unchecked")
	public static void setFieldValue(Object target, String fname, Class ftype,
			Object fvalue) {

		if (target == null
				|| fname == null
				|| "".equals(fname)
				|| (fvalue != null && !ftype
						.isAssignableFrom(fvalue.getClass()))) {
			return;
		}
		Class clazz = target.getClass();
		try {
			Method method = clazz.getDeclaredMethod("set"
					+ Character.toUpperCase(fname.charAt(0))
					+ fname.substring(1), ftype);
			if (!Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			}
			method.invoke(target, fvalue);

		} catch (Exception me) {
			try {
				Field field = clazz.getDeclaredField(fname);
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				field.set(target, fvalue);
			} catch (Exception fe) {
			}
		}
	}
	
	public static Object getFieldValue(Object target, String fname) {  
		Object reslut = null; 
		if (target == null  
		|| fname == null  
		|| "".equals(fname)) {  
		return null;  
		}  
		Class clazz = target.getClass();  
		try {  
		Field field = clazz.getDeclaredField(fname); 
		reslut =  field.get(fname); 


		} catch (Exception me) {  
		if (logger.isDebugEnabled()) {  
			logger.debug(me);  
		}  
		}  
		return reslut; 
		}  
	
	/**
	 * 根据方法得到对象的值
	 * @param target
	 * @param fname
	 * @return
	 */
	public static Object getFieldValue4Method(Object target, String fname) {  
		Object reslut = null; 
		if (target == null  
		|| fname == null  
		|| "".equals(fname)) {  
		return null;  
		}  
		Class clazz = target.getClass();  
		try {  
			   Method method = clazz.getMethod("get" + fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()), new Class[]{});
			   reslut = method.invoke(target, new Object[]{});

		} catch (Exception me) {  
		if (logger.isDebugEnabled()) {  
			logger.debug(me);  
		}  
		}  
		return reslut; 
		}  
}
