package com.chz.smartoa.dynamicForm.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.Region;

import com.chz.smartoa.dynamicForm.export.exception.ExportErrorException;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;

/**
 * 导出 Excel 文件
 * @author william
 * @time 23:59 2014/11/25
 */
public class ExcelExportor extends AbstractDynamicExportor {
	
	private static final Logger logger = Logger.getLogger(ExcelExportor.class);
	
	@Override
	public TableModel export(HttpServletResponse response,FormTemplate formTemplate,boolean doReport)
            throws ExportErrorException {
		//判断是否为固定表单
		boolean isDynamic = formTemplate!=null?formTemplate.isDynamic():false;
		
		TableModel tableModel = new TableModel();
		if(!isDynamic){
			//固定表单导出
			try {
				exportFixed(response, tableModel);
			} catch (IOException e) {
				logger.error("ExcelExportor>>导出固定表单数据CSV出错:"+e.getMessage());
				throw new ExportErrorException("ExcelExportor>>导出固定表单数据Excel出错:"+e.getMessage());
			}
		}else{
			String viewUrl = formTemplate.getViewUrl();
			if(!"/dynamicForm/form/flow-input-form.html".equals(viewUrl)){
				exportFixedTemplate(formTemplate, tableModel);
			}else{
				/**
				 * 动态表单导出
				 */
				exportDynamic(formTemplate,tableModel);
			}
			//如果不是导出，而只是获取数据
			if(!doReport){return tableModel;}
			
			exportExcel(response, tableModel);
		}
		
		return tableModel;
	}
    
