package com.chz.smartoa.form.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;

import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.service.DynamicFormBiz;
import com.chz.smartoa.dynamicForm.util.RecordBuilder;
import com.chz.smartoa.dynamicForm.util.StringUtils;
import com.chz.smartoa.dynamicForm.util.ruleGenerator.IRuleGenerator;
import com.chz.smartoa.dynamicForm.util.ruleGenerator.RuleGenerator;
import com.chz.smartoa.form.constants.FormConstants;
import com.chz.smartoa.form.constants.FormStatus;
import com.chz.smartoa.form.service.CommonFormAdapter;
import com.chz.smartoa.system.service.StaffBiz;

public class DynamicFormAdapter implements CommonFormAdapter {
	
	private DynamicFormBiz dynamicFormBiz;
	private StaffBiz staffBiz;

	public DynamicFormBiz getDynamicFormBiz() {
		return dynamicFormBiz;
	}
	public void setDynamicFormBiz(DynamicFormBiz dynamicFormBiz) {
		this.dynamicFormBiz = dynamicFormBiz;
	}
	public StaffBiz getStaffBiz() {
		return staffBiz;
	}
	public void setStaffBiz(StaffBiz staffBiz) {
		this.staffBiz = staffBiz;
	}
	@Override
	public void saveForm(Map paramMap) throws Exception {
		
		if(paramMap == null || paramMap.size()<=0) {
			throw new Exception("传入paramMap为空！ ");
		}
		
		Map<String, String[]> requestMap = (Map<String, String[]>) paramMap.get(FormConstants.FLOW_REQUEST_MAP);
		//项目ID(如果立项)
		String projectId = "";
		
		String formRecordId = "";
		if(requestMap.containsKey(FormConstants.FORMRECORD_ID) && requestMap.get(FormConstants.FORMRECORD_ID)!=null){
			formRecordId = requestMap.get(FormConstants.FORMRECORD_ID).length>0?
					requestMap.get(FormConstants.FORMRECORD_ID)[0]:"";
		}
		
		/**
		 * 暂存状态：0      由暂存->修改提交状态：1        退回修改提交状态：2
		 */
		
		/**
		 * 1. 保存填报数据
		 */
		if(!StringUtils.isEmpty(formRecordId)){
			/**
			 * 更新表单数据
			 */
			//获取模板
			FormTemplate formTemplate = (FormTemplate) paramMap.get(FormConstants.FORMTEMPLATE_OBJ);
			
			FormRecord formRecord = dynamicFormBiz.findFormRecord(formRecordId);
			if(formRecord==null){
				throw new Exception("原记录"+FormConstants.FORMRECORD_ID+"["+formRecordId+"]不存在！");
			}
			
			//判断何时生成 ID:如果由暂存->修改提交状态：1，则生成 重新生成规则编号
			String idByRule = "";
			if(paramMap.containsKey(FormConstants.FORM_STATUS) &&
					"1".equals(String.valueOf(paramMap.get(FormConstants.FORM_STATUS)))){
				/**
				 * 封装 参数 Map
				 */
				paramMap.put(IRuleGenerator.RULE_SRC_RULE, formTemplate.getDocNoRule());				//源规则字符串
				paramMap.put(IRuleGenerator.RULE_EXEC_SQL, "Form_listFormRecordCountByDocNoRule");		//执行sql脚本
				paramMap.put(IRuleGenerator.RULE_SEQ_REQ_FLAG, true);									//需要(在规则字符最后)生成序号
				
				//如： sino-{yyyy-MM-dd}-{index}
				//文档编号--按照规则生成
				idByRule = RuleGenerator.generateStrByRule(paramMap);
				formRecord.setId(idByRule);
			}
			
			//生成记录数据
			RecordBuilder.build(formRecord,requestMap);
			
			//记录 更新人
			formRecord.setUpdateUser(String.valueOf(paramMap.get(FormConstants.LOGIN_NAME)));
			
			// 状态----由流程控制状态
			formRecord.setStatus(null==paramMap.get(FormConstants.FORM_STATUS)?
					FormStatus.NONE.value:Integer.valueOf(String.valueOf(paramMap.get(FormConstants.FORM_STATUS))));
					
			//更新数据库
			try{
				dynamicFormBiz.updateForm(formRecord);
			}catch(Exception e){
				if(e instanceof DuplicateKeyException){
					//如果更新数据出错，则（获取id以后）重新更新
					idByRule = RuleGenerator.generateStrByRule(paramMap);
					formRecord.setId(idByRule);
					dynamicFormBiz.updateForm(formRecord);
				}
			}
			//返回记录ID
			paramMap.put(FormConstants.FORMRECORD_ID,formRecord.getId());
			//如果是立项，返回项目ID
			if(formTemplate.isProject()){
				paramMap.put(FormConstants.PROJECT_ID,formRecord.getId());
				projectId = formRecord.getId();
			}
		}else{
			/**
			 * 新增表单数据
			 */
			//获取模板
			FormTemplate formTemplate = (FormTemplate) paramMap.get(FormConstants.FORMTEMPLATE_OBJ);
			
			if(formTemplate.getId()==null){
				throw new Exception("原记录"+FormConstants.FORMTEMPLATE_OBJ+"不存在！");
			}

			FormRecord formRecord = new FormRecord();
			
			//项目名称--按照规则生成
			paramMap.put(IRuleGenerator.RULE_SRC_RULE, formTemplate.getProNameRule());				//更改源规则
			paramMap.put(IRuleGenerator.RULE_SEQ_REQ_FLAG, false);									//需要(在规则字符最后)生成序号
			String title = RuleGenerator.generateStrByRule(paramMap);
			formRecord.setTitle(title);
			
			//TODO  判断何时生成 ID:如果不是暂存，则按照正常情况去生成 编号，否则让其自动生成UUID
			String idByRule = "";
			if(paramMap.containsKey(FormConstants.FORM_STATUS) &&
					"0".equals(String.valueOf(paramMap.get(FormConstants.FORM_STATUS)))){
				//不处理
			}else{
				/**
				 * 封装 参数 Map
				 */
				paramMap.put(IRuleGenerator.RULE_SRC_RULE, formTemplate.getDocNoRule());				//源规则字符串
				paramMap.put(IRuleGenerator.RULE_EXEC_SQL, "Form_listFormRecordCountByDocNoRule");		//执行sql脚本
				paramMap.put(IRuleGenerator.RULE_SEQ_REQ_FLAG, true);									//需要(在规则字符最后)生成序号
				
				//如： sino-{yyyy-MM-dd}-{index}
				//文档编号--按照规则生成
				idByRule = RuleGenerator.generateStrByRule(paramMap);
				formRecord.setId(idByRule);
			}
			formRecord.setFormTemplateId(formTemplate.getId());
			
			//生成记录数据
			RecordBuilder.build(formRecord,requestMap);
			
			//记录 创建人/更新人
			formRecord.setCreateUser(String.valueOf(paramMap.get(FormConstants.LOGIN_NAME)));
			formRecord.setUpdateUser(String.valueOf(paramMap.get(FormConstants.LOGIN_NAME)));
			
			// 状态----由流程控制状态
			formRecord.setStatus(null==paramMap.get(FormConstants.FORM_STATUS)?
					FormStatus.NONE.value:Integer.valueOf(String.valueOf(paramMap.get(FormConstants.FORM_STATUS))));
			
			//更新数据库
			try{
				dynamicFormBiz.insertForm(formRecord);
			}catch(Exception e){
				if(e instanceof DuplicateKeyException){
					//如果插入数据出错，则（获取id以后）重新插入
					idByRule = RuleGenerator.generateStrByRule(paramMap);
					formRecord.setId(idByRule);
					dynamicFormBiz.insertForm(formRecord);
				}
			}
			//返回记录ID
			paramMap.put(FormConstants.FORMRECORD_ID,formRecord.getId());
			//如果是立项，返回项目ID
			if(formTemplate.isProject()){
				paramMap.put(FormConstants.PROJECT_ID,formRecord.getId());
				projectId = formRecord.getId();
			}
		}
		
		/**
		 * 2. 如果有"项目控件"，则需要修改权限信息
		 */
		handleDatasForRights(requestMap,projectId);
	}
	/**
	 * 修改权限信息
	 * @param requestMap
	 */
	private void handleDatasForRights(Map<String, String[]> parameters,String projectId){
		if(parameters==null || parameters.size()==0){
        	return;
        }

		//key:groupName	value:@0:@1-->@0:角色 @1:用户
		Map<String,String> groupMap = new LinkedHashMap<String,String>();
		
		if(StringUtils.isEmpty(projectId)){
			//循环所有页面录入值，筛选 “项目控件” “人眼选择控件”
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
	            //项目控件:xf_f_proselection
	            String key = entry.getKey();
	            String[] values = entry.getValue();
	            if(key!=null && key.indexOf("xf_f_proselection")>-1){
	            	projectId = values[0];
	            }
	            //人员选择控件(userselection) 	*~xf_f_us_r~@0*		*~xf_f_us_u~@1*
	            if(key!=null && 
	            		(key.contains("~xf_f_us_r~@0") ||
	            				key.contains("~xf_f_us_u~@1"))){
	            	String extensionCode = "";
	    			String groupStr = "";
	            	String[] matchers = key.split("~");
	            	String position = matchers[2];
	            	extensionCode = matchers.length>3?matchers[3]:"exCode";
	    			
	            	if(groupMap.containsKey(extensionCode)){
	            		groupStr = groupMap.get(extensionCode);
	            	}else{
	            		groupStr = "@0:@1";
	            	}
	            	//替换 "@0" "@1"
	            	groupStr = groupStr.replace(position, values[0]);
	            	groupMap.put(extensionCode, groupStr);
	            }
	        }
		}
		
