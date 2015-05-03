package com.chz.smartoa.dynamicForm.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.dynamicForm.export.Exportor;
import com.chz.smartoa.dynamicForm.export.exception.ExportErrorException;
import com.chz.smartoa.dynamicForm.pojo.Field;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateJsonContent;
import com.chz.smartoa.dynamicForm.pojo.Section;
import com.chz.smartoa.dynamicForm.service.DynamicFormBiz;
import com.chz.smartoa.dynamicForm.util.PublicFunction;
import com.chz.smartoa.dynamicForm.util.RecordBuilder;
import com.chz.smartoa.dynamicForm.util.StringUtils;
import com.chz.smartoa.dynamicForm.util.ruleGenerator.IRuleGenerator;
import com.chz.smartoa.dynamicForm.util.ruleGenerator.RuleGenerator;
import com.chz.smartoa.form.constants.FormConstants;
import com.chz.smartoa.form.constants.FormStatus;
import com.chz.smartoa.global.ServerInfo;
import com.chz.smartoa.system.action.OperateResult;
import com.chz.smartoa.system.action.ResultEntry;
import com.chz.smartoa.system.constant.DictionaryConstants;
import com.chz.smartoa.system.pojo.DictionaryConfig;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.service.DictionaryConfigBiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 表单管理类
 * @author william
 * @time	12:27 2014/10/4
 * @version 1.0.0
 */