    /**
     * 导出 Excel
     * @param response
     * @param tableModel
     * @param formTemplate
     * @throws ExportErrorException
     */
    public void exportExcel(HttpServletResponse response, 
    		TableModel tableModel)throws ExportErrorException{
    	
    	if(tableModel==null || 
    			tableModel.getData()==null ||tableModel.getData().size()==0){
			throw new ExportErrorException("动态表单无可导出的数据！");
    	}else if(tableModel.getHeadcodes()==null ||tableModel.getHeadcodes().size()==0){
			throw new ExportErrorException("动态表单无可导出的列！");
    	}
    	
        //数据
        List<String> codes = tableModel.getHeadcodes();
        List<FormRecord> formRecords = tableModel.getData();
        
    	int x = 0;
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet1 = book.createSheet();
        Region region = new   Region(0,   (short)   0,   0,   (short)   (codes.size()-1));
        sheet1.addMergedRegion(region);
        setExcelCell(sheet1);
        
        book.setSheetName(0,tableModel.getName());
        HSSFCellStyle style1 = book.createCellStyle();
        HSSFFont font1 = book.createFont();
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font1.setUnderline(HSSFFont.U_SINGLE);
        style1.setFont(font1);
        
        HSSFCellStyle style2 = createFirstStyle(book);
        HSSFCellStyle style3 = createSecondStyle(book);
        HSSFCellStyle style4 = createThirdStyle(book);
        
        HSSFRow row0 = getExcelRow(sheet1, x);
        getExcelCell(row0, tableModel.getName(), 0, style2);
        x++;
        
        /*HSSFRow row1 = getExcelRow(sheet1, x);
        getExcelCell(row1, "状态", 0, style3);
        getExcelCell(row1, "", 1, style4);
        x++;*/
        
        HSSFRow row2 = getExcelRow(sheet1, x);
        int titleNum = 0;
        
		//列头
		for (int i = 0; i < tableModel.getHeaderCount(); i++) {
			getExcelCell(row2, tableModel.getHeader(i), titleNum, style3);						titleNum++;
        }
        
        x++;
        
        HSSFRow rowData = null;
        for (FormRecord recordTmp : formRecords) {
            int cellNum = 0;
    		rowData = getExcelRow(sheet1, x++);
        	Map<String,String> values = recordTmp.getFormProps();
        	for (int j = 0; j < codes.size(); j++) {
        		String cellValue = values.containsKey(codes.get(j))?values.get(codes.get(j)):"";
        		getExcelCell(rowData, cellValue, cellNum, style4);
        		cellNum++;
			}
		}
        
        try {
            String filePath = ExcelExportor.class.getResource("/").getPath()+
                    (new String("Daily_Report".getBytes("UTF-8"))).concat(".xls");
            String fileName = new String("sign_report".getBytes("UTF-8")).concat(".xls");       
        
            FileOutputStream fileoutputstream = new FileOutputStream(filePath);
            book.write(fileoutputstream);
            fileoutputstream.close();
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            int bytes = fis.available();
            //download
            response.setContentType("application/octet-stream;charset =UTF-8");
            response.setHeader("Content-Disposition", "attachment; " +
                 "filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
            response.setHeader("Content-Length", ""+bytes);
            response.setCharacterEncoding("UTF-8");
            OutputStream os = response.getOutputStream();
            byte [] b = new byte [bytes];
            int len = -1;
            while ((len=fis.read(b)) != -1 ) {
                os.write(b,0,len);
            }
            os.flush();
            os.close();
            fis.close();
            file.delete();
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
    private void setExcelCell(HSSFSheet sheet){
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$L$1"));
        sheet.setColumnWidth(0, 256*23);
        sheet.setColumnWidth(1, 256*23);
        sheet.setColumnWidth(2, 256*23);
        sheet.setColumnWidth(3, 256*23);
        sheet.setColumnWidth(4, 256*25);
        sheet.setColumnWidth(5, 256*25);
        sheet.setColumnWidth(6, 256*23);
        sheet.setColumnWidth(7, 256*18);
        sheet.setColumnWidth(8, 256*18);
        sheet.setColumnWidth(9, 256*18);
        sheet.setColumnWidth(10, 256*18);
        sheet.setColumnWidth(11, 256*18);
        sheet.setColumnWidth(12, 256*18);
        sheet.setColumnWidth(13, 256*18);
        sheet.setColumnWidth(14, 256*18);
        sheet.setColumnWidth(15, 256*18);
        sheet.setColumnWidth(16, 256*18);
        sheet.setColumnWidth(17, 256*18);
        sheet.setColumnWidth(18, 256*18);
        sheet.setColumnWidth(19, 256*18);
        sheet.setColumnWidth(20, 256*18);
        sheet.setColumnWidth(21, 256*18);
        sheet.setColumnWidth(22, 256*18);
    }
    private HSSFCell getExcelCell(HSSFRow row, String value, int colNumber, HSSFCellStyle style){
        HSSFCell cell = null;
        
        if(null != row){
            cell = row.createCell((short)colNumber);
            if(null != style){
               cell.setCellStyle(style); 
            }
            
            String str = null;
            str = value;
            if(null == str){
                str = "";
            }
            cell.setCellValue(str);
        }
        
        return cell;
    }
    
    private HSSFRow getExcelRow(HSSFSheet sheet, int rowNumber){
        HSSFRow row = null;
        
        if(null != sheet){
            row = sheet.createRow(rowNumber);
        }
        
        return row;
    }
    private HSSFCellStyle createFirstStyle(HSSFWorkbook wb) {
        //create first row style
        HSSFCellStyle conStyle=wb.createCellStyle();
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setFontName("Times New Roman");
        headerFont.setFontHeightInPoints((short)14);
        conStyle.setAlignment(CellStyle.ALIGN_CENTER);
        conStyle.setFont(headerFont);
        
        return conStyle;
    }
    private HSSFCellStyle createSecondStyle(HSSFWorkbook wb) {
        // create second row style
        HSSFCellStyle conStyle=wb.createCellStyle();
        Font ccFont = wb.createFont();
        ccFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        ccFont.setFontName("Times New Roman");
        ccFont.setFontHeightInPoints((short)12);
        conStyle.setAlignment(CellStyle.ALIGN_CENTER);
        conStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        conStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        
        conStyle.setFont(ccFont);
        return conStyle;
    }
    
    private HSSFCellStyle createThirdStyle(HSSFWorkbook wb) {
        // create third row style
        HSSFCellStyle conStyle=wb.createCellStyle();
        Font ccFont = wb.createFont();
        ccFont.setFontName("Times New Roman");
        ccFont.setFontHeightInPoints((short)9);
        conStyle.setAlignment(CellStyle.ALIGN_LEFT);
        conStyle.setFont(ccFont);
        return conStyle;
    }
    
}
