package com.chz.smartoa.dynamicForm.pojo;

import java.math.BigDecimal;
import java.util.List;

public class Field{
	private String type;
	private int row;
	private int col;
	private String name;
	private String alias;
	private String items;
	private BigDecimal minVal;			//数值控件
	private BigDecimal maxVal;			//数值控件
	private int precision;				//数值控件
	private String resources;			//合成控件
	private String text;
	private String dataType;
	private boolean required;
	private boolean readOnly;
	private boolean reportFlag;	//导出标识
	private List collections;
	
	private NameValPair[] dicsLs;
	
	//select 控件 数据字典名
	private String dic;
	//select 控件 数据字典列表
	private NameValPair[] dicsArr;
	
	//UserSelection
	private NameValPair[] rolesArr;
	private NameValPair[] usersArr;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public BigDecimal getMinVal() {
		return minVal;
	}
	public void setMinVal(BigDecimal minVal) {
		this.minVal = minVal;
	}
	public BigDecimal getMaxVal() {
		return maxVal;
	}
	public void setMaxVal(BigDecimal maxVal) {
		this.maxVal = maxVal;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public String getResources() {
		return resources;
	}
	public void setResources(String resources) {
		this.resources = resources;
	}
	public String getText() {
		return text;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public boolean isReportFlag() {
		return reportFlag;
	}
	public void setReportFlag(boolean reportFlag) {
		this.reportFlag = reportFlag;
	}
	public List getCollections() {
		return collections;
	}
	public void setCollections(List collections) {
		this.collections = collections;
	}
	public NameValPair[] getDicsLs() {
		return dicsLs;
	}
	public void setDicsLs(NameValPair[] dicsLs) {
		this.dicsLs = dicsLs;
	}
	public NameValPair[] getRolesArr() {
		return rolesArr;
	}
	public void setRolesArr(NameValPair[] rolesArr) {
		this.rolesArr = rolesArr;
	}
	public NameValPair[] getUsersArr() {
		return usersArr;
	}
	public void setUsersArr(NameValPair[] usersArr) {
		this.usersArr = usersArr;
	}
	public String getDic() {
		return dic;
	}
	public void setDic(String dic) {
		this.dic = dic;
	}
	public NameValPair[] getDicsArr() {
		return dicsArr;
	}
	public void setDicsArr(NameValPair[] dicsArr) {
		this.dicsArr = dicsArr;
	}
}