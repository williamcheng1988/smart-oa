package com.chz.smartoa.dynamicForm.export;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.chz.smartoa.dynamicForm.export.exception.ExportErrorException;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.util.ServletUtils;

/**
 * 导出 CSV 文件
 * @author william
 * @time 17:17 2014/10/25
 */
public class CsvExportor extends AbstractDynamicExportor {
	
	private static final Logger logger = Logger.getLogger(CsvExportor.class);
	
	public TableModel export(HttpServletResponse response, FormTemplate formTemplate,boolean doReport)
            throws ExportErrorException {
		//判断是否为固定表单
		boolean isDynamic = formTemplate!=null?formTemplate.isDynamic():false;
		
		TableModel tableModel = new TableModel();
		if(!isDynamic){
			//固定表单导出
			try {
				exportFixed(response, tableModel);
			} catch (IOException e) {
				logger.error("CsvExportor>>导出固定表单数据CSV出错:"+e.getMessage());
				throw new ExportErrorException("CsvExportor>>导出固定表单数据CSV出错:"+e.getMessage());
			}
		}else{
			//动态表单导出
			exportDynamic(formTemplate, tableModel);
			//如果不需要导出
			if(!doReport){return tableModel;}
			
			exportCsv(response, tableModel, formTemplate);
		}
		
		return tableModel;
	}
    /**
     * 导出动态表单数据
     * @param response
     * @param tableModel
     * @param formTemplate
     * @throws ExportErrorException
     */
    public void exportCsv(HttpServletResponse response, 
    		TableModel tableModel,FormTemplate formTemplate)throws ExportErrorException{
    	
		StringBuilder buff = new StringBuilder();
		//数据
        List<String> codes = tableModel.getHeadcodes();
        List<FormRecord> formRecords = tableModel.getData();
		if (formRecords != null && formRecords.size() > 0) {
			//列头
			for (int i = 0; i < tableModel.getHeaderCount(); i++) {
	            buff.append(tableModel.getHeader(i));
	            
	            if (i != (tableModel.getHeaderCount() - 1)) {
	                buff.append(",");
	            }
	        }
			
	        buff.append("\n");
	        
	        //数据
	        for (FormRecord recordTmp : formRecords) {
	        	Map<String,String> values = recordTmp.getFormProps();
	        	
	        	for (int j = 0; j < codes.size(); j++) {
	        		buff.append(values.containsKey(codes.get(j))?values.get(codes.get(j)):"");
	        		
	        		if (j != (tableModel.getHeaderCount() - 1)) {
	                    buff.append(",");
	                }
				}
				buff.append("\n");
			}

			//导出完成，以流形式输出
			response.setContentType(ServletUtils.STREAM_TYPE);
	        try {
				ServletUtils.setFileDownloadHeader(response, tableModel.getName()
				        + ".csv");

		        response.getOutputStream().write(buff.toString().getBytes("UTF-8"));
		        response.getOutputStream().flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
