package com.chz.smartoa.dynamicForm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.chz.smartoa.dynamicForm.pojo.FormProp;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.form.constants.FormConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 构建Record.
 */
public class RecordBuilder {
	
	public static String PREFIX_FEILD_NAME = "xf_f_";
	
	public static List<FormRecord> buildAll(FormRecord record, 
            Map<String, String[]> parameters) {
		
		if(record==null || parameters==null || parameters.size()==0){
        	return new ArrayList<FormRecord>();
        }
		//设置一般属性
    	String uuid = UUID.randomUUID().toString();
    	if(StringUtils.isEmpty(record.getId())){
            record.setId("Form_"+uuid);
    	}
    	if(StringUtils.isEmpty(record.getUuId())){
    		record.setUuId(uuid);
    	}
        
		List<FormRecord> formRecords = new ArrayList<FormRecord>();
		
		//记录条数大于1
		int count = 0;
		if(parameters.containsKey(FormConstants.FORMRECORD_COUNT)){
			String[] values = parameters.get(FormConstants.FORMRECORD_COUNT);
			count=(values!=null&&values.length>0)?Integer.valueOf(values[0]):0;
		}
		
		if(count>0){
			//如果大于 0 ，则记录条数至少为一条以上
			String[] values = parameters.get(FormConstants.FORMRECORD_COUNT);
			count=(values!=null&&values.length>0)?Integer.valueOf(values[0]):0;
			build(record, parameters,count);
		}else{
			build(record, parameters);
			formRecords.add(record);
		}
		
		return formRecords;
	}
	/**
     * 封装 record 数据
     * 针对记录条数为多条的表单记录
     */
    public static List<FormRecord> build(FormRecord record, 
            Map<String, String[]> parameters,int count) {
    	List<FormRecord> formRecords = new ArrayList<FormRecord>();
    	
        Map<String,Map<String, String[]>> mapFormPara = new HashMap<String, Map<String, String[]>>();
        Map<String, String[]> srcParameters = new HashMap<String, String[]>();
        srcParameters.putAll(parameters);
        
        for (Map.Entry<String, String[]> entry : srcParameters.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            
            if ((value == null) || (value.length == 0)) {
                continue;
            }
            
            if(key.indexOf(FormConstants.SUFFIX_FORMPROP_NAME_CLONE)>1){
            	
            	String groupKey = key.substring(key.indexOf(FormConstants.SUFFIX_FORMPROP_NAME_CLONE));
            	
            	Map<String,String[]> paramMapTmp = null;
            	if(mapFormPara.containsKey(groupKey)){
            		paramMapTmp = mapFormPara.get(groupKey);
            	}else{
            		paramMapTmp = new HashMap<String, String[]>();
            	}
            	paramMapTmp.put(key,value);
            	mapFormPara.put(groupKey, paramMapTmp);
            	continue;
            }
            
            FormProp prop = new FormProp();
            prop.setName(key);
            prop.setValue(getValue(value));
            
            List<FormProp> props = null;
            if(record.getProps().containsKey(prop.getName())){
            	props = record.getProps().get(prop.getName());
            }else{
            	props = new ArrayList<FormProp>();
            }
            props.add(prop);
            
            record.getProps().put(prop.getName(),props);
        }
        
        for (Map.Entry<String, Map<String, String[]>> entry : mapFormPara.entrySet()) {
        	Map<String, String[]> valMap = entry.getValue();
        	FormRecord r = null;
        	if(r!=null){
        		formRecords.add(build(r, valMap));
        	}
		}
        
        return formRecords;
    }
    
    /**
     * 封装 record 数据
     * 针对记录条数为单条的表单记录
     */
    public static FormRecord build(FormRecord record, 
            Map<String, String[]> parameters) {
    	String uuid = UUID.randomUUID().toString();
    	if(StringUtils.isEmpty(record.getId())){
            record.setId("Form_"+uuid);
    	}
    	if(StringUtils.isEmpty(record.getUuId())){
            record.setUuId(uuid);
    	}
        
        if(parameters==null || parameters.size()==0){
        	return record;
        }
        
        //记录条数大于1
  		int count = 0;
  		if(parameters.containsKey(FormConstants.FORMRECORD_COUNT)){
  			String[] values = parameters.get(FormConstants.FORMRECORD_COUNT);
  			count=(values!=null&&values.length>0)?Integer.valueOf(values[0]):0;
  		}
  		if(count>0){
  			record.setCount(count);
  		}

        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();

            if ((value == null) || (value.length == 0)) {
                continue;
            }
            
            FormProp prop = new FormProp();
            prop.setName(key);
            if(key.lastIndexOf(PREFIX_FEILD_NAME)>1){
            	String code = key.substring(0,key.lastIndexOf(PREFIX_FEILD_NAME)-1);
            	//parentCode = parentCode.indexOf("-")==-1?parentCode:parentCode.substring(0, parentCode.indexOf("-"));
            	prop.setName(code);
            	prop.setInnerName(key);
            }
            prop.setValue(getValue(value));
            
            List<FormProp> props = null;
            if(record.getProps().containsKey(prop.getName())){
            	props = record.getProps().get(prop.getName());
            }else{
            	props = new ArrayList<FormProp>();
            }
            props.add(prop);
            
            record.getProps().put(prop.getName(),props);
        }

