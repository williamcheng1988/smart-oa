package com.chz.smartoa.form.service.impl;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;

import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.StaffWages;
import com.chz.smartoa.dynamicForm.pojo.Wage;
import com.chz.smartoa.dynamicForm.service.StaffBenefitsBiz;
import com.chz.smartoa.dynamicForm.util.StringUtils;
import com.chz.smartoa.dynamicForm.util.ruleGenerator.IRuleGenerator;
import com.chz.smartoa.dynamicForm.util.ruleGenerator.RuleGenerator;
import com.chz.smartoa.form.constants.FormConstants;
import com.chz.smartoa.form.constants.FormStatus;
import com.chz.smartoa.form.service.CommonFormAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FixedFormAdapter implements CommonFormAdapter {

	
	StaffBenefitsBiz staffBenefitsBiz;
	public StaffBenefitsBiz getStaffBenefitsBiz() {
		return staffBenefitsBiz;
	}
	public void setStaffBenefitsBiz(StaffBenefitsBiz staffBenefitsBiz) {
		this.staffBenefitsBiz = staffBenefitsBiz;
	}
	
	@Override
	public void saveForm(Map paramMap) throws Exception {
		//目前针对工资录入 处理
		Map<String, String[]> requestMap = (Map<String, String[]>) paramMap.get(FormConstants.FLOW_REQUEST_MAP);
		
		if(!requestMap.containsKey(FormConstants.WAGE_WAGES) || requestMap.get(FormConstants.WAGE_WAGES).length==0){
			throw new Exception("工资信息不存在！");
		}
		
		String wageJson = requestMap.get(FormConstants.WAGE_WAGES)[0];
		Gson gson = new GsonBuilder().create();
		
		Wage srcWage = gson.fromJson(wageJson, Wage.class);
		StaffWages srcStaffWage = srcWage.getTotal();
		
		
		String formRecordId = "";
		if(requestMap.containsKey(FormConstants.FORMRECORD_ID) && requestMap.get(FormConstants.FORMRECORD_ID)!=null){
			formRecordId = requestMap.get(FormConstants.FORMRECORD_ID).length>0?
					requestMap.get(FormConstants.FORMRECORD_ID)[0]:"";
		}
		
		/**
		 * 暂存状态：0      由暂存->修改提交状态：1        退回修改提交状态：2
		 */
		//获取模板
		FormTemplate formTemplate = (FormTemplate) paramMap.get(FormConstants.FORMTEMPLATE_OBJ);
		/**
		 * 1. 保存填报数据
		 */
		if(!StringUtils.isEmpty(formRecordId)){
			/**
			 * 更新表单数据
			 */
			Wage tarWage = staffBenefitsBiz.findWageById(new StaffWages(formRecordId));
			if(tarWage ==null || tarWage.getTotal()==null){
				throw new Exception("原工资记录"+FormConstants.FORMRECORD_ID+"["+formRecordId+"]不存在！");
			}
			
			StaffWages tarStaffWages =  tarWage.getTotal();
			
			String uuid = tarStaffWages.getUuid();
			String id = tarStaffWages.getId();
			
			BeanUtils.copyProperties(srcStaffWage, tarStaffWages);
			
			tarStaffWages.setId(id);
			tarStaffWages.setUuid(uuid);
			tarWage.setTotal(tarStaffWages);
			tarWage.setDetail(srcWage.getDetail());
			
			//判断何时生成 ID:如果由暂存->修改提交状态：1，则生成 重新生成规则编号
			String idByRule = "";
			if(paramMap.containsKey(FormConstants.FORM_STATUS) &&
					"1".equals(String.valueOf(paramMap.get(FormConstants.FORM_STATUS)))){
				/**
				 * 封装 参数 Map
				 */
				paramMap.put(IRuleGenerator.RULE_SRC_RULE, formTemplate.getDocNoRule());				//源规则字符串
				paramMap.put(IRuleGenerator.RULE_EXEC_SQL, "Form_listWageRecordCountByDocNoRule");		//执行sql脚本
				paramMap.put(IRuleGenerator.RULE_SEQ_REQ_FLAG, true);									//需要(在规则字符最后)生成序号
				
				//如： sino-{yyyy-MM-dd}-{index}
				//文档编号--按照规则生成
				idByRule = RuleGenerator.generateStrByRule(paramMap);
				tarStaffWages.setId(idByRule);
			}
			
			//记录 更新人
			tarStaffWages.setUpdateUser(String.valueOf(paramMap.get(FormConstants.LOGIN_NAME)));
			
			// 状态----由流程控制状态
			tarStaffWages.setStatus(null==paramMap.get(FormConstants.FORM_STATUS)?
					FormStatus.NONE.value:Integer.valueOf(String.valueOf(paramMap.get(FormConstants.FORM_STATUS))));
			
			//更新数据库
			try{
				staffBenefitsBiz.updateWage(tarWage);
			}catch(Exception e){
				if(e instanceof DuplicateKeyException){
					//如果更新数据出错，则（获取id以后）重新更新
					idByRule = RuleGenerator.generateStrByRule(paramMap);
					tarStaffWages.setId(idByRule);
					staffBenefitsBiz.updateWage(tarWage);
				}
			}
			//返回记录ID
			paramMap.put(FormConstants.FORMRECORD_ID,tarStaffWages.getId());
		}else{
			/**
			 * 新增表单数据
			 */
			if(formTemplate.getId()==null){
				throw new Exception("原记录"+FormConstants.FORMTEMPLATE_OBJ+"不存在！");
			}

			StaffWages srcStaffWages =  srcWage.getTotal();
			
			String idByRule = "";
			if(paramMap.containsKey(FormConstants.FORM_STATUS) &&
					"0".equals(String.valueOf(paramMap.get(FormConstants.FORM_STATUS)))){
				String uuid = UUID.randomUUID().toString();
				srcStaffWages.setUuid(uuid);
				srcStaffWages.setId(uuid);
			}else{
				/**
				 * 封装 参数 Map
				 */
				paramMap.put(IRuleGenerator.RULE_SRC_RULE, formTemplate.getDocNoRule());				//源规则字符串
				paramMap.put(IRuleGenerator.RULE_EXEC_SQL, "Form_listWageRecordCountByDocNoRule");		//执行sql脚本
				paramMap.put(IRuleGenerator.RULE_SEQ_REQ_FLAG, true);									//需要(在规则字符最后)生成序号
				
				//如： sino-{yyyy-MM-dd}-{index}
				//文档编号--按照规则生成
				idByRule = RuleGenerator.generateStrByRule(paramMap);
				srcStaffWages.setId(idByRule);
				
				String uuid = UUID.randomUUID().toString();
				srcStaffWages.setUuid(uuid);
			}
			srcStaffWages.setTemplateId(formTemplate.getId());
			
			//记录 创建人/更新人
			srcStaffWages.setCreateUser(String.valueOf(paramMap.get(FormConstants.LOGIN_NAME)));
			srcStaffWages.setUpdateUser(String.valueOf(paramMap.get(FormConstants.LOGIN_NAME)));
			
			// 状态----由流程控制状态
			srcStaffWages.setStatus(null==paramMap.get(FormConstants.FORM_STATUS)?
					FormStatus.NONE.value:Integer.valueOf(String.valueOf(paramMap.get(FormConstants.FORM_STATUS))));
			
			//更新数据库
			try{
				staffBenefitsBiz.insertWage(srcWage);
			}catch(Exception e){
				if(e instanceof DuplicateKeyException){
					//如果更新数据出错，则（获取id以后）重新更新
					idByRule = RuleGenerator.generateStrByRule(paramMap);
					srcStaffWages.setId(idByRule);
					staffBenefitsBiz.insertWage(srcWage);
				}
			}
			//返回记录ID
			paramMap.put(FormConstants.FORMRECORD_ID,srcStaffWages.getId());
		}
	}

}
