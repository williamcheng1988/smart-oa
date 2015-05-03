package com.chz.smartoa.dynamicForm.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.chz.smartoa.dynamicForm.pojo.NameValPair;
import com.chz.smartoa.system.pojo.DictionaryConfig;

public class PublicFunction {
	
	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(PublicFunction.class);
	
	private static String FORM_RECORD_NAME_PREFIX="xf_f_"; 
	
	/**
	 * 获取请求的所有参数
	 * @param request : HttpServletRequest 对象
	 * @param excludeds : 排除元素
	 * @return parameters
	 */
	public static Map<String, String[]> fetchParamsFromHttpRequest(HttpServletRequest request,List<String> excludeds){
		 Map<String, String[]> parameters = new HashMap<String, String[]>();
		Enumeration e = (Enumeration)request.getParameterNames();
		while(e.hasMoreElements()){
			String key = (String) e.nextElement();
			/**
			 * 筛选掉不需要存入的记录
			 */
			if(excludeds!=null && excludeds.contains(key)){
				continue;
			}
			
			/**
			 * TODO  待xform 脚本文件写完以后更新代码
			 * 筛选需要存入的记录
			 */
			if (key.startsWith(FORM_RECORD_NAME_PREFIX)) {
				String[] value = request.getParameterValues(key);
				
				logger.debug("参数==>key:" + key + "，value:" + value);
				
				parameters.put(key, value);
			}
		}
		
		return parameters;
	}
	/**
	 * 通过传入 paramMap ，筛选出需要保存到表单记录的数据
	 * @param paramMap
	 * @return parameters
	 */
	public static Map<String, String[]> fetchParams(Map<String,Object> paramMap){
		//需要返回的map集合
		Map<String,String[]> parameters = new LinkedHashMap<String, String[]>();
		for(Map.Entry<String,Object> entry: paramMap.entrySet()){
			String key = entry.getKey();
			
			if (key.startsWith(FORM_RECORD_NAME_PREFIX)) {
				
				String[] value = (String[]) entry.getValue();
				
				logger.debug("参数==>key:" + key + "，value:" + value);
				
				parameters.put(key, value);
			}
		}
		return parameters;
	}
	
	public static NameValPair[] transDicToNameValPair(List<DictionaryConfig> dictionaries){
		if(dictionaries==null || dictionaries.size()==0){
			return new NameValPair[0];
		}
		NameValPair[] pairs = new NameValPair[dictionaries.size()];
		for(int i=0;i<dictionaries.size();i++){
			DictionaryConfig dic =dictionaries.get(i);
			NameValPair pair = new NameValPair();
			pair.setName(dic.getDictionaryName());
			pair.setValue(dic.getDictionaryKey());
			
			pairs[i] = pair;
		}
		
		return pairs;
	}
}
