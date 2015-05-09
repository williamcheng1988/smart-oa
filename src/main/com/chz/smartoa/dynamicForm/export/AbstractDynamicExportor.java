package com.chz.smartoa.dynamicForm.export;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.chz.smartoa.dynamicForm.export.constant.FixedExpConstants;
import com.chz.smartoa.dynamicForm.export.exception.ExportErrorException;
import com.chz.smartoa.dynamicForm.pojo.Column;
import com.chz.smartoa.dynamicForm.pojo.Field;
import com.chz.smartoa.dynamicForm.pojo.FormProp;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateJsonContent;
import com.chz.smartoa.dynamicForm.pojo.Section;
import com.chz.smartoa.dynamicForm.pojo.StaffBenefits;
import com.chz.smartoa.dynamicForm.pojo.StaffWages;
import com.chz.smartoa.dynamicForm.pojo.Wage;
import com.chz.smartoa.dynamicForm.service.DynamicFormBiz;
import com.chz.smartoa.dynamicForm.service.StaffBenefitsBiz;
import com.chz.smartoa.dynamicForm.util.ServletUtils;
import com.chz.smartoa.dynamicForm.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 动态导出  抽象类
 * @author william
 * @time 23:59 2014/11/25
 */
public abstract class AbstractDynamicExportor implements Exportor {
	
