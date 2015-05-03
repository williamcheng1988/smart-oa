package com.chz.smartoa.dynamicForm.util.ruleGenerator;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.chz.smartoa.dynamicForm.service.DynamicFormBiz;
import com.chz.smartoa.dynamicForm.util.StringUtils;


/**
 * 根据前缀查找表中数据
 * @author william
 * @time 3:01 2014/11/21
 */
public class RuleSequenceGenerator implements IRuleGenerator {

	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(RuleSequenceGenerator.class);
	
	private DynamicFormBiz dynamicFormBiz;

	public DynamicFormBiz getDynamicFormBiz() {
		return dynamicFormBiz;
	}
	public void setDynamicFormBiz(DynamicFormBiz dynamicFormBiz) {
		this.dynamicFormBiz = dynamicFormBiz;
	}
	
	@Override
	public String generateValByRule(Map<String,Object> param) {
		if(param==null||param.size()==0 || !param.containsKey(RULE_EXEC_SQL)){
			logger.debug("RuleSequenceGenerator 传入参数为空："+param);
			return "";
		}
		
		String sql = String.valueOf(param.get(RULE_EXEC_SQL));
		//Map<String,Object> queryParamMap = (HashMap<String,Object>)param.get(RULE_EXEC_SQL_PARAM_MAP);
		if(StringUtils.isEmpty(sql)){
			return "";
		}
		
		//查询条件
		//Map<String, String> queryParamMap = new HashMap<String, String>();
		//map.put(DOCNORULE, param.get("")+"%");
		
		int count = dynamicFormBiz.countByParam(sql,param);
		
		return String.format("%04d", ++count);
	}
	
	public static void main(String[] args) {
		System.out.println("1234-5678{90".substring(0, "1234-5678{90".indexOf("{")));
	}
}
