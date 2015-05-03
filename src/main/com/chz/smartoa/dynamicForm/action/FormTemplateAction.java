package com.chz.smartoa.dynamicForm.action;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.dynamicForm.pojo.Field;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateJsonContent;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateType;
import com.chz.smartoa.dynamicForm.pojo.NameValPair;
import com.chz.smartoa.dynamicForm.pojo.Section;
import com.chz.smartoa.dynamicForm.service.DynamicFormBiz;
import com.chz.smartoa.dynamicForm.util.PublicFunction;
import com.chz.smartoa.dynamicForm.util.StringUtils;
import com.chz.smartoa.form.constants.FormStatus;
import com.chz.smartoa.system.action.OperateResult;
import com.chz.smartoa.system.action.ResultEntry;
import com.chz.smartoa.system.constant.DictionaryConstants;
import com.chz.smartoa.system.pojo.DictionaryConfig;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.service.DictionaryConfigBiz;
import com.chz.smartoa.system.service.ResourceBiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 表单模板管理类
 * @author william
 * @time	10:55 2014/9/21
 * @version 1.0.0
 */
@Controller
public class FormTemplateAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2529383545980573394L;
	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(FormTemplateAction.class);
	
	//dynamicFormBiz
	DynamicFormBiz dynamicFormBiz;
	
	DictionaryConfigBiz dictionaryConfigBiz;
	
	ResourceBiz resourceBiz;
	
	//formTemplate object
	FormTemplate formTemplate = new FormTemplate();
	
	//formTemplates object
	List<FormTemplate> formTemplates=null;
	
	List<String> selectedItem = new ArrayList<String>();
	private String entityKeys;
	private String entityKey;
	
	List<FormTemplateType> templateTypes = new ArrayList<FormTemplateType>();
	
	protected com.chz.smartoa.system.action.ResultEntry result;
	
	String jsonDataType = "";
	//数据字典 key
	private String dicKey = "";
	
	public com.chz.smartoa.system.action.ResultEntry getResult() {
		return result;
	}
	public void setResult(com.chz.smartoa.system.action.ResultEntry result) {
		this.result = result;
	}
	public List<String> getSelectedItem() {
		return selectedItem;
	}
	public void setSelectedItem(List<String> selectedItem) {
		this.selectedItem = selectedItem;
	}
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
	public ResourceBiz getResourceBiz() {
		return resourceBiz;
	}
	public void setResourceBiz(ResourceBiz resourceBiz) {
		this.resourceBiz = resourceBiz;
	}
	public FormTemplate getFormTemplate() {
		return formTemplate;
	}
	public void setFormTemplate(FormTemplate formTemplate) {
		this.formTemplate = formTemplate;
	}
	public List<FormTemplate> getFormTemplates() {
		return formTemplates;
	}
	public void setFormTemplates(List<FormTemplate> formTemplates) {
		this.formTemplates = formTemplates;
	}
	public List<FormTemplateType> getTemplateTypes() {
		return templateTypes;
	}
	public void setTemplateTypes(List<FormTemplateType> templateTypes) {
		this.templateTypes = templateTypes;
	}
	public String getJsonDataType() {
		return jsonDataType;
	}
	public void setJsonDataType(String jsonDataType) {
		this.jsonDataType = jsonDataType;
	}
	public String getEntityKeys() {
		return entityKeys;
	}
	public void setEntityKeys(String entityKeys) {
		this.entityKeys = entityKeys;
	}
	public String getEntityKey() {
		return entityKey;
	}
	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}
	public String getDicKey() {
		return dicKey;
	}
	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}
	/**
	 * 新增模板信息
	 * @return insert-form-template
	 */
	public String beforeInsert(){
		init();
		
		return "input-form-template";
	}
	
	/**
	 * 新增模板信息
	 * @return list()
	 */
	public String insert(){
		logger.debug("进入FormTemplateAction>>insert>>isdo:"+isdo);
		
		if(isdo!=1){
			return beforeInsert();
		}
		
		/**
		 * 验证必要信息
		 */
		if(formTemplate!=null){
			String name=formTemplate.getName();
			String content=formTemplate.getContent();
			String id = UUID.randomUUID().toString();
			if(!StringUtils.isEmpty(name) &&
					!StringUtils.isEmpty(content)){
				Staff staff = getLoginStaff();
				if(staff!=null){
					formTemplate.setCreateUser(staff.getLoginName());
					formTemplate.setUpdateUser(staff.getLoginName());
				}
				formTemplate.setId(id);
				//设置默认值  数据库已经有默认字段值
				formTemplate.setHandleClass("dynamicFormAdapter");
				formTemplate.setViewUrl("/dynamicForm/form/flow-input-form.html");
				formTemplate.setDynamicForm("T");
				//新增模板记录
				dynamicFormBiz.insertFormTemplate(formTemplate);
				
				operateResult =  new OperateResult(1, "添加模板成功！");
			}else{
				operateResult =  new OperateResult(-1, "添加模板失败！");
			}
		}else{
			operateResult =  new OperateResult(-1, "添加模板失败！");
		}
		
		return OPER_RESULT;
	}
	
	/**
	 * 删除模板信息
	 * @return list()
	 */
	public String delete(){
		if(StringUtils.isEmpty(entityKeys)){
			operateResult = new OperateResult(-1, "未选中相应的数据！");
			return OPER_RESULT;
		}
		String[] formTemplateIds = entityKeys.split(",");
		
		boolean canDelete = true;
		for (String id : formTemplateIds) {
			//FormRecord record = new FormRecord();
			//record.setFormTemplateId(id);
			if(dynamicFormBiz.listFormRecordCount(id)>0){
				canDelete = false;
				operateResult = new OperateResult(-1, "删除失败！ 该模板["+id+"]已被引用,暂无法删除!");
				break;
			}
			selectedItem.add(id);
		}
		if(!canDelete){
			return OPER_RESULT;
		}
		
		dynamicFormBiz.deleteFormTemplates(selectedItem,true);
		operateResult = new OperateResult(1, "删除模板成功！");
				
		return OPER_RESULT;
	}
	
	/**
	 * 修改模板信息
	 * @return update-form-template
	 */
	public String beforeUpdate(){
		
		// 参数检查
		if (StringUtils.isEmpty(entityKey)) {
			setErrorMsg("请选择需要修改的模板类型!");
			return "commonFailure";
		}
		
		formTemplate = dynamicFormBiz.findFormTemplate(entityKey);
		
		init(); 
		
		//injectDataDic(formTemplate);
		
		return "input-form-template";
	}
	
	/**
	 * 修改模板信息
	 * @return list()
	 */
	public String update(){
		logger.debug("进入FormTemplateAction>>update>>isdo:"+isdo);
		
		if(isdo!=1){
			return beforeUpdate();
		}
		
		try{
			//发起用户
			Staff staff = getLoginStaff();
			if(formTemplate!=null && staff!=null){
				formTemplate.setUpdateUser(staff.getLoginName());
			}
			
			//更新记录
			dynamicFormBiz.updateFormTemplate(formTemplate);
			
			operateResult =  new OperateResult(1, "更新模板成功！");
		}catch(Exception e){
			operateResult =  new OperateResult(-1, "更新模板失败！");
		}
		
		return OPER_RESULT;
	}
	
	/**
	 * 显示模板详细信息
	 * @return list
	 */
	public String get() {
		
		return null;
	}
	/**
	 * 显示模板列表信息
	 * @return list-form-template
	 */
	public String list() {
		
		logger.info("进入FormTemplateAction>>list");
		
		if(templateTypes==null || templateTypes.size()==0){
			templateTypes = dynamicFormBiz.listFormTemplateType(new FormTemplateType());
		}
		
		return "list-form-template";
	}
	
	public String listData() {
		logger.info("进入FormTemplateAction>>listData");
		
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(formTemplate);
		//获取分页数据
		formTemplates = dynamicFormBiz.listFormTemplate(formTemplate);
		// 封装数据
		dataGrid = new DataGrid(dynamicFormBiz.listFormTemplateCount(formTemplate), formTemplates);
		return DATA_GRID;
	}
	
	public String export(){
		
		String content = null; 
		
		Gson gson = new GsonBuilder().create();
		//使用Type类，取得相应类型对象的class属性。
		//TypeToken内的泛型就是Json数据中的类型
		//Type listType=new TypeToken<ArrayList<Section>>(){}.getType();
		//使用该class属性，获取的对象均是list类型的
		FormTemplateJsonContent o = gson.fromJson(content, FormTemplateJsonContent.class);
		
		return null;
	}
	
	public String getModuleInfo(){
		
		try{
			String jsonDatas = "";
			
			if("formTemplate".equals(jsonDataType)){
				jsonDatas = getFormTemplateJson();
			}else if("dataDictionary".equals(jsonDataType)){
				jsonDatas = getDataDictionaryJson();
			}
			
			logger.info("获取返回jsonDatas："+jsonDatas);
			
			//设置返回消息
			//result = new ResultEntry(1, "成功获取模板信息！",jsonDatas);
			
			getHttpServletResponse().getWriter().write(jsonDatas); 
		}catch(Exception e){
			String errMsg = "异步获取模板信息出错："+e.getMessage();
			result = new ResultEntry(-4, errMsg);
			logger.error(errMsg);
		}
		
		return null;
	}
	
	private String getFormTemplateJson(){
		/**
		 * ---------------分页处理开始---------------
		 */
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(formTemplate);
		//获取分页数据
		formTemplates = dynamicFormBiz.listFormTemplate(formTemplate);
		// 得到总记录数, 调用基类方法设置上一页下一页等属性（firstPage,nextPage等）
		setTotalCount(dynamicFormBiz.listFormTemplateCount(formTemplate));
		/**
		 * ---------------分页处理结束---------------
		 */
		Gson gson = new GsonBuilder().create();
		
		String jsonDatas = gson.toJson(formTemplates);
		
		return jsonDatas;
	}
	
	private String getDataDictionaryJson(){
		DictionaryConfig dic = dictionaryConfigBiz.findDictionarytCfgByKey("NORMAL_USERS");
		
		List<DictionaryConfig> dics = new ArrayList<DictionaryConfig>();
		dics.add(dic);
		
		Gson gson = new GsonBuilder().create();
		
		String jsonDatas = gson.toJson(dics);
		
		return jsonDatas;
	}
	
	/**
	 * 初始化信息
	 */
	public void init(){
		//模板类型数据
		if(templateTypes==null || templateTypes.size()==0){
			templateTypes = dynamicFormBiz.listFormTemplateType(new FormTemplateType());
		}
		/**
		 * 数据字典信息获取
		 */
		injectDataDic();
	}
	
	public void injectDataDic(){

		Gson gson = new GsonBuilder().create();
		/**
		 * 数据字典所有列表
		 */
		List<NameValPair> allDicsWithNameVal = dictionaryConfigBiz.listDictionaryFirstLevel();
		String json_allDicsWithNameVal = gson.toJson(allDicsWithNameVal);
		//logger.info("生成json。。。"+json_allDicsWithNameVal);
		/**
		 * 数据字典“流程”数据
		 */
		List<DictionaryConfig> dicsProcdef = 
				dictionaryConfigBiz.findDictionaryByKey(DictionaryConstants.PROCESS_PROCDEF);
		NameValPair[] nameValPair = PublicFunction.transDicToNameValPair(dicsProcdef);

		//logger.info("生成json。。。"+json_allDicsWithNameVal);
		
		getHttpServletRequest().setAttribute("json_allDicsWithNameVal", json_allDicsWithNameVal);
		getHttpServletRequest().setAttribute("dicsProcdef", nameValPair);
		
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
				List<NameValPair> allDicsWithNameVal = dictionaryConfigBiz.listDictionaryFirstLevel();
				
				for(int i=0;i<fields.size();i++){
					Field field = fields.get(i);
					//设置  数据字典列表
					if("select".equals(field.getType()) && allDicsWithNameVal!=null && 
							allDicsWithNameVal.size()>0){
						NameValPair[] strings = new NameValPair[allDicsWithNameVal.size()];
						field.setDicsLs(allDicsWithNameVal.toArray(strings));
					}
				}
			}
		}
		//重新设置到模板对象中
		ft.setContent(gson.toJson(jsonContent,FormTemplateJsonContent.class));
	}
	
	public String fetchRoles(){
		List<DictionaryConfig> roleSelect = 
				dictionaryConfigBiz.findDictionaryByKey(DictionaryConstants.NORMAL_ROLES_PROJECTS);
		
		NameValPair[] roles= PublicFunction.transDicToNameValPair(roleSelect);
		
		entry = roles;
		
		return ENTRY;
	}
	
	public String fetchUsers(){
		List<DictionaryConfig> userSelect = 
				dictionaryConfigBiz.findDictionaryByKey(DictionaryConstants.NORMAL_USERS);
		
		NameValPair[] users= PublicFunction.transDicToNameValPair(userSelect);
		
		if(users!=null && users.length>0){
			NameValPair[] usersTmp = new NameValPair[users.length];
			for (int i = 0; i < users.length; i++) {
				NameValPair pair = users[i];
				String name = pair.getName();
				String value = pair.getValue();
				pair.setName(name+"("+value+")");
				
				usersTmp[i] = pair;
			}
			users = usersTmp;
		}
		
		entry = users;
		
		return ENTRY;
	}
	
	/**
	 * 查询所有项目
	 * @return
	 */
	public String listProjects(){
		
		List<FormRecord> formRecords = dynamicFormBiz.listProjects();
		
		if(formRecords!=null && formRecords.size()>0){

			NameValPair[] projects = new NameValPair[formRecords.size()];
			for (int i = 0; i < formRecords.size(); i++) {
				NameValPair project = new NameValPair();
				project.setValue(formRecords.get(i).getId());
				project.setName(formRecords.get(i).getTitle());
				projects[i] = project;
			}
			
			entry = projects;
		}else{
			entry =  new NameValPair[0];
		}
		
		return ENTRY;
	}
	
	/**
	 * 查询所有项目
	 * @return
	 */
	public String listDictionarys(){
		try{
			if(!StringUtils.isEmpty(dicKey)){
				List<DictionaryConfig> dictionaries = 
						dictionaryConfigBiz.findDictionaryByKey(dicKey);
				
				NameValPair[] nameValues= PublicFunction.transDicToNameValPair(dictionaries);
				
				entry = nameValues;
			}
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		
		return ENTRY;
	}
}
