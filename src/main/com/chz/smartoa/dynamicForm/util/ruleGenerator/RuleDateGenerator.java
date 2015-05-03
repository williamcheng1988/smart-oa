package com.chz.smartoa.dynamicForm.util.ruleGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.chz.smartoa.dynamicForm.util.StringUtils;

public class RuleDateGenerator implements IRuleGenerator {

	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(RuleDateGenerator.class);

	@Override
	public String generateValByRule(Map<String,Object> param) {
		if(param==null || param.size()==0 || !param.containsKey(RULE_RULE_PATTERN)){
			logger.debug("RuleDateGenerator 传入参数为空："+param);
			return "";
		}
		
		String pattern = String.valueOf(param.get(RULE_RULE_PATTERN));
		if(StringUtils.isEmpty(pattern)){
			 return pattern;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}
	/**
	 * 获取现在时间
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
		
	}
	/**
	 * 获取现在时间
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}
}
