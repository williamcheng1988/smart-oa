package com.chz.smartoa.dynamicForm.export;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chz.smartoa.dynamicForm.pojo.Column;
import com.chz.smartoa.dynamicForm.pojo.StaffWages;
import com.chz.smartoa.dynamicForm.util.ReflectUtils;

public class TableModel {
    private static Logger logger = LoggerFactory.getLogger(TableModel.class);
    private String name;
    private List<String> headers = new ArrayList<String>();
    private List<String> headcodes = new ArrayList<String>();
    private List<Column> columns = new ArrayList<Column>();
    private List<?> data;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void addHeaders(String... header) {
        if (header == null) {
            return;
        }
        
        for (String text : header) {
            if (text == null) {
                continue;
            }

            headers.add(text);
        }
    }

    public void setData(List data) {
        this.data = data;
        getHeaders(data);
    }
    public List getData() {
        return this.data;
    }

    public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public int getHeaderCount() {
        return headers.size();
    }

    public int getDataCount() {
        return data.size();
    }

    public String getHeader(int index) {
        return headers.get(index);
    }
    
    public List<String> getHeadcodes() {
		return headcodes;
	}

	public void setHeadcodes(List<String> headcodes) {
		this.headcodes = headcodes;
	}

	public String getValue(int i, int j) {
        try {
            String header = getHeader(j);
            Object object = data.get(i);
            String methodName = ReflectUtils
                    .getGetterMethodName(object, header);
            Object value = ReflectUtils.getMethodValue(object, methodName);

            return (value == null) ? "" : value.toString();
        } catch (Exception ex) {
            logger.info("error", ex);

            return "";
        }
    }
	/**
	 * 获取导出列头信息
	 * @param data
	 */
	public void getHeaders(List data){
		if(data!=null && data.size()>0){
			try{
				Object object = data.get(0);
				Class clazz = object.getClass();
				Field[] fields = clazz.getDeclaredFields();
				for(Field f : fields){
					//列 键
					headcodes.add(f.getName());
					
					ExpColumn c = (ExpColumn)f.getAnnotation(ExpColumn.class);
					if(c==null){continue;}
					//列头
					headers.add(c.value());
					
					System.out.println(f.getName());
					
					System.out.println(c.value());
				}
			}catch(Exception e){
				
			}
		}
	}
	
	public static void main(String[] args) {
		List<StaffWages> datas = new ArrayList<StaffWages>();
		datas.add(new StaffWages("1"));
		TableModel t = new TableModel();
		
		t.setData(datas);
	}
	
}