@Controller
public class FormAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 733090671776240029L;

	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(FormAction.class);
	
	//dynamicFormBiz
	DynamicFormBiz dynamicFormBiz;
	
	//formTemplate object
	FormTemplate formTemplate = new FormTemplate();
	
	//formRecord object
	FormRecord formRecord = new FormRecord();
	
	DictionaryConfigBiz dictionaryConfigBiz;
	
	private String saveUrl = null;
	
	private String saveType=null;
	
	private Map formMap = new HashMap();
	
	protected com.chz.smartoa.system.action.ResultEntry result;
	
	List<Long> selectedItem = new ArrayList<Long>();
	
	public com.chz.smartoa.system.action.ResultEntry getResult() {
		return result;
	}
	public void setResult(com.chz.smartoa.system.action.ResultEntry result) {
		this.result = result;
	}
	
	public DynamicFormBiz getDynamicFormBiz() {
		return dynamicFormBiz;
	}

	public void setDynamicFormBiz(DynamicFormBiz dynamicFormBiz) {
		this.dynamicFormBiz = dynamicFormBiz;
	}
	
	public FormTemplate getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(FormTemplate formTemplate) {
		this.formTemplate = formTemplate;
	}

	public DictionaryConfigBiz getDictionaryConfigBiz() {
		return dictionaryConfigBiz;
	}

	public void setDictionaryConfigBiz(DictionaryConfigBiz dictionaryConfigBiz) {
		this.dictionaryConfigBiz = dictionaryConfigBiz;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public FormRecord getFormRecord() {
		return formRecord;
	}

	public void setFormRecord(FormRecord formRecord) {
		this.formRecord = formRecord;
	}

	public String getSaveUrl() {
		return saveUrl;
	}

	public void setSaveUrl(String saveUrl) {
		this.saveUrl = saveUrl;
	}
	
	public List<Long> getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(List<Long> selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * 新增填报记录信息
	 * @return insert-form
	 */
	public String beforeInsert(){
		
		String id = getHttpServletRequest().getParameter("tId");
		
		if(id!=null){
			formTemplate = dynamicFormBiz.findFormTemplate(id);
		}
		
		/**
		 * 加载自定义控件数据
		 */
		injectDataDic(formTemplate);
		
		//this.saveUrl="form!insert.do";
		
		init();
		
		return "insert-form";
	}
	
	/**
	 * 新增填报记录信息
	 * @return
	 */
	public String insert(){
		
		if(isdo!=1){
			return beforeInsert();
		}
		//获取模板
		formTemplate = dynamicFormBiz.findFormTemplate(formRecord.getFormTemplateId());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String[]> requetParaMap = PublicFunction.fetchParamsFromHttpRequest(getHttpServletRequest(),null);
		/**
		 * 封装 参数 Map
		 */
		paramMap.put(FormConstants.FLOW_REQUEST_MAP, requetParaMap);
		paramMap.put(IRuleGenerator.RULE_SRC_RULE, formTemplate.getDocNoRule());				//源规则字符串
		paramMap.put(IRuleGenerator.RULE_EXEC_SQL, "Form_listFormRecordCountByDocNoRule");	//执行sql脚本
		Map<String,Object> queryParamMap = new HashMap<String, Object>();				//sql查询参数map
		paramMap.put(IRuleGenerator.RULE_EXEC_SQL_PARAM_MAP, queryParamMap);					
		paramMap.put(IRuleGenerator.RULE_SEQ_REQ_FLAG, true);								//需要(在规则字符最后)生成序号
		
		//如： sino-{yyyy-MM-dd}-{index}
		String idByRule = RuleGenerator.generateStrByRule(paramMap);
		formRecord.setId(idByRule);
		
		//生成记录数据
		//FormRecord record = formRecord!=null?formRecord:new FormRecord();
		RecordBuilder.build(formRecord,requetParaMap);
		
		Staff staff = getLoginStaff();
		if(staff!=null){
			formRecord.setCreateUser(staff.getLoginName());
			formRecord.setUpdateUser(staff.getLoginName());
		}
		
		try{
			if(StringUtils.isEmpty(this.saveType)){
				this.setErrorMsg("保存失败！保存类型为空！");
				return get();
			}else if("Draft".equalsIgnoreCase(this.saveType)){
				//表单信息保存为：草稿
				formRecord.setStatus(FormStatus.DRAFT.value);
				dynamicFormBiz.insertForm(formRecord);
				this.setSuccessMsg("保存草稿成功!");
			}else if("Submit".equalsIgnoreCase(this.saveType)){
				//表单信息保存为：提交
				formRecord.setStatus(FormStatus.NORMAL.value);
				dynamicFormBiz.insertForm(formRecord);
				this.setSuccessMsg("提交成功!");
			}
		}catch(Exception e){
			if(e instanceof DuplicateKeyException){
				//如果插入数据出错，则（获取id以后）重新插入
				idByRule = RuleGenerator.generateStrByRule(paramMap);
				formRecord.setId(idByRule);
				dynamicFormBiz.insertForm(formRecord);
			}
		}
		
		init();
		
		return get();
	}
	
	public String beforeUpdate(){
		String id = getHttpServletRequest().getParameter("tId");
		
		if(id!=null){
			formTemplate = dynamicFormBiz.findFormTemplate(id);
		}
		
		this.saveUrl="form!update.do";
		
		init();
		
		return "insert-form";
	}
	/**
	 * 更新填报记录信息
	 * @return
	 */
	public String update(){
		logger.debug("进入FormAction>>update>>isdo:"+isdo);
		
		if(isdo!=1){
			return beforeUpdate();
		}
		
		List<String> excludeds = new ArrayList<String>();
		/*excludeds.add("saveType");
		excludeds.add("isdo");
		excludeds.add("formTemplate.id");
		excludeds.add("formRecord.formTemplateId");*/
		
		//生成记录数据
		//FormRecord record = formRecord!=null?formRecord:new FormRecord();
		RecordBuilder.build(formRecord,PublicFunction.fetchParamsFromHttpRequest(getHttpServletRequest(),excludeds));
		
		Staff staff = getLoginStaff();
		if(staff!=null){
			formRecord.setCreateUser(staff.getLoginName());
			formRecord.setUpdateUser(staff.getLoginName());
		}
		
		if(StringUtils.isEmpty(this.saveType)){
			this.setErrorMsg("保存失败！保存类型为空！");
			return get();
		}else if("Draft".equalsIgnoreCase(this.saveType)){
			//表单信息保存为：草稿
			formRecord.setStatus(FormStatus.DRAFT.value);
			dynamicFormBiz.updateForm(formRecord);
			this.setSuccessMsg("保存草稿成功!");
		}else if("Submit".equalsIgnoreCase(this.saveType)){
			//表单信息保存为：提交
			formRecord.setStatus(FormStatus.NORMAL.value);
			dynamicFormBiz.updateForm(formRecord);
			this.setSuccessMsg("提交成功!");
		}
		
		return get();
	}
	
	/**
	 * 显示填报记录详细信息
	 * @return list
	 */
	public String get() {
		
		String id = formRecord!=null?formRecord.getFormTemplateId():
			formTemplate!=null?formTemplate.getId():null;

		String recordId=formRecord.getId();
		
		id = id!=null ? id:getHttpServletRequest().getParameter("tId");
		
		recordId=recordId!=null?recordId:getHttpServletRequest().getParameter("rId");
		
		if(id!=null){
			formTemplate = dynamicFormBiz.findFormTemplate(id);
			
			formRecord = dynamicFormBiz.findForm(recordId);
			
			Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.create();
			
			String json = gson.toJson(RecordBuilder.recordToMap(formRecord));
			
			getHttpServletRequest().setAttribute("json", json);
			
			formMap.put("json", json);
		}
		
		init();
		
		/**
		 * 加载自定义控件数据
		 */
		injectDataDic(formTemplate);
		
		return "view-form";
	}
	/**
	 * 显示模板列表信息
	 * @return list-form-template
	 */
	public String list() {
		
		logger.info("进入FormAction>>list");
		
		/**
		 * ---------------分页处理开始---------------
		 */
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(formTemplate);
		//获取分页数据
		//formTemplates = dynamicFormBiz.listFormTemplate(formTemplate);
		// 得到总记录数, 调用基类方法设置上一页下一页等属性（firstPage,nextPage等）
		setTotalCount(dynamicFormBiz.listFormTemplateCount(formTemplate));
		/**
		 * ---------------分页处理结束---------------
		 */
		
		//初始化方法
		init();
		
		return "list-form-template";
	}
	
	public String exportCSV(){
		String id = getHttpServletRequest().getParameter("tId");
		
		if(id==null){
			this.setSuccessMsg("模板ID为空！");
			return list();
		}
		
		formTemplate = dynamicFormBiz.findFormTemplate(id);
		
		try {
			Exportor csvExportor = (Exportor)ServerInfo.getBean("csvExportor");
			csvExportor.export(getHttpServletResponse(), formTemplate,true);
		} catch (ExportErrorException e) {
			String msg = e.getMessage();
			this.setSuccessMsg(msg);
			return list();
		}
        
		return null;
	}
	
	public String exportExcel(){
		/**
		 * 后续支持多个模板导出
		 */
		/*if(selectedItem==null || selectedItem.size()==0){
			this.setErrorMsg("未选中任何选项，删除失败！");
			return list();
		}*/
		
		String id = getHttpServletRequest().getParameter("tId");
		
		if(id==null){
			this.setSuccessMsg("模板ID为空！");
			return "list-form-template";
		}
		
		formTemplate = dynamicFormBiz.findFormTemplate(id);
		
		try {
			result = new ResultEntry();
			Exportor excelExportor = (Exportor)ServerInfo.getBean("excelExportor");
			excelExportor.export(getHttpServletResponse(),formTemplate,true);
		} catch (ExportErrorException e) {
			formTemplate=null;
			String msg = e.getMessage();
			this.setSuccessMsg(msg);
			return "list-form-template";
		}
        
		return null;
	}
	
	/**
	 * 初始化信息
	 */
	public void init(){
		
		getHttpServletRequest().setAttribute("scopePrefix", getHttpServletRequest().getContextPath());
		getHttpServletRequest().setAttribute("ctx", getHttpServletRequest().getContextPath());
		getHttpServletRequest().setAttribute("locale", getHttpServletRequest().getLocale());
		
		formMap.put("formTemplate", formTemplate);
		formMap.put("formRecord", formRecord);
		formMap.put("saveUrl", saveUrl);
		formMap.put("saveType", saveType);
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
	/**
	 * 填报统计--展示填报详细表格
	 * @return
	 */
	public String showXFTab(){
		String id = getHttpServletRequest().getParameter("tId");
		
		String recordId = getHttpServletRequest().getParameter("rId");
		
		if(id!=null){
			formTemplate = dynamicFormBiz.findFormTemplate(id);
			
			formRecord = dynamicFormBiz.findForm(recordId);
			
			Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.create();
			
			String json = gson.toJson(RecordBuilder.recordToMap(formRecord));
			
			formTemplate.setDescription(json);
		}
		
		entry = formTemplate;
		
		return ENTRY;
	}
	
	public String showDetails(){
		String recordId = getHttpServletRequest().getParameter("rId");
		
		if(StringUtils.isEmpty(recordId)){return null;}
		
		formRecord = dynamicFormBiz.findForm(recordId);
		
		Gson gson = new GsonBuilder()
		.excludeFieldsWithoutExposeAnnotation()
		.create();
		
		String json = gson.toJson(RecordBuilder.recordToMap(formRecord));
		
		operateResult = new OperateResult(1,json);
		//entry = RecordBuilder.recordToMap(formRecord);
		
		return OPER_RESULT;
	}
	
	
	public String handelRequest(){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String[]> requetParaMap = PublicFunction.fetchParamsFromHttpRequest(getHttpServletRequest(),null);
		
		return "";
		
	}
	
}
