package com.chz.smartoa.dynamicForm.util.ruleGenerator;

import java.util.Map;

/**
 * 规则生成器接口
 * @author william
 * @time 3:07 2014/11/21
 */
public interface IRuleGenerator {
	/**
	 * +++++++++++++++++++++++++++++++++++++++++++++
	 * -----------	规则生成器常量	start-----------
	 * +++++++++++++++++++++++++++++++++++++++++++++
	 */
	/**源规则**/
	public final static String RULE_SRC_RULE ="rule_srcRule";
	/**最终规则**/
	public final static String RULE_FIN_RULE ="rule_finalRule";
	/**运行SQL**/
	public final static String RULE_EXEC_SQL ="rule_execSql";
	/**查询SQL参数**/
	public final static String RULE_EXEC_SQL_PARAM_MAP ="rule_execSqlParamMap";
	/**索引序列 标识,值选项：true:需要生成序号/false：不需要生成序号**/
	public final static String RULE_SEQ_REQ_FLAG ="rule_sequenceRequiredFlag";
	/**匹配模式**/
	public final static String RULE_RULE_PATTERN ="rule_rulePattern";
	
	/**
	 * ****************************
	 * SQL ID 常量： 根据该SQL查询记录数
	 * ****************************
	 */
	//文件规则 SQL
	public final static String RULE_DOCNORULE_SQL ="Form_listFormRecordCountByDocNoRule";
	/**
	 * +++++++++++++++++++++++++++++++++++++++++++++
	 * -----------	规则生成器常量	end-----------
	 * +++++++++++++++++++++++++++++++++++++++++++++
	 */
	
	/**
	 * param[0]:原字符串
	 * param[1]:规则符号
	 * @param param
	 * @return 返回替换以后的整个字符串
	 */
	public String generateValByRule(Map<String,Object> param);
	
}