        return record;
    }

    /**
     * 获得多值属性，比如checkbox.
     */
    public static String getValue(String[] values) {
        if ((values == null) || (values.length == 0)) {
            return "";
        }
        
        if (values.length == 1) {
            return values[0];
        }

        StringBuilder buff = new StringBuilder("[");
        
        for (String value : values) {
            buff.append(value).append(",");
        }

        buff.deleteCharAt(buff.length() - 1);
        
        buff.append("]");

        return buff.toString();
    }
    
    public static Map<String,Object> recordToMap(FormRecord record){
    	
    	Map<String,Object> map = new HashMap<String, Object>();
    	
    	Map<String, List<FormProp>> props= record.getProps();

        for (Map.Entry<String, List<FormProp>> entry: props.entrySet()) {
            //String key = entry.getKey();
        	List<FormProp> lsprops = entry.getValue();
            
            if (lsprops == null) {
                continue;
            }
            
            tranPropToJson(map, lsprops);
        }
        
        //记录条数大于1
  		int count = record.getCount();
  		if(count>0){
  			map.put(FormConstants.FORMRECORD_COUNT,count);
  		}
        
        return map;
    }
    /**
     * 包含多个元素的转换方法
     * @param map
     * @param prop
     */
    private static void tranPropToJson(Map<String,Object> map,List<FormProp> props){
    	if(props!=null && props.size()==1){
    		FormProp prop = props.get(0);
    		String name = prop.getName();
    		String value = prop.getValue();
    		if(!StringUtils.isEmpty(value) 
    				&& value.startsWith("[") && value.endsWith("]")){
    			String str = value.substring(1, value.length()-1);
    			String[] array = str.split(",");
    			map.put(name, array);
    		}else{
    			map.put(name, value);
    		}
    		
    		return;
    	}
    	if(props==null || props.size()<2){
    		return;
    	}
    	
    	String proManagerExCode = "";
    	for(FormProp prop:props){
        	/**
        	 * 人员选择控件：UserSelection
        	 */
        	//所有props
        	Map<String,List<FormProp>> groupMap = null;
        	if(map.containsKey(prop.getName())){
        		groupMap = (Map<String, List<FormProp>>) map.get(prop.getName());
        	}else{
        		groupMap = new LinkedHashMap<String,List<FormProp>>();
        	}
        	
        	String[] matchers = prop.getInnerName().split("~");
        	
        	String extensionCode = matchers.length>3?matchers[3]:"exCode";
        	
        	/**
    		 * 费用报销页面需要用到“项目经理”
    		 */
    		if("项目经理".equals(prop.getValue())){
    			proManagerExCode = extensionCode;
    		}
    		
        	List<FormProp> listProp = null;
        	if(groupMap.containsKey(extensionCode)){
        		listProp = groupMap.get(extensionCode);
        		
        		if(proManagerExCode.equals(extensionCode)){
        			//项目经理名字
        			String proManagerName = "";
        			if(prop.getInnerName().indexOf("@1")>0){
        				proManagerName = prop.getValue();
    				}else{
    					proManagerName = listProp.get(0).getValue();
    				}
    				map.put("xf_f_fixedName_xmjl", proManagerName);
        		}
        	}else{
        		listProp = new ArrayList<FormProp>();
        	}
    		listProp.add(prop);
    		
    		groupMap.put(extensionCode, listProp);
        	
        	map.put(prop.getName(), groupMap);
    	}
    }
    
    public static void main(String[] args) {
    	String[] values = new String[10];
    	values[0]="0";
    	values[1]="1";
    	values[2]="2";
    	values[3]="3";
    	
    	Map map = new HashMap();
    	map.put("test",values);
    	
    	Gson gson = new GsonBuilder().create();
		
		String jsonDatas = gson.toJson(map);
		
		System.out.println(jsonDatas);
		
		
		String value = "[\" 第一天\", \" 第三天 \"]";
		String str = value.substring(1, value.length()-1);
		System.out.println(str);
		
		String[] array = str.split(",");
		for (int i = 0; i < array.length; i++) {
			System.out.println("|"+array[i]+"|");
		}
		
    }
}