	DynamicFormBiz dynamicFormBiz;
	StaffBenefitsBiz staffBenefitsBiz;
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
	/**
	 * 导出固定表单数据
	 * @param response
	 * @param tableModel
	 * @throws IOException
	 */
	public void exportFixed(HttpServletResponse response, TableModel tableModel)
            throws IOException {
		if(tableModel==null || 
    			tableModel.getData()==null ||tableModel.getData().size()==0){
			throw new ExportErrorException("动态表单无可导出的数据！");
    	}else if(tableModel.getHeadcodes()==null ||tableModel.getHeadcodes().size()==0){
    		throw new ExportErrorException("动态表单无可导出的列！");
    	}
    	
        StringBuilder buff = new StringBuilder();

        for (int i = 0; i < tableModel.getHeaderCount(); i++) {
            buff.append(tableModel.getHeader(i));

            if (i != (tableModel.getHeaderCount() - 1)) {
                buff.append(",");
            }
        }

        buff.append("\n");

        for (int i = 0; i < tableModel.getDataCount(); i++) {
            for (int j = 0; j < tableModel.getHeaderCount(); j++) {
                buff.append(tableModel.getValue(i, j));
                
                if (j != (tableModel.getHeaderCount() - 1)) {
                    buff.append(",");
                }
            }
            
            buff.append("\n");
        }

        response.setContentType(ServletUtils.STREAM_TYPE);
        ServletUtils.setFileDownloadHeader(response, tableModel.getName()
                + ".csv");
        response.getOutputStream().write(buff.toString().getBytes("UTF-8"));
        response.getOutputStream().flush();
    }
    /**
     * 导出动态表单数据
     * @param tableModel
     * @param formTemplate
     * @throws ExportErrorException
     */
    public void exportDynamic(FormTemplate formTemplate,TableModel tableModel)throws ExportErrorException{
    	String content = formTemplate.getContent(); 
		
		Gson gson = new GsonBuilder().create();
		FormTemplateJsonContent jsonContent = gson.fromJson(content, FormTemplateJsonContent.class);
		
		//获取导出到 Excel 列头
		List<String> columnHeaders = new ArrayList<String>();
		List<String> codes = new ArrayList<String>();
		List<Column> columnsTmp = new ArrayList<Column>();
		columnsTmp.add(new Column("formRecordId","填报编号","100px"));
		
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
				for(int i=0;i<fields.size();i++){
					Field field = fields.get(i);
					/**
					 * 类型为：textfield/radio等统计在内
					 * 类型为：label 则不统计
					 * 条件：需要导出
					 */
					if("label".equals(field.getType()) || (!field.isReportFlag() && !"T".equals(formTemplate.getExportFlag()))){
						continue;
					}
					codes.add(field.getName());
					columnHeaders.add(field.getAlias());
					columnsTmp.add(new Column(field.getName(),field.getAlias(),"100px"));
				}
				fields.clear();
			}
			sections.clear();
		}
		
		if((!"T".equals(formTemplate.getExportFlag())) 
				&& columnHeaders.size()==0){
			throw new ExportErrorException("动态表单\""+formTemplate.getName()+"\"无可导出的列！");
		}
		
		/**
		 * 获取所有符合条件的 formRecords
		 */
		FormRecord formRecord = formTemplate.getRecord();//new FormRecord();
		//formRecord.setFormTemplateId(formTemplate.getId());
		formRecord.setPropCodes(codes);
		formRecord.setStatus(0);
		List<FormRecord> formRecords = dynamicFormBiz.listForm(formRecord);
		
        tableModel.setName(formTemplate.getName());
        String[] strings = new String[columnHeaders.size()];
        tableModel.addHeaders(columnHeaders.toArray(strings));
        tableModel.setColumns(columnsTmp);
        tableModel.setHeadcodes(codes);
        tableModel.setData(formRecords);
		if (formRecords != null && formRecords.size() > 0) {
			//重组 FormProp 对象，以 code 为key 存入 LinkedHashMap 中
			Map<String,String> eachRecordLine = null;
			Map<String, List<FormProp>> props = null;
			for (FormRecord record : formRecords) {
				//循环“记录”
				eachRecordLine = new LinkedHashMap<String, String>();
				props = record.getProps();
				for(Map.Entry<String,List<FormProp>> entry:props.entrySet()){
					//循环每个“控件属性”
					String key = entry.getKey();
					StringBuffer value = new StringBuffer("");
					List<FormProp> lsprops = entry.getValue();
					
					//1. 如果是单个值，如text框/文本域
					if(lsprops!=null && lsprops.size()==1){
			    		FormProp prop = lsprops.get(0);
			    		eachRecordLine.put(key, StringUtils.isEmpty(prop.getValue())?
			    				"":prop.getValue().replaceAll(",", "~"));
			    		continue;
			    	}
					
					//2. 如果是多控件类型，如  userselection/checkbox
					Map<String,String> groupMap = new LinkedHashMap<String,String>();
					for(int i=0;i<lsprops.size();i++){
						FormProp prop = lsprops.get(i);
						String extensionCode = "";
						String groupStr = "";
						
						if(prop.getInnerName().indexOf("@")==-1){
							//其它控件
							extensionCode = ""+i;
							groupStr = prop.getValue();
						}else{
							//userselection
				        	String[] matchers = prop.getInnerName().split("~");
				        	String position = matchers[2];
				        	extensionCode = matchers.length>3?matchers[3]:"exCode";
							
				        	if(groupMap.containsKey(extensionCode)){
				        		groupStr = groupMap.get(extensionCode);
				        	}else{
				        		groupStr = "@1(@0)";
				        	}
				        	//替换 "@0" "@1"
				        	groupStr = groupStr.replace(position, prop.getValue());
						}
			        	
			        	//放入Map中
			        	groupMap.put(extensionCode, groupStr);
					}
					for(Map.Entry<String, String> e: groupMap.entrySet()){
						value.append(e.getValue()).append("~");
					}
					
					eachRecordLine.put(key,value.substring(0,value.lastIndexOf("~")));
				}
				record.setFormProps(eachRecordLine);
			}
		}
    }
    
    /**
     * 导出固定表单数据
     * @param tableModel
     * @param formTemplate
     * @throws ExportErrorException
     */
    public void exportFixedTemplate(FormTemplate formTemplate,TableModel tableModel)throws ExportErrorException{
    	if(!"T".equals(formTemplate.getExportFlag())){
			throw new ExportErrorException("动态表单\""+formTemplate.getName()+"\"无可导出的列！");
		}
		
		//Excel 列头
		List<String> columnHeaders = new ArrayList<String>();
		List<String> codes = new ArrayList<String>();
		List<Column> columnsTmp = new ArrayList<Column>();
		columnsTmp.add(new Column("formRecordId","填报编号","100px"));

		//获取column header 数据
		Map<String,String> constantMap = null;
		String viewUrl = formTemplate.getViewUrl();
		if("/fixedForm/form/flow-input-form1.html".equals(viewUrl)){
			constantMap = FixedExpConstants.TRAVELEXPENSE;
		} else if("/fixedForm/form/flow-input-form2.html".equals(viewUrl)){
			constantMap = FixedExpConstants.WEEKLYPUBLICATION;
		}
		
		/**
		 * 获取所有符合条件的 formRecords
		 */
		FormRecord formRecord = formTemplate.getRecord();//new FormRecord();
		formRecord.setPropCodes(codes);
		formRecord.setStatus(0);
		List<FormRecord> formRecords = dynamicFormBiz.listForm(formRecord);
		
		List<FormRecord> recordsExp = new ArrayList<FormRecord>();
		if (formRecords != null && formRecords.size() > 0) {
			//重组 FormProp 对象，以 code 为key 存入 LinkedHashMap 中
			Map<String,String> eachRecordLine = null;
			Map<String, List<FormProp>> props = null;
			for (FormRecord record : formRecords) {
				props = record.getProps();
				
				int count = record.getCount()+1;
				//根据count，动态创建记录数：record
				for(int i=0;i<count;i++){
					FormRecord r = new FormRecord();
					r.setId(record.getId());
					//循环“记录”
					eachRecordLine = new LinkedHashMap<String, String>();
					for(Map.Entry<String, String> entry: constantMap.entrySet()){
						String name = entry.getKey();
						String key = name;
						if(name.indexOf("~dy_clone")>0){
							key += i;
						}
						List<FormProp> lsprops = props.get(key);
						if(lsprops!=null && lsprops.size()>0){
							FormProp prop = lsprops.get(0);
				    		eachRecordLine.put(name, StringUtils.isEmpty(prop.getValue())?"":prop.getValue().replaceAll(",", "~"));
						}else{
							eachRecordLine.put(name, "");
						}
					}
					r.setFormProps(eachRecordLine);
					recordsExp.add(r);
				}
			}
		}
		
		for(Map.Entry<String, String> entry: constantMap.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			
			codes.add(key);
			columnHeaders.add(value);
			columnsTmp.add(new Column(key,value,"100px"));
		}
		
        tableModel.setName(formTemplate.getName());
        String[] strings = new String[columnHeaders.size()];
        tableModel.addHeaders(columnHeaders.toArray(strings));
        tableModel.setColumns(columnsTmp);
        tableModel.setHeadcodes(codes);
        tableModel.setData(recordsExp);
    }
    
    
}
