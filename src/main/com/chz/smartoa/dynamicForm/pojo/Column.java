package com.chz.smartoa.dynamicForm.pojo;

/**
 * 列对象
 * @author william
 * @time 1:04 2014/12/5
 */
public class Column{
	
	public Column(){}
	public Column(String field,String title){
		this.field = field;
		this.title = title;
	}
	public Column(String field,String title,String width){
		this.field = field;
		this.title = title;
		this.width = width;
	}
	public Column(String field,String title,String width,String formatter){
		this.field = field;
		this.title = title;
		this.width = width;
		this.formatter=formatter;
	}
	private String field;
	private String title;
	private String width;
	private String align;
	private int rowspan;
	private boolean sortable;
	private String formatter; 
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public int getRowspan() {
		return rowspan;
	}
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	public boolean isSortable() {
		return sortable;
	}
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}
	public String getFormatter() {
		return formatter;
	}
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
}