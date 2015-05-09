package com.chz.smartoa.form.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.dynamicForm.pojo.Field;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateJsonContent;
import com.chz.smartoa.dynamicForm.pojo.Section;
import com.chz.smartoa.dynamicForm.pojo.StaffBenefits;
import com.chz.smartoa.dynamicForm.pojo.StaffWages;
import com.chz.smartoa.dynamicForm.pojo.Wage;
import com.chz.smartoa.dynamicForm.service.DynamicFormBiz;
import com.chz.smartoa.dynamicForm.service.StaffBenefitsBiz;
import com.chz.smartoa.dynamicForm.util.PublicFunction;
import com.chz.smartoa.dynamicForm.util.RecordBuilder;
import com.chz.smartoa.dynamicForm.util.StringUtils;
import com.chz.smartoa.form.constants.FormConstants;
import com.chz.smartoa.form.service.CommonFormAdapter;
import com.chz.smartoa.form.service.FormBiz;
import com.chz.smartoa.global.ServerInfo;
import com.chz.smartoa.system.constant.DictionaryConstants;
import com.chz.smartoa.system.pojo.DictionaryConfig;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.service.DictionaryConfigBiz;
import com.chz.smartoa.system.service.StaffBiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class FormBizImpl implements FormBiz {
	
	private DynamicFormBiz dynamicFormBiz;
	
	private DictionaryConfigBiz dictionaryConfigBiz;
	
	private StaffBiz staffBiz;
	
	private StaffBenefitsBiz staffBenefitsBiz;
	
	public DynamicFormBiz getDynamicFormBiz() {
		return dynamicFormBiz;
	}

	public void setDynamicFormBiz(DynamicFormBiz dynamicFormBiz) {
		this.dynamicFormBiz = dynamicFormBiz;
	}
	
	public DictionaryConfigBiz getDictionaryConfigBiz() {
		return dictionaryConfigBiz;
	}

	public void setDictionaryConfigBiz(DictionaryConfigBiz dictionaryConfigBiz) {
		this.dictionaryConfigBiz = dictionaryConfigBiz;
	}

	public StaffBiz getStaffBiz() {
		return staffBiz;
	}

	public void setStaffBiz(StaffBiz staffBiz) {
		this.staffBiz = staffBiz;
	}

	public StaffBenefitsBiz getStaffBenefitsBiz() {
		return staffBenefitsBiz;
	}

	public void setStaffBenefitsBiz(StaffBenefitsBiz staffBenefitsBiz) {
		this.staffBenefitsBiz = staffBenefitsBiz;
	}

	@Override
	public Map reachFormTemplate(String templateId) throws Exception {
		return reachFormTemplate(templateId,null);
	}

	@Override
	public Map reachFormTemplate(String templateId,String formRecordId) throws Exception {
		
		if(templateId==null){
			throw new Exception("templateId为空！ ");
		}
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		FormTemplate formTemplate = dynamicFormBiz.findFormTemplate(templateId);
		
		//injectDataDic(formTemplate);
		
		String processName = formTemplate.getProcessName();
		String viewUrl = formTemplate.getViewUrl();
		
		paramMap.put(FormConstants.PROCESS_NAME, URLEncoder.encode(processName));
		paramMap.put(FormConstants.VIEW_URL, viewUrl);
		paramMap.put(FormConstants.FORMTEMPLATE_OBJ, formTemplate);
		paramMap.put(FormConstants.FORMTEMPLATE_NAME, formTemplate.getName());
		paramMap.put(FormConstants.FORMTEMPLATE_TYPE_NAME, formTemplate.getFtType().getType());
		paramMap.put(FormConstants.FORMTEMPLATE_TYPE_DESCRIPTION, formTemplate.getFtType().getDescription());

		
		//如果是工资发放页面
		if("/fixedForm/form/flow-input-form3.html".equals(viewUrl)){
			//表单数据 JSON
			if(!StringUtils.isEmpty(formRecordId)){
				
				Wage wage = staffBenefitsBiz.findWageById(new StaffWages(formRecordId));
				JSONObject js = net.sf.json.JSONObject.fromObject(wage);
				
				paramMap.put(FormConstants.FORMRECORD_JSON, js.toString());
				paramMap.put(FormConstants.FORMRECORD_ID,formRecordId);
				//paramMap.put(FormConstants.FORMRECORD_COUNT, sbs.size()-1);
			}else{
				List<Staff> staffs = staffBiz.listAllStaffs(new Staff());
				JSONArray js = net.sf.json.JSONArray.fromObject(staffs);
				paramMap.put(FormConstants.WAGE_STAFFS,js.toString());
			}
		}else{
			//表单数据 JSON
			if(!StringUtils.isEmpty(formRecordId)){
				FormRecord formRecord = dynamicFormBiz.findForm(formRecordId);
				
				JSONObject js = net.sf.json.JSONObject.fromObject(RecordBuilder.recordToMap(formRecord));
				String json = js.toString();
				paramMap.put(FormConstants.FORMRECORD_JSON, json);
				paramMap.put(FormConstants.FORMRECORD_ID,formRecordId);
				paramMap.put(FormConstants.FORMRECORD_COUNT, formRecord.getCount());
			}
		}
		return paramMap;
	}
	
	public void injectDataDic(FormTemplate ft){
		if(ft==null){
			return;
		}
		String content = ft.getContent();
		
		Gson gson = new GsonBuilder().create();
		FormTemplateJsonContent jsonContent = gson.fromJson(content, FormTemplateJsonContent.class);
		
		List<Section> sections = jsonContent.getSections();
		//取得需要到处的元素名称
		if(sections!=null && sections.size()>0){
			List<Field> fields = null;
			for(Section section: sections){
				//获取类型，判断为  grid，即表格
				String type = section.getType();
				if("grid".equals(type)){
					fields = section.getFields();
					break;
				}
			}
			
			if(fields!=null && fields.size()>0){
				/**
				 * 准备数据
				 */
				List<DictionaryConfig> userSelect = 
						dictionaryConfigBiz.findDictionaryByKey(DictionaryConstants.NORMAL_USERS);
				List<DictionaryConfig> roleSelect = 
						dictionaryConfigBiz.findDictionaryByKey(DictionaryConstants.NORMAL_ROLES);
				
				for(int i=0;i<fields.size();i++){
					Field field = fields.get(i);
					
					//设置自身引用  数据字典项  的数据
					if("select".equals(field.getType())){
						if(!StringUtils.isEmpty(field.getItems())){
							//logger.info("Field "+field.getName()+"items 不为空！");
							continue;
						}
						if(StringUtils.isEmpty(field.getDic())){
							continue;
						}
						List<DictionaryConfig> dics = 
								dictionaryConfigBiz.findDictionaryByKey(field.getDic());
						field.setDicsArr(PublicFunction.transDicToNameValPair(dics));
					}
					//设置 角色数据字典项 数据 
					//设置 用户数据字典项 数据
					if("userselection".equals(field.getType())){
						field.setRolesArr(PublicFunction.transDicToNameValPair(roleSelect));
						field.setUsersArr(PublicFunction.transDicToNameValPair(userSelect));
					}
					//设置设置 用户数据字典项 数据
					if("userpicker".equals(field.getType())){
						field.setUsersArr(PublicFunction.transDicToNameValPair(userSelect));
					}
					//引用类型，所以不用重新装入
					//fields.add(i, field);
				}
			}
		}
		//重新设置到模板对象中
		ft.setContent(gson.toJson(jsonContent,FormTemplateJsonContent.class));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String,Object> saveForm(Map paramMap) throws Exception {
		
		//验证传入参数
		validateParameters(paramMap);
		
		//获取模板ID
		String templateId = String.valueOf(""+paramMap.get(FormConstants.FORMTEMPLATE_ID));
		
		FormTemplate formTemplate = dynamicFormBiz.findFormTemplate(templateId);
		
		String handleClass = formTemplate.getHandleClass();
		
		if(handleClass==null || "".equals(handleClass)){
			throw new Exception("模板HandleClass配置不存在！");
		}
		
		//Spring获得  CommonFormAdapter 实例
		CommonFormAdapter commonFormAdapter = (CommonFormAdapter) ServerInfo.getBean(handleClass);
		
		paramMap.put(FormConstants.FORMTEMPLATE_OBJ, formTemplate);
		
		/**
		 * 保存表单信息
		 */
		commonFormAdapter.saveForm(paramMap);
		
		/**
		 * 保存完成后，返回表单数据
		 */
		
		return paramMap;
	}
	/**
	 * 验证流程传入参数 
	 * @param paramMap
	 * @return boolean
	 * @throws Exception
	 */
	private boolean validateParameters(Map paramMap) throws Exception {
		if(paramMap == null || paramMap.size()<=0){
			throw new Exception("传入paramMap为空！ ");
		}
		if(!paramMap.containsKey(FormConstants.FLOW_REQUEST_MAP)){
			throw new Exception(FormConstants.FLOW_REQUEST_MAP+"为空！");
		}
		if(!paramMap.containsKey(FormConstants.FORMTEMPLATE_ID)){
			throw new Exception(FormConstants.FORMTEMPLATE_ID+"为空！");
		}
		if(!paramMap.containsKey(FormConstants.LOGIN_NAME)){
			throw new Exception(FormConstants.LOGIN_NAME+"为空！");
		}
		if(!paramMap.containsKey(FormConstants.FORMTEMPLATE_OBJ)){
			//throw new Exception(FormConstants.FORMTEMPLATE_OBJ+"为空！");
		}
		return true;
	}

	@Override
	public String getProcessNamebyFtId(String id) throws DataAccessException {
		
		FormTemplate ft = dynamicFormBiz.findFormTemplate(id);
		
		if(ft==null){
			return null;
		}
		
		return ft.getProcessName();
	}
}
