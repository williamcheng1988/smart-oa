package com.chz.smartoa.dynamicForm.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.common.base.TreeData;
import com.chz.smartoa.dynamicForm.export.Exportor;
import com.chz.smartoa.dynamicForm.export.TableModel;
import com.chz.smartoa.dynamicForm.export.constant.FixedExpConstants;
import com.chz.smartoa.dynamicForm.export.exception.ExportErrorException;
import com.chz.smartoa.dynamicForm.pojo.Column;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateType;
import com.chz.smartoa.dynamicForm.pojo.StaffBenefits;
import com.chz.smartoa.dynamicForm.pojo.StaffWages;
import com.chz.smartoa.dynamicForm.pojo.Wage;
import com.chz.smartoa.dynamicForm.service.DynamicFormBiz;
import com.chz.smartoa.dynamicForm.service.StaffBenefitsBiz;
import com.chz.smartoa.dynamicForm.util.ReflectUtils;
import com.chz.smartoa.form.constants.FormStatus;
import com.chz.smartoa.global.ServerInfo;
import com.chz.smartoa.system.action.OperateResult;
import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.service.OperateLogBiz;

/**
 * 表单管理类
 * @author william
 * @time	12:27 2014/10/4
 * @version 1.0.0
 */
@Controller
public class FormStatisticsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 733090671776240029L;

	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(FormStatisticsAction.class);
	
	private String entityKey;
	
	FormTemplate formTemplate = new FormTemplate();
	FormRecord formRecord = new FormRecord();
	//dynamicFormBiz
	DynamicFormBiz dynamicFormBiz;
	StaffBenefitsBiz staffBenefitsBiz;
	
	private OperateLogBiz operateLogBiz;
	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}
	public StaffBenefitsBiz getStaffBenefitsBiz() {
		return staffBenefitsBiz;
	}
	public void setStaffBenefitsBiz(StaffBenefitsBiz staffBenefitsBiz) {
		this.staffBenefitsBiz = staffBenefitsBiz;
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
	public FormRecord getFormRecord() {
		return formRecord;
	}
	public void setFormRecord(FormRecord formRecord) {
		this.formRecord = formRecord;
	}
	public String getEntityKey() {
		return entityKey;
	}
	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}
	
	
	public String treeDataFormTempate(){
		logger.info("FormStatisticsAction>>treeDataFormTempate...");
		
		FormTemplate formTemplate = new FormTemplate();
		formTemplate.setStatus(FormStatus.NORMAL.value);
		formTemplate.setTypeStatus(FormStatus.NORMAL.value);
		Map<FormTemplateType,List<FormTemplate>> templateMap = dynamicFormBiz.listFormTemplateByTypeSeq(formTemplate);
		List<TreeData> tree = new ArrayList<TreeData>();
		for (Map.Entry<FormTemplateType,List<FormTemplate>> entry : templateMap.entrySet()) {
			FormTemplateType ftt = entry.getKey();
			TreeData data = new TreeData(String.valueOf(ftt.getId()),ftt.getType(),false);
			data.setChildren(getTreeDataFormTem(entry.getValue()));
			tree.add(data);
		}
		treeData = tree;
		
		return TREE_DATA;
	}
	
	private List<TreeData> getTreeDataFormTem(List<FormTemplate> fts){
		List<TreeData> tree = new ArrayList<TreeData>();
		for (FormTemplate ft : fts) {
			TreeData data = new TreeData(String.valueOf(ft.getId()),ft.getName(),true);
			//data.setState("closed");
			tree.add(data);
		}
		return tree;
	}
	
	public String displayExpDatas(){
		
		if(formRecord.getFormTemplateId()==null){
			
			return ENTRY;
		}
		
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(formRecord);
		
		formTemplate = dynamicFormBiz.findFormTemplate(formRecord.getFormTemplateId());
		formTemplate.setRecord(formRecord);
		
		int count = 0;
		
		
		if("/fixedForm/form/flow-input-form3.html".equals(formTemplate.getViewUrl())){
			try{
				Map<String,String> headers1 = FixedExpConstants.STAFF_BEN;
		        Map<String,String> headers2 = FixedExpConstants.STAFF_WAGE;
		        
		        FormRecord formRecord = formTemplate.getRecord();
		    	String tmpId = formTemplate.getId();
		    	
		    	StaffWages staffWage = new StaffWages();
		    	staffWage.setTemplateId(tmpId);
		    	List<Wage> wages = staffBenefitsBiz.findWagesByTmpId(staffWage);
	
				List columns = new ArrayList();
				List rows = new ArrayList();
		    	
		    	List<Column> columnsTmp = new ArrayList<Column>();

				//列头
				for (Map.Entry<String, String> entry:headers2.entrySet()) {
					String key = entry.getKey()+"_C";
					String value = entry.getValue();
					columnsTmp.add(new Column(key,value,"100px"));
		        }
				//列头
				for (Map.Entry<String, String> entry:headers1.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					columnsTmp.add(new Column(key,value,"100px"));
		        }
				columns.add(columnsTmp);
				
				// 数据
				if(wages!=null && wages.size()>0){
					for (int i=0;i<wages.size();i++) {
						Wage myWage = wages.get(i);
						StaffWages total = myWage.getTotal();
			        	List<StaffBenefits> detail = myWage.getDetail();
			        	
			        	Map<String,String> values = null;
			        	if(detail!=null&&detail.size()>0){
				        	for(StaffBenefits ben : detail){
				        		values = new HashMap<String, String>();
				        		//数据
			            		for (Map.Entry<String, String> entry:headers1.entrySet()) {
			                 		String methodName = ReflectUtils.getGetterMethodName(ben, entry.getKey());
			                        Object value = ReflectUtils.getMethodValue(ben, methodName);
	
			                        String val = (value == null) ? "" : value.toString();
			                        values.put(entry.getKey(), val);
			                    }
			            		
			            		//数据
			            		for (Map.Entry<String, String> entry:headers2.entrySet()) {
			                 		String methodName = ReflectUtils.getGetterMethodName(total, entry.getKey());
			                        Object value = ReflectUtils.getMethodValue(total, methodName);
	
			                        String val = (value == null) ? "" : value.toString();
			                        values.put(entry.getKey()+"_C", val);
			                    }
			            		
			            		rows.add(values);
				        	}
			        	}
					}
				}
				
				//封装 dataGrid 对象
				dataGrid = new DataGrid();
				dataGrid.setTotal(rows.size());
				dataGrid.setColumns(columns);
				dataGrid.setRows(rows);
			}catch(Exception e){
				logger.error("获取导出数据发生错误："+e.getMessage());
			}
		}else{
			count = dynamicFormBiz.listFormRecordCount(formRecord.getFormTemplateId());
			
			try{
				Exportor excelExportor = (Exportor)ServerInfo.getBean("excelExportor");
				//获取可用于导出的数据,与导出获取 formProp 数据方式，保持一致
				TableModel tableModel = excelExportor.export(getHttpServletResponse(),formTemplate,false);
				// 列头
				List<Column> columnsTmp = tableModel.getColumns();
				List columns = new ArrayList();
				columns.add(columnsTmp);
				List rows = new ArrayList();
				// 数据
				List<FormRecord> formRecords = tableModel.getData();
				if(formRecords!=null && formRecords.size()>0){
					count = formRecords.size();
					for (FormRecord recordTmp : formRecords) {
			        	Map<String,String> values = recordTmp.getFormProps();
			        	values.put("formRecordId", recordTmp.getId());
			        	rows.add(values);
					}
				}
				
				//封装 dataGrid 对象
				dataGrid = new DataGrid();
				dataGrid.setTotal(count);
				dataGrid.setColumns(columns);
				dataGrid.setRows(rows);
			}catch(Exception e){
				logger.error("获取导出数据发生错误："+e.getMessage());
			}
		}
		
		//转换dataGrid对象为json串
		JSONObject js = net.sf.json.JSONObject.fromObject(dataGrid);
		String json = js.toString();
		
		entry = json;

		operateLogBiz.info(OperateLogType.FORM_STATISTICS,this.formTemplate.getId(),formTemplate.getName(), "查询成功");
		return ENTRY;
	}
	
	public String exportCSV(){
		if(formRecord.getFormTemplateId()==null){
			operateResult = new OperateResult(-1, "未选中模板!");
			return OPER_RESULT;
		}
		
		try {
			formTemplate = dynamicFormBiz.findFormTemplate(entityKey);
			formTemplate.setRecord(formRecord);		//设置 record 实体，给予查询时使用
			
			Exportor csvExportor = (Exportor)ServerInfo.getBean("csvExportor");
			csvExportor.export(getHttpServletResponse(), formTemplate,true);
		} catch (ExportErrorException e) {
			String msg = e.getMessage();
			operateResult = new OperateResult(-1, "导出出现错误："+msg);
			return OPER_RESULT;
		}
        
		return null;
	}
	
	public String exportExcel(){
		if(formRecord.getFormTemplateId()==null){
			operateResult = new OperateResult(-1, "未选中模板!");
			return OPER_RESULT;
		}
		
		try {
			formTemplate = dynamicFormBiz.findFormTemplate(formRecord.getFormTemplateId());
			formTemplate.setRecord(formRecord);		//设置 record 实体，给予查询时使用
			
			Exportor excelExportor = (Exportor)ServerInfo.getBean("excelExportor");
			excelExportor.export(getHttpServletResponse(),formTemplate,true);
			operateLogBiz.info(OperateLogType.FORM_STATISTICS,this.formTemplate.getId(),formTemplate.getName(), "导出成功");
		} catch (ExportErrorException e) {
			String msg = e.getMessage();
			operateResult = new OperateResult(-1, "导出出现错误："+msg);
			operateLogBiz.info(OperateLogType.FORM_STATISTICS,this.formTemplate.getId(),formTemplate.getName(), "导出失败");
			return OPER_RESULT;
		}
        
		return null;
	}
	
}
