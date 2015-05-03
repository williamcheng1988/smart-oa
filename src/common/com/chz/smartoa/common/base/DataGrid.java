package com.chz.smartoa.common.base;

import java.io.Serializable;
import java.util.List;

import com.chz.smartoa.dynamicForm.pojo.Column;

public class DataGrid implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public DataGrid() {
		
	}
	
	public DataGrid(int total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	
	private int total;
	private List<?> rows;
	
	/** 自定义表格  **/
	private List<List<Column>> columns;
	private List<?> data;
	private boolean pagination = false;
	
	public List<List<Column>> getColumns() {
		return columns;
	}
	public void setColumns(List<List<Column>> columns) {
		this.columns = columns;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public boolean isPagination() {
		return pagination;
	}
	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}
