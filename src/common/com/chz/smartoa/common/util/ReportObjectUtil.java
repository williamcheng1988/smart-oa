package com.chz.smartoa.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author : wesson
 * @create_date ��2014-12-05
 *
 */
@SuppressWarnings("rawtypes")
public class ReportObjectUtil
{
	public static Map object2Map(Object obj) 
	{
		String getMethodStartTag = "get"; // ��ȡ��ݵķ�����ǰ׺
		String isMethodStartTag = "is"; // boolean��ȡ��ݵķ�����ǰ׺
		if (obj instanceof Map)
		{
			return (Map)obj;
		}
		try{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Method[] methods = obj.getClass().getMethods();
			for (int i = 0; i < methods.length; i++)
			{
				String methodName = methods[i].getName();
				// ��ȡ����get����
				if (methodName.subSequence(0, getMethodStartTag.length()).equals(getMethodStartTag) || methodName.subSequence(0, isMethodStartTag.length()).equals(isMethodStartTag))
				{
					String tempName = "";
					// ��ȡis������ֶ���� 
					if (methodName.indexOf(isMethodStartTag) == 0){
						tempName = methodName.substring(isMethodStartTag.length(), methodName.length());
					}
					// ��ȡget������ֶ���� 
					else {
						tempName = methodName.substring(getMethodStartTag.length(), methodName.length());
					}
					// ����һ���ַ�ת��ΪСд
					Method mGet = obj.getClass().getMethod("get" + tempName, new Class[] {});
					
					String firstName = tempName.substring(0, 1).toLowerCase();
					String lastName = "";
					if (tempName.length() > 1)
					{
						lastName = tempName.substring(1, tempName.length());
					}
					tempName = firstName + lastName;
					Object val = null;
					try
					{
						Class type = mGet.getReturnType();
						String name = type.getName();
						
	//					 ����getter������ȡ����ֵ
						val = methods[i].invoke(obj);
						if(val == null){
							resultMap.put(tempName,null);
						}else{
							if ("java.lang.String".equals(name)) {// �ַ�
								resultMap.put(tempName, val.toString());
							} else if ("java.lang.Long".equals(name) || (type == Long.TYPE)) {// ������
								resultMap.put(tempName, Long.valueOf(val.toString()));
							} else if ("java.lang.Integer".equals(name) || (type == Integer.TYPE)) {// ����
								resultMap.put(tempName, Integer.valueOf(val.toString()));
							}else if ("java.math.BigDecimal".equals(name) || (type == Integer.TYPE)) {// ����������
								resultMap.put(tempName, NumberUtils.stringToBigDecimal(val.toString()));
							}else if ("java.util.Date".equals(name)) { // ����
								try{
									resultMap.put(tempName, DateUtil.formatDateToString(val.toString(), "yyyy/MM/dd"));
								} catch (ParseException e){
									try{
										resultMap.put(tempName, DateUtil.formatDateToString(val.toString(), "yyyy/MM/dd"));
									} catch (ParseException e2){
										resultMap.put(tempName, null);
									}
								}
							}else{
								resultMap.put(tempName,null);
							}
						}
						//methods[i]
					} catch (Exception e)
					{
					}
					//resultMap.put(tempName, val);
				}
			}
			return resultMap;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	
	/**��request�л�ȡֵ����䵽������ȥ
	 * �?��Ʊ���������ж����һ��
	 * @param obj
	 * @param request
	 * @param columns
	 * @return
	 */
	public static void fillMap2Obj(Object obj, Map requestMap) 
	{
		String getMethodStartTag = "get"; // ��ȡ��ݵķ�����ǰ׺
		String isMethodStartTag = "is"; // boolean��ȡ��ݵķ�����ǰ׺
		if (obj instanceof Map || obj instanceof Collection)
		{
			return ;
		}
		try{
			Method[] methods = obj.getClass().getMethods();
			for (int i = 0; i < methods.length; i++)
			{
				String methodName = methods[i].getName();
				// ��ȡ����get����
				if (methodName.subSequence(0, getMethodStartTag.length()).equals(getMethodStartTag) || methodName.subSequence(0, isMethodStartTag.length()).equals(isMethodStartTag))
				{
					String tempName = "";
	//				 ��ȡis������ֶ���� 
					if (methodName.indexOf(isMethodStartTag) == 0){
						tempName = methodName.substring(isMethodStartTag.length(), methodName.length());
					}
					// ��ȡget������ֶ���� 
					else {
						tempName = methodName.substring(getMethodStartTag.length(), methodName.length());
					}
					Object requestValue = requestMap.get(tempName); // ���ַ��д��ȡ
					if (requestValue == null)
					{
						// ����һ���ַ�ת��ΪСд
						String firstName = tempName.substring(0, 1).toLowerCase();
						String lastName = "";
						if (tempName.length() > 1)
						{
							lastName = tempName.substring(1, tempName.length());
						}
						// ����ı?�ֶ����
						tempName = firstName + lastName;
						// ��ȡ��������ֵ
						requestValue = requestMap.get(tempName); // ҳ�洫������ֵ
					}
					methods[i].getReturnType(); //  ������������
					setObjPropertyValue(obj, tempName, requestValue);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**��request�л�ȡֵ����䵽������ȥ
	 * �?��Ʊ���������ж����һ��
	 * @param obj
	 * @param request
	 * @param columns
	 * @return
	 */
	public static void fill2Obj(Object obj, HttpServletRequest request) 
	{
		String getMethodStartTag = "get"; // ��ȡ��ݵķ�����ǰ׺
		String isMethodStartTag = "is"; // boolean��ȡ��ݵķ�����ǰ׺
		if (obj instanceof Map || obj instanceof Collection)
		{
			return ;
		}
		try{
			Method[] methods = obj.getClass().getMethods(); // ���ұ����� + ���������������ķ���
//			Method[] methods = obj.getClass().getDeclaredMethods(); // ֻ�鵽��ǰ���������ķ���
			for (int i = 0; i < methods.length; i++)
			{
				String methodName = methods[i].getName();
				// ��ȡ����get����
				if (methodName.subSequence(0, getMethodStartTag.length()).equals(getMethodStartTag) || methodName.subSequence(0, isMethodStartTag.length()).equals(isMethodStartTag))
				{
					String tempName = "";
	//				 ��ȡis������ֶ���� 
					if (methodName.indexOf(isMethodStartTag) == 0){
						tempName = methodName.substring(isMethodStartTag.length(), methodName.length());
					}
					// ��ȡget������ֶ���� 
					else {
						tempName = methodName.substring(getMethodStartTag.length(), methodName.length());
					}
					String requestValue = request.getParameter(tempName); // ���ַ��д��ȡ
					if (requestValue == null)
					{
						// ����һ���ַ�ת��ΪСд
						String firstName = tempName.substring(0, 1).toLowerCase();
						String lastName = "";
						if (tempName.length() > 1)
						{
							lastName = tempName.substring(1, tempName.length());
						}
						// ����ı?�ֶ����
						tempName = firstName + lastName;
						// ��ȡ��������ֵ
						requestValue = request.getParameter(tempName); // ҳ�洫������ֵ
					}
					methods[i].getReturnType(); //  ������������
					setObjPropertyValue(obj, tempName, requestValue);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/***
	 * ͨ�������ö����ֵ
	 * @param cell
	 * @param formatString
	 * @return
	 */
	public static void setObjPropertyValue(Object obj,String columnName, Object strText) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, ClassNotFoundException
	{
        columnName = columnName.substring(0,1).toUpperCase() + columnName.substring(1, columnName.length());
		try{
	        // ��ȡ����
			Method mGet = obj.getClass().getMethod("get" + columnName, new Class[] {});
			
			// ���������������Ե�ֵ
	        Method mSet = obj.getClass().getMethod("set" + columnName, new Class[]{
	        		  Class.forName(mGet.getReturnType().getName()) 
	                });
	        mSet.invoke(obj, new Object[]{
	        		convertValue(strText,mGet.getReturnType())
	                });
		} catch (NoSuchMethodException e) {
			// �Ҳ������������?
		}

	}
	
	/**
	 * ��DTO�����Էŵ�Map��
	 *@description  
	 *@param arg
	 *@param params
	 */
	public static void putOjbectInMap(Object arg, Map params){
		if(arg != null) {
			if (arg instanceof java.util.Map || arg instanceof java.util.HashMap) {
				params.put("row_id", ((Map)arg).get("row_id"));
				
			} else {
				Field[] fields = arg.getClass().getDeclaredFields();
				for(Field field : fields){
					//�ж��Ƿ�Ϊ����
					if(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) continue;
					//�ж��Ƿ񼯺�
					Class type = field.getType();
					if(type.isArray() || type.isAssignableFrom(List.class) || type.isAssignableFrom(Map.class)) continue;
					params.put(field.getName(),getValue(arg,field));
				}
			}
		}
	}
	
	/**
	 * ��ȡ���������ֵ
	 *@description  
	 *@param arg
	 *@param field
	 *@return
	 */
	public static Object getValue(Object arg,Field field){
		String fieldName = field.getName();
		String methodName = "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
		try {
			Method method = arg.getClass().getDeclaredMethod(methodName, new Class[]{});
			if(method != null) return method.invoke(arg, new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ݷ�����ƻ�ȡ����ֵ
	 *@description  
	 *@param arg
	 *@param fieldName
	 *@return
	 */
	public static Object getValue(Object arg,String functionName){
		try {
			Method method = arg.getClass().getMethod(functionName, new Class[]{});
			return method.invoke(arg, new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**�����жϣ���������������
	 * @param cell
	 * @param formatString
	 * @return
	 */
	public static Object convertValue(Object value, Class type)
	{
		if (value == null )
		{
			return null;
		}
		String name = type.getName();
		if ("java.lang.String".equals(name))// �ַ�
		{
			return value.toString();
		} else if ("java.lang.Long".equals(name) || (type == Long.TYPE))// ������
		{
			return Long.valueOf(value.toString());
		} else if ("java.lang.Integer".equals(name) || (type == Integer.TYPE)) // ����
		{
			return Integer.valueOf(value.toString());
		}else if ("java.math.BigDecimal".equals(name) || (type == Integer.TYPE)) // ����������
		{
			return NumberUtils.stringToBigDecimal(value.toString());
		}else if ("java.util.Date".equals(name)) // ����
		{
			try
			{
				return DateUtil.formatDateToString(value.toString(), "yyyy-MM-dd");
			} catch (ParseException e)
			{
				try
				{
					return DateUtil.formatDateToString(value.toString(), "yyyy/MM/dd");
				} catch (ParseException e2)
				{
					return null;
				}
			}
		}
		return null;
	}
	
	/**�ж�һ�����������Ƿ񼯺ϻ�������
	 * @param obj
	 * @return
	 */
	public static boolean isCollectionOrArray(Object obj){
		if (obj instanceof Map)
		{
			return true;
		}
		else if (obj instanceof List)
		{
			return true;
		}
		else if (obj instanceof Set)
		{
			return true;
		}else if (obj instanceof Array)
		{
			return true;
		}
		return false;
	}
	
	/**��ȡ��ǰ����IP
	 * @return
	 */
	public static String getCurrentPcIp()
	{
		try {
			InetAddress address = InetAddress.getLocalHost();
			return address.getHostAddress();
		} catch (UnknownHostException e) {
			return "";
		}     
	}
	
	/**��ȡexception������Ϣ    
	 * @param e �쳣��Ϣ
	 * @return
	 */
    public static String getExceptionDetail(Exception e) {   
  
       StringBuffer msg = new StringBuffer(10);   
 
       if (e != null) {   
           msg = new StringBuffer("");   
 
           String message = e.toString();   
 
           int length = e.getStackTrace().length;   
 
           if (length > 0) {   
 
               msg.append(message + "\n");   
 
               for (int i = 0; i < length; i++) {   
 
                   msg.append("\t" + e.getStackTrace()[i] + "\n");   
 
               }   
           } else {   
 
               msg.append(message);   
           }   
 
       }   
       return msg.toString();   
   }
    
    /**��ҳ��������ת������Ϊmap
     * @param obj
     * @param request
     * @return
     */
    public static Map<String,Object> fill2Object2Map(Object obj, HttpServletRequest request)
    {
    	fill2Obj(obj, request);
    	return object2Map(obj);
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
