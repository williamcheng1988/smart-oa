package com.chz.smartoa.fileUpload.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;  
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;  
import java.util.Set;

import org.apache.log4j.Logger;


public class PerpertiesTool {
	
	private static final Logger logger = Logger.getLogger(PerpertiesTool.class);

	private static PerpertiesTool pTool = null;
	
	private static Map<String, String> propertiesMap = new HashMap<String, String>();
	
	
	public PerpertiesTool() {
		super();
	}
	
	public static PerpertiesTool getInstance(){
		if(pTool == null){
			pTool = new PerpertiesTool();
		}
		return pTool;
	}
	
	public Map<String, String> getProMap(){
		return propertiesMap;
	}
	
	//** 外部调用推荐些方法(配合PerpertyNames使用) ** 遵循高内聚！！
	public static String getPro(String propertName){
		return getInstance().getProMap().get(propertName);
	}
	
	
	private static Properties properties = null;
	
	@SuppressWarnings("rawtypes")
	public static void init(String confFileName) throws Exception {
		try {
			if (properties == null) {
				properties = new Properties();
				properties.load(new BufferedInputStream(new FileInputStream(confFileName)));
				Set keyValue = properties.keySet();
	            for (Iterator it = keyValue.iterator(); it.hasNext();){
	        	   String key = (String) it.next();
	        	   String value = new String((String) properties.get(key));
	        	   propertiesMap.put(key, value);
	            }
			}
		} catch (Exception e) {
			logger.error("加载配置文件[" + confFileName + "]失败："+e);
			throw new Exception("加载配置文件[" + confFileName + "]失败："+e);
		}
	}
	
}
