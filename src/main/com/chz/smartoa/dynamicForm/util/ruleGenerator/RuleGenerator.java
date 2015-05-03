package com.chz.smartoa.dynamicForm.util.ruleGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.chz.smartoa.dynamicForm.util.StringUtils;
import com.chz.smartoa.form.constants.FormConstants;
import com.chz.smartoa.global.ServerInfo;

/**
 * (表单记录)统一规则生成器
 * 		日期、序列
 * @author william
 * @22:58 2014/11/21
 */
public class RuleGenerator {
	
	public RuleGenerator(){}
	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(RuleGenerator.class);
	
	static Map<String,String> initParamMap = new LinkedHashMap<String, String>();
	
	/**
	 * 根据规则生成相应字符串
	 * @param srcStr
	 * @param paramMap 参数值：用以替换 srcStr 中占位符
	 * @return 返回生成的规则字符串
	 */
	public static String generateStrByRule(Map<String,Object> param){
		String srcStr = "";
		if(!param.containsKey(IRuleGenerator.RULE_SRC_RULE) || 
				StringUtils.isEmpty(srcStr=String.valueOf(param.get(IRuleGenerator.RULE_SRC_RULE)))// || srcStr.indexOf("{")==-1
				){
			//如果字参数不含有规则表达式，则不需要替换
			return "";
		}
		
		Map<String, String[]> paramMap = (HashMap<String, String[]>) param.get(FormConstants.FLOW_REQUEST_MAP);
		
		/**
		 * 获取所有规则表达式
		 */
		Map<String,String> tempMap = new HashMap<String, String>();
		tempMap.putAll(initParamMap);
		
		/**
		 * 根据规则表达式，得到对应的值
		 */
		try{
			Pattern pattern = Pattern.compile("\\{.+?\\}");
			Matcher matcher = pattern.matcher(srcStr);
			while (matcher.find()) {
				String matchStr = matcher.group();
				String replaceStr=matchStr;
				
				String matchKey = matchStr.substring(1, matchStr.length()-1);
				if(initParamMap.containsKey(matchKey)){
					/**
					 * 1. 替换 原有初始化规则，如“日期”
					 */
					/*Iterator<String> it = initParamMap.keySet().iterator();
					while (it.hasNext()) {
						String key = (String) it.next();
						if(!matchStr.contains(key)){continue;}
						String replaceValue =  getDesStrByRule(srcStr,key,tempMap.get(key));
						
						replaceStr=replaceStr.replaceAll(key,replaceValue);
					}*/
					param.put(IRuleGenerator.RULE_RULE_PATTERN,matchKey);
					String replaceValue =  getDesStrByRule(param,tempMap.get(matchKey));
					replaceStr=replaceStr.replaceAll(matchKey,replaceValue);
				}else{
					/**
					 * 2. 替换动态规则
					 */
					//a. 特殊规则 =====> 如：{proselection-0-2#ZM}-{yyyy-MM-dd} : ZM代表 获取“proselection-0-2”控件对应值的“字母”，默认获取首个字母字符串
					//b. 一般规则
					if(paramMap!=null && paramMap.size()>0){
						for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
							
				            String key = entry.getKey();
				            String[] values = entry.getValue();

							String replaceValue = "";
				            
				            if(matchKey.indexOf("#ZM")>0 && ("xf_f_"+matchKey).equals(key+"#ZM")){
				            	//获取第一个匹配的字母字符串
				        		Pattern patternTmp = Pattern.compile("[a-zA-Z]+");
				        		Matcher matcherTmp = patternTmp.matcher(values[0]);
				        		
				        		String matchStrTmp = "";
				        		int index = 0;
				        		while (matcherTmp.find()) {
				        			if(index==0){
				        				matchStrTmp = matcherTmp.group();
				        				break;
				        			}
				        		}
				        		
				        		replaceValue = matchStrTmp;
								replaceStr=replaceStr.replaceAll(matchKey,replaceValue);
				        		break;
				            }else if(("xf_f_"+matchKey).equals(key)){
				            	replaceValue =  values[0];
								replaceStr=replaceStr.replaceAll(matchKey,replaceValue);
				            }
						}
					}
				}
				
				srcStr=srcStr.replace(matchStr,replaceStr);
				
			}
			srcStr=srcStr.replace("{", "").replace("}", "");
			
			//将最终产生的规则，放入到Map中
			param.put(IRuleGenerator.RULE_FIN_RULE, srcStr);
			
			boolean sequenceFlag = param.containsKey(IRuleGenerator.RULE_SEQ_REQ_FLAG)?
					Boolean.valueOf(String.valueOf(param.get(IRuleGenerator.RULE_SEQ_REQ_FLAG))):false;
			/**
			 * 加上 索引序列
			 */
			if(sequenceFlag){
				srcStr+=getDesStrByRule(param,tempMap.get("sequence"));
				//将最终产生的规则，放入到Map中
				param.put(IRuleGenerator.RULE_FIN_RULE, srcStr);
			}
		}catch(Exception e){
			logger.error("RuleGenerator>>generateStrByRule>>正则解析发生错误："+e.getMessage());
		}
		
