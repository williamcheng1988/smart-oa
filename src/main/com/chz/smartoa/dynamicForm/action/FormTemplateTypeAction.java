package com.chz.smartoa.dynamicForm.action;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateType;
import com.chz.smartoa.dynamicForm.service.DynamicFormBiz;
import com.chz.smartoa.dynamicForm.util.StringUtils;
import com.chz.smartoa.system.action.OperateResult;
import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.pojo.Resource;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.service.OperateLogBiz;
import com.chz.smartoa.system.service.ResourceBiz;

/**
 * 表单模板类型管理类
 * @author william
 * @time	10:55 2014/9/21
 * @version 1.0.0
 */
@Controller
public class FormTemplateTypeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2529383545980573394L;
	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(FormTemplateTypeAction.class);
	
	//dynamicFormBiz
	DynamicFormBiz dynamicFormBiz;
	
	ResourceBiz resourceBiz;
	
	FormTemplateType formTemplateType = new FormTemplateType();
	
	List<FormTemplateType> formTemplateTypes;
	
	private OperateLogBiz operateLogBiz;
	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}
	
	private List<String> typeIds = new ArrayList<String>();
	private String entityKeys;
	private String entityKey;

	public DynamicFormBiz getDynamicFormBiz() {
		return dynamicFormBiz;
	}
	public void setDynamicFormBiz(DynamicFormBiz dynamicFormBiz) {
		this.dynamicFormBiz = dynamicFormBiz;
	}
	public ResourceBiz getResourceBiz() {
		return resourceBiz;
	}
	public void setResourceBiz(ResourceBiz resourceBiz) {
		this.resourceBiz = resourceBiz;
	}
	public FormTemplateType getFormTemplateType() {
		return formTemplateType;
	}
	public void setFormTemplateType(FormTemplateType formTemplateType) {
		this.formTemplateType = formTemplateType;
	}
	public List<FormTemplateType> getFormTemplateTypes() {
		return formTemplateTypes;
	}
	public void setFormTemplateTypes(List<FormTemplateType> formTemplateTypes) {
		this.formTemplateTypes = formTemplateTypes;
	}
	public List<String> getTypeIds() {
		return typeIds;
	}
	public void setTypeIds(List<String> typeIds) {
		this.typeIds = typeIds;
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
	/**
	 * 新增模板信息
	 * @return insert-form-template
	 */
	public String beforeInsert(){
		
		return "insert-form-template-type";
	}
	
	/**
	 * 新增模板类型信息
	 * @return 
	 */
	public String insert(){
		logger.debug("进入FormTemplateTypeAction>>insert>>isdo:"+isdo);
		if(isdo!=1){
			return beforeInsert();
		}
		
		/**
		 * 验证是否已经存在该模板
		 */
		FormTemplateType ftt = new FormTemplateType();
		ftt.setType(formTemplateType.getType());
		
		if(dynamicFormBiz.findFormTemplateType(ftt)!=null){
			operateLogBiz.info(OperateLogType.FORM_TEMP_TYPE,"",formTemplateType.getType(), "新增失败-已存在");
			operateResult = new OperateResult(-1, "该模板类型己存在!");
			return OPER_RESULT;
		}
		
		Staff staff = getLoginStaff();
		if(staff!=null){
			formTemplateType.setCreateUser(staff.getLoginName());
			formTemplateType.setUpdateUser(staff.getLoginName());
		}
		formTemplateType.setId(UUID.randomUUID().toString());
		//调用插入数据方法
		dynamicFormBiz.insertFormTemplateType(formTemplateType);

		operateLogBiz.info(OperateLogType.FORM_TEMP_TYPE,formTemplateType.getId(),formTemplateType.getType(), "新增成功");
		
		operateResult = new OperateResult(1, "添加模板类型成功！");
		
		//返回模板列表页面
		return OPER_RESULT;
	}
	
	/**
	 * 删除模板类型信息
	 * @return list()
	 */
	public String delete(){
		if(StringUtils.isEmpty(entityKeys)){
			operateResult = new OperateResult(-1, "未选中相应的模板！");
			return OPER_RESULT;
		}
		
		String[] formTemplateIds = entityKeys.split(",");
		
		boolean canDelete = true;
		for (String id : formTemplateIds) {
			FormTemplate ft = new FormTemplate();
			ft.setTypeId(id);
			if(dynamicFormBiz.listFormTemplateCount(ft)>0){
				canDelete = false;
				operateResult = new OperateResult(-1, "删除失败！ 该模板类型ID["+id+"]已被引用,暂无法删除!");
				operateLogBiz.info(OperateLogType.FORM_TEMP_TYPE,id,id, "删除失败-已被引用");
				break;
			}
			typeIds.add(id);
		}
		if(!canDelete){
			return OPER_RESULT;
		}
		dynamicFormBiz.deleteFormTemplateType(typeIds);
		operateLogBiz.info(OperateLogType.FORM_TEMP_TYPE,entityKeys,entityKeys, "删除成功");
		operateResult = new OperateResult(1, "删除模板类型成功！");
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
		
		FormTemplateType type = new FormTemplateType();
		type.setId(entityKey);
		
		formTemplateType = dynamicFormBiz.findFormTemplateType(type);
		
		return "update-form-template-type";
	}
	
	/**
	 * 修改模板类型信息
	 * @return 
	 */
	public String update(){
		logger.debug("进入FormTemplateTypeAction>>update>>isdo:"+isdo);
		
		if(isdo!=1){
			return beforeUpdate();
		}
		/**
		 * 验证是否 已经存在与当前需要更新的模板类型名称一样的数据
		 */
		FormTemplateType ftt = new FormTemplateType();
		ftt.setType(formTemplateType.getType());
		FormTemplateType fttExist = dynamicFormBiz.findFormTemplateType(ftt);
		if(fttExist!=null && (!fttExist.getId().equals(formTemplateType.getId()))){
			operateLogBiz.info(OperateLogType.FORM_TEMP_TYPE,formTemplateType.getId(),formTemplateType.getType(), "更新失败-该模板类型己存在!");
			operateResult = new OperateResult(-1, "该模板类型己存在!");
			return OPER_RESULT;
		}
		
		//调用更新数据方法
		dynamicFormBiz.updateFormTemplateType(formTemplateType);
		operateLogBiz.info(OperateLogType.FORM_TEMP_TYPE,formTemplateType.getId(),formTemplateType.getType(), "更新成功");
		operateResult = new OperateResult(1, "更新模板类型成功！");
		
		//返回模板列表页面
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
		logger.info("进入FormTemplateTypeAction>>list");
		
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(formTemplateType);
		//获取分页数据
		formTemplateTypes = dynamicFormBiz.listFormTemplateType(formTemplateType);
		// 封装数据
		dataGrid = new DataGrid(dynamicFormBiz.listFormTemplateTypeCount(formTemplateType), formTemplateTypes);
		return DATA_GRID;
	}
}