		//如果项目ID不为空,则循环 角色、用户，进行权限修改
		if(StringUtils.isEmpty(projectId)) return;
		
		//a. 先删除权限关联表数据
		staffBiz.deleteStaffRoleByProjectId(projectId);

		for (Map.Entry<String, String> entry : groupMap.entrySet()) {
			String roleAnduser = entry.getValue();
			//如果包含 "@0"或“@1” 符号，证明其中包含为空的值
			if(roleAnduser.contains("@0") || roleAnduser.contains("@1")){
				continue;
			}
			String[] groupValue = roleAnduser.split(":");
			String roleName = groupValue[0];
			String userInfos = groupValue[1];
			
			//b. 根据名称获取相应的ID
			String roleId = staffBiz.getRoleName(roleName);
			String staffId ="";
			if(!StringUtils.isEmpty(roleId) && !StringUtils.isEmpty(userInfos)){
				String[] userArrays = userInfos.split(",");
				for (int i = 0; i < userArrays.length; i++) {
					String tmpStr = userArrays[i];
					int s = tmpStr.indexOf("(");
					int e = tmpStr.indexOf(")");
					staffId = (s>0&&e>0)?tmpStr.substring(s+1,e):"";
					if(StringUtils.isEmpty(staffId)) continue;
					//c. 再插入权限关联表数据
					staffBiz.insertStaffRole(staffId, roleId, projectId);
				}
			}
		}
	}
}