		return srcStr;
	}
	/**
	 * 根据  “规则表达式” “实现类”，得到相应的值
	 * @param srcStr
	 * @param pattern
	 * @param handClass
	 * @return
	 */
	public static String getDesStrByRule(Map<String,Object> param,String handClass){
		
		IRuleGenerator ruleGenerator = (IRuleGenerator)ServerInfo.getBean(handClass);
		String desStr = ruleGenerator.generateValByRule(param);
		
		logger.info("RuleGenerator>>getDesStrByRule根据规则得到返回值："+desStr);
		
		return desStr;
	}
	
	public static String reflectRuleGenerator(String param){
		String returnVal = "";
		
		Object obj = null;
		String pack = "com.chz.smartoa.dynamicForm.util.ruleGenerator";
		
		try {
			if (param.indexOf(".") != -1) {
				String className = param.substring(0, param.indexOf("."));
				
				Class clazz = Class.forName(pack+"."+className);
				obj = clazz.newInstance();
				Method generateStrByRule = clazz.getMethod("generateStrByRule", new Class[] { Map.class });
				Object[] arguments = new Object[] { new HashMap<String,String>() };
				returnVal = (String) generateStrByRule.invoke(obj, arguments);
			}else{
				Class clazz = Class.forName(pack+"."+param);
				obj = clazz.newInstance();
				Method generateStrByRule = clazz.getMethod("generateStrByRule");
				returnVal = (String) generateStrByRule.invoke(obj); 
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnVal;
	}
	
	static{
		initParamMap.put("yyyy-MM-dd", "ruleDateGenerator");
		initParamMap.put("yyyyMMdd", "ruleDateGenerator");
		initParamMap.put("HH:mm:ss", "ruleDateGenerator");
		initParamMap.put("yyyy", "ruleDateGenerator");
		initParamMap.put("yy", "ruleDateGenerator");
		initParamMap.put("MM", "ruleDateGenerator");
		initParamMap.put("dd", "ruleDateGenerator");
		initParamMap.put("HH", "ruleDateGenerator");
		initParamMap.put("mm", "ruleDateGenerator");
		initParamMap.put("ss", "ruleDateGenerator");
		initParamMap.put("sequence", "ruleSequenceGenerator");
	}
	
	public static void main(String[] args) {
		String srcStr = "s001-{yyyy-uu_MM-dd}-test-yyyy-MM-dd_{aass-u-mm}-";
		Pattern pattern = Pattern.compile("[a-zA-Z]+");
		Matcher matcher = pattern.matcher(srcStr);
		
		String matchStr = "";
		int index = 0;
		while (matcher.find()) {
			if(index==0){
				matchStr = matcher.group();
				break;
			}
		}
		
		System.out.println("aaaaaaaa:"+ matchStr);
	}
}
